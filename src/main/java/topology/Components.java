package topology;

import java.util.Map;

public class Components {
	private String id;
	private String type;
	protected Map<String, Double>numData;
	protected Map<String, String>netList;
	public Components(String id,String type) {
		this.id=id;
		this.type=type;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setType(String type) {
		this.type=type;
	}
	public String getType() {
		return this.type;
	}
}
