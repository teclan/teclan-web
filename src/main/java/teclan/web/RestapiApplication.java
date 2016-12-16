package teclan.web;

import static spark.Spark.get;
import static spark.Spark.*;


import spark.servlet.SparkApplication;

public abstract class RestapiApplication implements SparkApplication {

    @Override
    public void init() {
    	creatApis();
    	filter();
    }

    public abstract void creatApis();
    public abstract void filter();

}
