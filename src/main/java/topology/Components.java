package topology;

import java.util.Map;

public class Components {
	private String id;
	private String type;
	protected Map<String, Double>numData;
	protected Map<String, String>netList;
	public Components(String id,String type) {
		// TODO Auto-generated constructor stub
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
	public Map<String, Double> getNumData() {
		return numData;
	}
	public void setNumData(Map<String, Double> numData) {
		this.numData = numData;
	}
	public Map<String, String> getNetList() {
		return netList;
	}
	public void setNetList(Map<String, String> netList) {
		this.netList = netList;
	}
}
