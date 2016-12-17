package teclan.web.example;


import com.google.inject.AbstractModule;

import spark.servlet.SparkApplication;
import teclan.web.db.Database;
import teclan.web.provider.DatabaseProvider;

public class SeverModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Database.class).toProvider(DatabaseProvider.class);
    }
}

