package teclan.web.api;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.util.List;

import com.google.inject.Inject;

import teclan.utils.GsonUtils;
import teclan.web.db.model.ContentRecord;
import teclan.web.db.service.ContentRecordService;

public class ContentApis implements ServiceApis {

    @Inject
    private ContentRecordService service;

    @Override
    public void initApis() {

        get("/all", (req, res) -> {

            List<ContentRecord> results = service.all();
            System.out.println(
                    "============= " + GsonUtils.toJsonForJdbcRecord(results));

            return GsonUtils.toJsonForJdbcRecord(results.get(0));

        });

        get("/new", (req, res) -> {
            return "Hello World";

        });

        post("/new", (req, res) -> {
            return "Hello World";

        });

        put("/sys", (req, res) -> {
            return "Hello World";

        });

        delete("/delete/:id", (req, res) -> {
            return "Hello World";

        });
    }

}
