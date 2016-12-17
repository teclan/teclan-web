package teclan.web.example.test;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class ContentModel {
	public long id;
	public String name;
	public String content;
	public String description;
	@JsonIgnoreProperties("created_at")
	public String createdAt;
	@JsonIgnoreProperties("updated_at")
	public String updatedAt;

	public String toString() {
		return String.format("id = %s,name = %s,content = %s,description = %s,createAt = %s,updatedAt = %s", id, name,
				content, description,createdAt,updatedAt);
	}
}
