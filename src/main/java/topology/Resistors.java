package topology;

import java.util.Map;

public class Resistors extends Components {

	public Resistors(String id, String type,Map<String, String>netList,Map<String, Double>numData) {
		super(id, type);
		// TODO Auto-generated constructor stub
		this.netList=netList;
		this.numData=numData;
	}
	public void setNetList(Map<String, String>netList) {
		this.netList=netList;
	}
	public Map<String, String> getNetList() {
		return this.netList;
	}
	public void setNumData(Map<String, Double>numData) {
		this.numData=numData;
	}
	public Map<String, Double>getNumData(){
		return this.numData;
	}
}
