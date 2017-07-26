package roadgraph;

import geography.GeographicPoint;

public class MapNode {
	
	public final GeographicPoint vertex;
	public final Road road;

	
	public MapNode(GeographicPoint vertex, Road road) {
		this.vertex = vertex;
		this.road = road;
	}
}
