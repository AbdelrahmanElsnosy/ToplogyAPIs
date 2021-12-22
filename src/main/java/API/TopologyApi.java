package API;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import topology.*;

public class TopologyApi {
	private List<Topology> topology;
	private List<Components> components;
	private JSONParser jsonParser = new JSONParser();

	public TopologyApi() {
		this.topology = new ArrayList<Topology>();
		this.components = new ArrayList<Components>();
	}
	/**
	 * this method is used to read a given json file and save it in the memory
	 * @param filename type string which is the name of the file
	 * @return true if the file read successfully false otherwise
	 */
	public boolean readJson(String filename) {
		try (FileReader fileReader = new FileReader(filename)){
			Topology topo;
			Object object = jsonParser.parse(fileReader);
			JSONObject topJsonObject = (JSONObject) object;
			String topId = (String) topJsonObject.get("id");
			JSONArray compJsonArray = (JSONArray) topJsonObject.get("components");
			for (int i = 0; i < compJsonArray.size(); i++) {
				JSONObject compJsonObject = (JSONObject) compJsonArray.get(i);
				String compType = (String) compJsonObject.get("type");
				String compId = (String) compJsonObject.get("id");
				if (compType.equals("resistor")) {
					Map<String, String> netlist = new HashMap<>();
					Map<String, Double> resistance = new HashMap<>();
					// read resistance data from resistance object
					JSONObject resistanceJsonObject = (JSONObject) compJsonObject.get("resistance");
					resistance.put("default", Double.parseDouble(resistanceJsonObject.get("default").toString()));
					resistance.put("min", Double.parseDouble(resistanceJsonObject.get("min").toString()));
					resistance.put("max", Double.parseDouble(resistanceJsonObject.get("max").toString()));
					// read netlist data from netlist object
					JSONObject netlJsonObject = (JSONObject) compJsonObject.get("netlist");
					netlist.put("t1", netlJsonObject.get("t1").toString());
					netlist.put("t2", netlJsonObject.get("t2").toString());
					Resistors resistors = new Resistors(compId, compType, netlist,resistance);
					this.components.add(resistors);

				} else {
					Map<String, String> netlist = new HashMap<>();
					Map<String, Double> numDaMap = new HashMap<>();
					// read resistance data from resistance object
					JSONObject numDataJsonObject = (JSONObject) compJsonObject.get("m(l)");
					numDaMap.put("default", Double.parseDouble(numDataJsonObject.get("default").toString()));
					numDaMap.put("min", Double.parseDouble(numDataJsonObject.get("min").toString()));
					numDaMap.put("max", Double.parseDouble(numDataJsonObject.get("max").toString()));
					// read netlist data from netlist object
					JSONObject netlJsonObject = (JSONObject) compJsonObject.get("netlist");
					netlist.put("drain", netlJsonObject.get("drain").toString());
					netlist.put("gate", netlJsonObject.get("gate").toString());
					netlist.put("source", netlJsonObject.get("source").toString());
					Nmos nmos = new Nmos(compId, compType, netlist, numDaMap);
					this.components.add(nmos);
				}
			}
			topo = new Topology(topId, this.components.toArray(new Components[this.components.size()]));
			this.topology.add(topo);
			this.components.clear();
			return true;
		} catch (FileNotFoundException e) {
			System.out.println("file Not found!");
			return false;
		} catch (IOException e) {
			System.out.println("unexpected error!");
			return false;

		} catch (org.json.simple.parser.ParseException e) {
			System.out.println("couldn't parse file!");
			return false;
		}
	}
	/**
	 * this method used to write topology in a json file.
	 * @param topo type of Topology which is the topology u need to write.
	 * @return true if the file written successfully false otherwise.
	 */
	@SuppressWarnings("unchecked")
	public boolean writeJson(Topology topo) {
		JSONObject topJsonObject = new JSONObject();
		topJsonObject.put("id", topo.getId());
		JSONArray componentsArrayJson = new JSONArray();
		Components[]componentsArray=topo.getComponents();
		for(int i=0;i<componentsArray.length;i++) {
			JSONObject compJsonObject = new JSONObject();
			compJsonObject.put("type", componentsArray[i].getType());
			compJsonObject.put("id", componentsArray[i].getId());
			if(componentsArray[i].getType().equals("resistor")) {
				JSONObject resistanceJsonObject = new JSONObject();
				Map<String, Double>numData = componentsArray[i].getNumData();
				resistanceJsonObject.put("default", numData.get("default"));
				resistanceJsonObject.put("min", numData.get("min"));
				resistanceJsonObject.put("max", numData.get("max"));
				compJsonObject.put("resistance", resistanceJsonObject);
				Map<String, String>netlist = componentsArray[i].getNetList();
				JSONObject netlistJsonObject = new JSONObject();
				netlistJsonObject.put("t1", netlist.get("t1"));
				netlistJsonObject.put("t2", netlist.get("t2"));
				compJsonObject.put("netlist", netlistJsonObject);

			}
			else {
				JSONObject m1JsonObject = new JSONObject();
				Map<String, Double>numData = componentsArray[i].getNumData();
				m1JsonObject.put("default", numData.get("default"));
				m1JsonObject.put("min", numData.get("min"));
				m1JsonObject.put("max", numData.get("max"));
				compJsonObject.put("m(l)", m1JsonObject);
				Map<String, String>netlist = componentsArray[i].getNetList();
				JSONObject netlistJsonObject = new JSONObject();
				netlistJsonObject.put("drain", netlist.get("drain"));
				netlistJsonObject.put("gate", netlist.get("gate"));
				netlistJsonObject.put("source", netlist.get("source"));
				compJsonObject.put("netlist", netlistJsonObject);
			}
			componentsArrayJson.add(compJsonObject);
		}
		topJsonObject.put("components", componentsArrayJson);
		try(FileWriter fileWriter = new FileWriter(".\\JSON files\\topologywrite.json")) {
			fileWriter.write(topJsonObject.toJSONString());
			fileWriter.flush();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	/**
	 * this method is used to get all topologies in the memory
	 * @return true if there are topology in the memory false otherwise
	 */
	public boolean queryTopologies() {
		if(this.topology.isEmpty()) {
			System.out.println("There is no topologies in the memory!");
			return false;
		}
		else {
			for(int i =0;i<this.topology.size();i++) {
				System.out.println("Topology found in the memory with ID: "+this.topology.get(i).getId());
			}
			return true;
		}
	}
	/**
	 * this method used to delete topology from the memory
	 * @param id type String which is the id of the topology to remove
	 * @return true if delete is successfull false otherwise
	 */
	public boolean deleteTopology(String id) {
		if(this.topology.isEmpty()) {
			System.out.println("There is no topologies in the memory!");
			return false;
		}
		else {
			for(int i =0;i<this.topology.size();i++) {
				if(this.topology.get(i).getId().equals(id)) {
					this.topology.remove(i);
					System.out.println("Topology found and deleted successfully");
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * this method use to get all devices in a given topology
	 * @param id type String which is the id of the topology
	 * @return true if there are topologies in the memory and id is valid false otherwise
	 */
	public boolean queryDevices(String id) {
		if(this.topology.isEmpty()) {
			System.out.println("There is no topologies in the memory!");
			return false;
		}
		else {
			for(int i =0;i<this.topology.size();i++) {
				if(this.topology.get(i).getId().equals(id)) {
					System.out.println("Topology found with ID: "+id);
					System.out.println("Devices within the topologies are: ");
					Components [] compons=this.topology.get(i).getComponents();
					for(int j=0;j<compons.length;j++) {
						System.out.println("device # "+(j+1)+ " is " +compons[j].getType());
					}
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * this method is used to get Query about which devices are connected to a given netlist node in a given topology.
	 * @param id type String which is the id of the topology
	 * @param netist type String which is the netlist
	 * @return true if it sucessfully found the netlist false otherwise
	 */
	public boolean queryDeviceWithNetlist(String id,String netist){
		if(this.topology.isEmpty()) {
			System.out.println("There is no topologies in the memory!");
			return false;
		}
		else {
			String type = null;
			if(netist.equals("t1") || netist.equals("t2") ) {
				type = "resistor";
			}
			else if(netist.equals("drain") || netist.equals("gate") || netist.equals("source")) {
				type = "nmos";
			}
			else {
				System.out.println("There is no such netlist!");
				return false;
			}
			for(int i =0;i<this.topology.size();i++) {
				if(this.topology.get(i).getId().equals(id)) {
					Components [] compons=this.topology.get(i).getComponents();
					for(int j=0;j<compons.length;j++) {
						if(type.equals(compons[j].getType())) {
							System.out.println("The netlist is connected to "+compons[j].getType()+ " : " +compons[j].getNetList().get(netist));
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	public List<Topology> geTopologies(){
		return this.topology;
	}
}
