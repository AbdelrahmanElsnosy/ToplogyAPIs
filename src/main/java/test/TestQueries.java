package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import API.TopologyApi;

public class TestQueries {
	@Test
	public void main() {
		TopologyApi topologyApi = new TopologyApi();
		boolean isSuccess = false;
		assertEquals(isSuccess, topologyApi.queryTopologies());
		assertEquals(isSuccess, topologyApi.queryDevices("top1"));
		assertEquals(isSuccess, topologyApi.queryDeviceWithNetlist("top1", "t1"));
		assertEquals(isSuccess, topologyApi.deleteTopology("top1"));
		topologyApi.readJson(".\\JSON files\\topology.json");
		isSuccess = true;
		assertEquals(isSuccess, topologyApi.queryTopologies());
		assertEquals(isSuccess, topologyApi.queryDevices("top1"));
		assertEquals(isSuccess, topologyApi.queryDeviceWithNetlist("top1", "t1"));
		assertEquals(isSuccess, topologyApi.deleteTopology("top1"));
	}

}
