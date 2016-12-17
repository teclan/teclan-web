package teclan.web;

import static spark.Spark.after;
import static spark.Spark.before;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import teclan.web.api.ContentApis;
import teclan.web.db.Database;

@Singleton
public class Application extends RestapiApplication {
    private final Logger LOG = LoggerFactory.getLogger(Application.class);

    @Inject
    private Database     database;
    @Inject
    private ContentApis  contentApis;

    @Override
    public void creatApis() {
        contentApis.initApis();
    }

    @Override
    public void filter() {
        before((request, response) -> {
            database.openDatabase();
        });
        after((request, response) -> {
            database.closeDatabase();
        });

    }

}
