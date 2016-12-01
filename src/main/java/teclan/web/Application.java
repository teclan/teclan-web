package teclan.web;

import static spark.Spark.get;

import spark.servlet.SparkApplication;

public class Application implements SparkApplication {

    @Override
    public void init() {
        get("/hello", (req, res) -> "Hello World");
        get("/hello/:name", (request, response) -> {
            return "Hello: " + request.params(":name");
        });

    }

}
