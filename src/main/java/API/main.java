package API;

import static org.junit.Assert.assertEquals;

import topology.Components;
import topology.Topology;

public class main {

	public static void main(String[] args) {
		TopologyApi topologyApi = new TopologyApi();
		topologyApi.readJson(".\\JSON files\\topology.json");
		assertEquals(true, topologyApi.readJson(".\\JSON files\\topology.json"));
		Topology topology = topologyApi.geTopologies().get(0);
		topologyApi.writeJson(topology);
		topologyApi.queryDevices("top1");
		topologyApi.queryDeviceWithNetlist("top1", "t1");
		Components []components = topology.getComponents();
		System.out.println(topology.getId());
		for(int i=0;i<components.length;i++) {
			System.out.println(components[i].getType());
		}
	}

}
