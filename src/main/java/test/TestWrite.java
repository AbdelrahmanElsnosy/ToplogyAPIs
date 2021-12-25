package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import API.TopologyApi;

public class TestWrite {
@Test
	public void main() {
		//read the same file and write it back as json file.
		TopologyApi topologyApi = new TopologyApi();
		topologyApi.readJson(".\\JSON files\\topology.json");
		boolean isWriteExpected = true;
		assertEquals(isWriteExpected, topologyApi.writeJson(topologyApi.geTopologies().get(0)));
		int expectedSize = 2;
		assertEquals(expectedSize,topologyApi.geTopologies().get(0).getComponents().length);
	}

}
