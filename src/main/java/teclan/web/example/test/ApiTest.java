package teclan.web.example.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import teclan.web.example.Main;
import us.monoid.json.JSONArray;
import us.monoid.json.JSONObject;
import us.monoid.web.Resty;

// 测试之前先执行 teclan.web.example 的 Main.class
public class ApiTest {
    private final Logger LOGGER          = LoggerFactory
            .getLogger(ApiTest.class);
    private final String GET_ALL_URL     = "http://localhost:%d/%s/all";
    private final String FETCH_BY_ID_URL = "http://localhost:%d/%s/fetch/%d";

    @Test
    public void getAll() {
        try {

            String text = new Resty()
                    .text(String.format(GET_ALL_URL, Main.PORT, getResource()))
                    .toString();

            LOGGER.info("\n{}", text);
            JSONObject obj = new JSONObject(text);
            LOGGER.info("\n==={}", ((JSONArray) obj.get("content")).get(0));

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Test
    public void fectById() {
        try {

            String text = new Resty().text(
                    String.format(FETCH_BY_ID_URL, Main.PORT, getResource(), 3))
                    .toString();

            LOGGER.info("\n{}", text);

            JSONObject obj = new JSONObject(text);

            LOGGER.info("\n==={}", (((JSONArray) obj.get("content")).get(0)));

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private String getResource() {
        return "content";
    }
}
