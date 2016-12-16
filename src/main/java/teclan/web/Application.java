package teclan.web;

import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.get;

import java.util.Date;

import org.javalite.activejdbc.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import teclan.web.db.Database;
import teclan.web.db.model.ContentRecord;

@Singleton
public class Application extends RestapiApplication {
	private final Logger LOG = LoggerFactory.getLogger(Application.class);
	
	
	@Inject
	private Database database;

	@SuppressWarnings("deprecation")
	@Override
	public void creatApis() {
		  get("/hello", (req, res) -> {
			  ContentRecord.create("content","测试数据"+new Date().getSeconds()).save();
			  return "Hello World";
			  
		  });
	}
	
	
	@Override
	 public void filter(){
		   before((request, response) -> {
			   database.openDatabase();
			});
		   after((request, response) -> {
			   database.closeDatabase();
			});
	    	
	    }

}
