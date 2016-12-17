package teclan.web;

import static spark.Spark.ipAddress;
import static spark.Spark.port;

import spark.servlet.SparkApplication;

public abstract class RestapiApplication implements SparkApplication {
    private String host = "0.0.0.0";
    private int    port = 3770;

    @Override
    public void init() {
        ipAddress(host);
        port(port);
        creatApis();
        filter();
    }

    public abstract void creatApis();

    public abstract void filter();

    /**
     * 设置允许访问的 IP，默认所有 IP 军可访问
     * 
     * @param host
     */
    public void setHost(String host) {
        this.host = host;

    }

    /**
     * 设置服务端口，默认端口 3770
     * 
     * @param port
     */
    public void setPort(int port) {
        this.port = port;

    }

}
