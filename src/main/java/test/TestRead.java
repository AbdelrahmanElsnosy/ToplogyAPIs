package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import API.TopologyApi;

public class TestRead {

	@Test
	public void main() {
		TopologyApi topologyApi = new TopologyApi();
		boolean isReadExpected = true;
		assertEquals(isReadExpected,topologyApi.readJson(".\\JSON files\\topology.json"));
		int expectedSize = 2;
		assertEquals(expectedSize,topologyApi.geTopologies().get(0).getComponents().length);
	}

}
