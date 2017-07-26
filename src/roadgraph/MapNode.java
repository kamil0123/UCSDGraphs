package roadgraph;

import geography.GeographicPoint;

public class MapNode {
	
	public final GeographicPoint vertex;
	public final String roadName;
	public final String roadType;
	
	public MapNode(GeographicPoint vertex, String roadName, String roadType) {
		this.vertex = vertex;
		this.roadName = roadName;
		this.roadType = roadType;
	}
}
