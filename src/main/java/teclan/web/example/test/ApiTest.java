package teclan.web.example.test;

import static us.monoid.web.Resty.form;
import static us.monoid.web.Resty.put;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import teclan.utils.GsonUtils;
import teclan.web.example.Main;
import us.monoid.json.JSONArray;
import us.monoid.json.JSONException;
import us.monoid.json.JSONObject;
import us.monoid.web.Content;
import us.monoid.web.Resty;

// 测试之前先执行 teclan.web.example 的 Main.class
public class ApiTest {
	private final Logger LOGGER = LoggerFactory.getLogger(ApiTest.class);
	private final String GET_ALL_URL = "http://localhost:%d/%s/all";
	private final String FETCH_BY_ID_URL = "http://localhost:%d/%s/fetch/%d";
	private final String FETCH_WITH_QUERY_URL = "http://localhost:%d/%s/fetch";
	private final String ADD_RECORD_WITH_POST_URL = "http://localhost:%d/%s/new";
	private final String ADD_RECORD_WITH_PUT_URL = "http://localhost:%d/%s/new";
	private final String SYS_RECORD_BY_ID_URL = "http://localhost:%d/%s/sys/%d";

	@Test
	public void sysById() {

		ContentModel model = new ContentModel();
		model.id=1;
		model.name="lu fei";
		Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

		try {
			JSONObject object = new JSONObject();
			object.put(getResource(), GSON.toJson(model));

			new Resty().text(String.format(SYS_RECORD_BY_ID_URL, Main.PORT, getResource(),model.id),
					put(new Content("application/json; charset=utf-8", object.toString().getBytes("UTF-8"))));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void newWithPut() {
		ContentModel model = new ContentModel();

		Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

		try {
			JSONObject object = new JSONObject();
			object.put(getResource(), GSON.toJson(model));

			new Resty().text(String.format(ADD_RECORD_WITH_PUT_URL, Main.PORT, getResource()),
					put(new Content("application/json; charset=utf-8", object.toString().getBytes("UTF-8"))));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void newWithPost() {
		try {
			new Resty().text(String.format(ADD_RECORD_WITH_POST_URL, Main.PORT, getResource()),
					form("name=Teclan&content=lvzaotou"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void fetchWithQuery() {
		try {
			LOGGER.info("{}",
					new Resty().json(String.format(FETCH_WITH_QUERY_URL, Main.PORT, getResource()), form("page=all"))
							.object().toString());

			LOGGER.info("\n=================");

			LOGGER.info("{}", new Resty().json(String.format(FETCH_WITH_QUERY_URL, Main.PORT, getResource()),
					form("page=1&limit=2&content=测试")).object().toString());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void getAll() {
		try {
			JSONObject json = new Resty().json(String.format(GET_ALL_URL, Main.PORT, getResource())).object();
			LOGGER.info("=={}", json);
			JSONArray array = ((JSONArray) json.get("content"));

			for (int i = 0; i < array.length(); i++) {
				LOGGER.info("{}", array.get(i));

				ContentModel model = GsonUtils.fromJson(array.get(i).toString(), ContentModel.class);
				LOGGER.info("model : {}", model.toString());
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	@Test
	public void fectById() {
		try {
			String json = new Resty().json(String.format(FETCH_BY_ID_URL, Main.PORT, getResource(), 2)).object()
					.toString();
			LOGGER.info("\n{}", json);
			ContentModel model = GsonUtils.fromJson(json, ContentModel.class);
			LOGGER.info("model : {}", model.toString());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	private String getResource() {
		return "content";
	}
}
