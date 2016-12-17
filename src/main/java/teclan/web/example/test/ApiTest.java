package teclan.web.example.test;

import static us.monoid.web.Resty.form;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.gson.GsonBuilder;

import teclan.utils.GsonUtils;
import teclan.web.example.Main;
import us.monoid.json.JSONArray;
import us.monoid.json.JSONException;
import us.monoid.json.JSONObject;
import us.monoid.web.JSONResource;
import us.monoid.web.Resty;

// 测试之前先执行 teclan.web.example 的 Main.class
public class ApiTest {
	private final Logger LOGGER = LoggerFactory.getLogger(ApiTest.class);
	private final String GET_ALL_URL = "http://localhost:%d/%s/all";
	private final String FETCH_BY_ID_URL = "http://localhost:%d/%s/fetch/%d";
	private final String FETCH_WITH_QUERY_URL = "http://localhost:%d/%s/fetch";
	
	@Test
	public void fetchWithQuery(){

        try {
			LOGGER.info("{}", new Resty().json(String.format(FETCH_WITH_QUERY_URL, Main.PORT, getResource()),
			        form("page=all")).object().toString());
			
			LOGGER.info("\n=================");
			
			LOGGER.info("{}", new Resty().json(String.format(FETCH_WITH_QUERY_URL, Main.PORT, getResource()),
			        form("page=1&limit=2&content=测试")).object().toString());
			
			
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Test
    public void getAll() {
        try {

        	JSONObject json = new Resty()
                    .json(String.format(GET_ALL_URL, Main.PORT, getResource())).object();
        	LOGGER.info("=={}",json);
        	JSONArray array = ((JSONArray)json.get("content"));

        	for(int i=0;i<array.length();i++){
        		LOGGER.info("{}",array.get(i));

    			ContentModel model = GsonUtils.fromJson(array.get(i).toString(),
    					ContentModel.class);
    			LOGGER.info("model : {}", model.toString());
        	}
        	

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

	@Test
	public void fectById() {
		try {
			String json = new Resty().json(String.format(FETCH_BY_ID_URL, Main.PORT, getResource(), 2)).object().toString();
			LOGGER.info("\n{}", json);
			ContentModel model = GsonUtils.fromJson(json,
					ContentModel.class);
			LOGGER.info("model : {}", model.toString());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	private String getResource() {
		return "content";
	}

	class A {
		public int a;
		public int b;

		public A() {
			this.a = 10;
			this.b = 101;
		}
	}
}
