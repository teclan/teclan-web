package teclan.web;

import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.ipAddress;
import static spark.Spark.port;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.typesafe.config.Config;

import spark.servlet.SparkApplication;
import teclan.web.db.Database;

public abstract class RestapiApplication implements SparkApplication {
	@Inject
	@Named("config.base-url.ip")
	private String host;
	@Inject
	@Named("config.base-url.port")
	private int port;

	@Inject
	private Database database;

	@Override
	public void init() {
		ipAddress(host);
		port(port);
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
