package teclan.web.example.test;

import java.util.Date;

import com.google.gson.annotations.Expose;

public class ContentModel {
	private long id;
	private String name;
	private String content;
	private String description;
	private String createdAt;
	private String updatedAt;

	
	public ContentModel(){
		name = "test-name";
		content="测试 "+new Date();
		description="无";
	}
	
	
	public String toString() {
		return String.format("id = %s,name = %s,content = %s,description = %s,createAt = %s,updatedAt = %s", id, name,
				content, description,createdAt,updatedAt);
	}
}
