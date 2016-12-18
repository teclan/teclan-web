package teclan.web.api;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.javalite.common.Inflector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.reflect.TypeToken;

import teclan.utils.GsonUtils;
import teclan.utils.db.ActiveRecord;
import teclan.web.db.service.ActiveJdbcService;
import us.monoid.json.JSONArray;
import us.monoid.json.JSONException;
import us.monoid.json.JSONObject;

public abstract class AbstractServiceApis<T extends ActiveRecord>
        implements ServiceApis {
    private final Logger LOGGER = LoggerFactory
            .getLogger(AbstractServiceApis.class);

    @Override
    public void initApis() {
        // 查询所有
        get(getResource() + "/all", (request, response) -> {
            List<T> results = getService().all();
            if (results != null && !results.isEmpty()) {
                return generateResult(results).toString();
            } else {
                LOGGER.warn("\n无查询结果 {}", getResource() + "/all");
                return new JSONObject();
            }
        });

        // 按id查询
        get(getResource() + "/fetch/:id", (request, response) -> {
            long id = Long.valueOf(request.params(":id"));
            T record = getService().findById(id);
            if (record != null) {
                return generateResult(record);
            } else {
                LOGGER.warn("\n无查询结果 {}", getResource() + "/fetch/" + id);
                return new JSONObject();
            }
        });

        /*
         * 分页查询
         * page 从 1 开始，如果 page = all 则忽略所有条件，查询所有
         * 支持条件查询，所有条件包装在参数中的 condition 字段，json对象形式存放
         * 如果查询结果正确，结果信息中有一下字段：
         * date : 记录列表信息
         * mate : 返回结果头信息，mate包含以下字段
         * total : 满足当前条件的所有记录条数
         * offset : 当前是第几页
         * limit : 每页多少条记录
         * pages : 总共有多少页
         */
        post(getResource() + "/fetch", (request, response) -> {
            if ("all".equals(request.queryParams("page"))) {

                List<T> results = getService().all();
                if (results != null && !results.isEmpty()) {
                    return generateResult(results);
                } else {
                    LOGGER.warn("\n无查询结果 {}", getResource() + "/all");
                    return new JSONObject();
                }

            } else {
                int page = 1;
                int limit = 10;
                String query = "1 = ?";
                Object[] parameters = new Object[]{1};
                if (request.queryParams("page") != null) {
                    page = Integer.valueOf(request.queryParams("page"));
                }
                if (request.queryParams("limit") != null) {
                    limit = Integer.valueOf(request.queryParams("limit"));
                }
                
               
                Object[] params =  request.queryParams().toArray(); 
                
                Map<String,String> paramsMap = new LinkedHashMap<String,String>();
                
                
                for(Object param :params){
                	if("page".equals(param) || "limit".equals(param)){
                		continue;
                	}
                	paramsMap.put((String)param,request.queryParams((String)param));
                	
                }
                
                if (paramsMap != null) {
                    query = generateQuery(paramsMap);
                    parameters=  generateParameters(paramsMap);
                }
                
                return getService().fetch(page, limit, query, parameters)
                        .toJson();
            }
        });

        // 添加记录
        post(getResource() + "/new", (request, response) -> {
        	
            Object[] params =  request.queryParams().toArray(); 
            
            Map<String,Object> attributes = new LinkedHashMap<String,Object>();
            
            for(Object param:params){
            	attributes.put((String)param,request.queryParams((String)param));
            }
        	
            getService().create(attributes);
            return request.body();
        });
        
        // 添加记录
        put(getResource() + "/new", (request, response) -> {
       
        	LOGGER.info(request.body());
        	   Map<String, Object> attributes = GsonUtils
                       .toMap(new JSONObject(request.body()).get(getResource())
                               .toString());
        	   
            getService().create(attributes);
            return request.body();
        });
        

        // 指定 id 更新记录
        put(getResource() + "/sys/:id", (request, response) -> {
            long id = Long.valueOf(request.params(":id"));
            Map<String, Object> attributes = GsonUtils
                    .toMap(new JSONObject(request.body()).get(getResource())
                            .toString());
            List<T> results = getService().sync(id, attributes);
            if (results != null && !results.isEmpty()) {
                return generateResult(results);
            } else {
                LOGGER.warn("\n无查询结果 {}", getResource() + "/sys/" + id);
                return new JSONObject();
            }
        });

        // 批量更新记录
        put(getResource() + "/sys", (request, response) -> {
            List<Map<String, Object>> maps = GsonUtils.fromJson(request.body(),getResource(),
                    new TypeToken<List<Map<String, Object>>>() {
                        private static final long serialVersionUID = 3731405824720413383L;
                    }.getType());

            List<T> results = getService().sync(maps);
            if (results != null && !results.isEmpty()) {
                return generateResult(results);
            } else {
                LOGGER.warn("\n无查询结果 {}", getResource() + "/sys");
                return new JSONObject();
            }
        });

        // 指定 id 删除记录
        delete(getResource() + "/delete/:id", (request, response) -> {
            long id = Long.valueOf(request.params(":id"));
            getService().delete(id);
            return new JSONObject();
        });

        // 批量删除记录
        // 如果 ids 不存在，将删除所有记录
        delete(getResource() + "/delete", (request, response) -> {
            String[] ids = null;
            if (request.params("ids") == null) {
                getService().deleteAll(ids);
            } else {
                ids = request.params("ids").split(",");
                getService().deleteAll(ids);
            }
            return new JSONObject();
        });
    }

    public abstract String getResource();

    public abstract ActiveJdbcService<T> getService();

    private String generateQuery(Map<String, String> condition) {
    	
    	 if(condition.size()==1){
    		 for (String key : condition.keySet()) {
    			 return String.format("%s like ? ", key);
    	        }
         }
    	 
        List<String> columns = new ArrayList<String>();
        
        for (String key : condition.keySet()) {
            columns.add(key);
        }
       
        return String.join(" like ? and ", columns);
    }

    private Object[] generateParameters(Map<String, String> condition) {
        Object[] parameters = new Object[condition.size()];
        int index = 0;
        for (String key : condition.keySet()) {
            parameters[index] = "%"+condition.get(key)+"%";
            index++;
        }
        return parameters;
    }

    private JSONObject generateResult(List<T> records) {
        JSONArray items = new JSONArray();
        for (T t : records) {
            items.put(t.toJson());
        }
        try {
            return new JSONObject().put(getResource(), items);
        } catch (JSONException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return new JSONObject();
    }

    private JSONObject generateResult(T record) {
        try {
            return new JSONObject().put(getResource(), record.toJson());
        } catch (JSONException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return new JSONObject();
    }

}
