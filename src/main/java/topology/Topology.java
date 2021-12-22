package topology;

public class Topology {
	private String id;
	private Components[] components;
	public Topology(String id,Components[] components) {
		this.id=id;
		this.components =components;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Components[] getComponents() {
		return components;
	}
	public void setComponents(Components[] components) {
		this.components = components;
	}
}