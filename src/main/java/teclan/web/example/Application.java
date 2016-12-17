package teclan.web.example;

import static spark.Spark.after;
import static spark.Spark.before;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import teclan.web.RestapiApplication;
import teclan.web.db.Database;
import teclan.web.example.api.achieve.DefaultContentServerApis;

@Singleton
public class Application extends RestapiApplication {
    private final Logger             LOG = LoggerFactory
            .getLogger(Application.class);

    @Inject
    private Database                 database;
    @Inject
    private DefaultContentServerApis contentApis;

    @Override
    public void creatApis() {
        contentApis.initApis();
    }

    @Override
    public void filter() {
        before((request, response) -> {
            database.openDatabase();
            // ContentRecord.create("content", "测试" + new Date()).save();
        });
        after((request, response) -> {
            database.closeDatabase();
        });

    }

}
