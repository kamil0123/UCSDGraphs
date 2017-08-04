package roadgraph;

public class MapEdge {
	
	private MapNode start;
	private MapNode end;
	private Road road;
	
	MapEdge (MapNode start, MapNode end, Road roadInfo) {
		this.start = start;
		this.end = end;
		this.road = roadInfo;
	}
	
	MapEdge (MapNode start, MapNode end, Double distance, String roadName, String roadType) {
		Road roadInfo = new Road(roadName, roadType, distance);
		this.start = start;
		this.end = end;
		this.road = roadInfo;
	}
	
	MapNode getNeigbour(MapNode node) {
		if (node.equals(start))
			return end;
		else if (node.equals(end))
			return start;
		else
			return null;
	}
	
	Road getRoad() {
		return road;
	}
}
