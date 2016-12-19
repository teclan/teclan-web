package teclan.web;

import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.ipAddress;
import static spark.Spark.port;
import static spark.Spark.staticFiles;

import java.io.File;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import spark.servlet.SparkApplication;
import teclan.web.db.Database;
import teclan.web.media.MediaServiceApis;

public abstract class RestapiApplication implements SparkApplication {
    @Inject
    @Named("config.base-url.ip")
    private String           host;
    @Inject
    @Named("config.base-url.port")
    private int              port;
    @Inject
    @Named("config.media.public")
    private String           publicDir;

    @Inject
    private Database         database;
    @Inject
    private MediaServiceApis mediaServiceApis;

    @Override
    public void init() {
        ipAddress(host);
        port(port);
        defaultApis();
        creatApis();
        filter();
    }

    public abstract void creatApis();

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    private void defaultApis() {
        defaultServiceApis();
        defaultMediaServiceApis();
    }

    private void defaultServiceApis() {
        // Add something
    }

    private void defaultMediaServiceApis() {
        // staticFiles.location(getPublicDir());
        staticFiles.externalLocation(getPublicDir());
        mediaServiceApis.initApis();
    }

    public void filter() {
        before((request, response) -> {
            database.openDatabase();
        });
        after((request, response) -> {
            database.closeDatabase();
        });
    }

    private String getPublicDir() {
        String dir = System.getProperty("user.dir") + File.separator
                + publicDir;
        new File(dir).mkdirs();

        return dir;
    }
}
