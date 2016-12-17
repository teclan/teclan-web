package teclan.web.api;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.util.List;
import java.util.Map;

import org.javalite.common.Inflector;

import com.google.common.reflect.TypeToken;

import teclan.utils.GsonUtils;
import teclan.utils.db.ActiveRecord;
import teclan.web.db.service.ActiveJdbcService;
import us.monoid.json.JSONObject;

public abstract class AbstractServiceApis<T extends ActiveRecord>
        implements ServiceApis {

    @Override
    public void initApis() {
        // 查询所有
        get(getResource() + "/all", (request, response) -> {
            return GsonUtils.toJsonForJdbcRecord(getService().all());
        });

        // 按id查询
        get(getResource() + "/fetch/:id", (request, response) -> {
            long id = Long.valueOf(request.params(":id"));
            return GsonUtils.toJsonForJdbcRecord(getService().findById(id));
        });

        // 分页查询
        get(getResource() + "/fetch", (request, response) -> {
            if ("all".equals(request.queryParams("page"))) {
                return GsonUtils.toJsonForJdbcRecord(getService().all());
            } else {
                int page = 1;
                int limit = 10;
                if (request.queryParams("page") != null) {
                    page = Integer.valueOf(request.queryParams("page"));
                }
                if (request.queryParams("limit") != null) {
                    limit = Integer.valueOf(request.queryParams("limit"));
                }
                return getService().fetch(page, limit, "1=1").toJson();
            }
        });

        // 添加记录
        post(getResource() + "/new", (request, response) -> {
            Map<String, Object> attributes = GsonUtils
                    .toMap(new JSONObject(request.body()).get(getResource())
                            .toString());
            getService().create(attributes);
            return request.body();
        });

        put(getResource() + "/sys/:id", (request, response) -> {
            long id = Long.valueOf(request.params(":id"));
            Map<String, Object> attributes = GsonUtils.toMap(
                    new JSONObject(request.body()).get("terminal").toString());
            return GsonUtils
                    .toJsonForJdbcRecord(getService().sync(id, attributes));
        });

        put(getResource() + "/sys", (request, response) -> {
            List<Map<String, Object>> maps = GsonUtils.fromJson(request.body(),
                    Inflector.pluralize(getResource()),
                    new TypeToken<List<Map<String, Object>>>() {
                        private static final long serialVersionUID = 3731405824720413383L;
                    }.getType());

            return GsonUtils.toJsonForJdbcRecord(getService().sync(maps));
        });

        delete(getResource() + "/delete/:id", (request, response) -> {
            long id = Long.valueOf(request.params(":id"));
            getService().delete(id);
            return new JSONObject();

        });
    }

    public abstract String getResource();

    public abstract ActiveJdbcService<T> getService();

}
