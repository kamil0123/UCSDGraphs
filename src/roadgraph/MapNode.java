package roadgraph;

import geography.GeographicPoint;

public class MapNode {
	
	public final GeographicPoint vertex;
	public final String streetName;
	public final String streetType;
	
	public MapNode(GeographicPoint vertex, String name, String type) {
		this.vertex = vertex;
		this.streetName = name;
		this.streetType = type;
	}
}
