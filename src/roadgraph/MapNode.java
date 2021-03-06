package roadgraph;

import java.util.HashSet;
import java.util.Set;

import geography.GeographicPoint;

public class MapNode implements Comparable<MapNode> {

	// location - position on map of this node;
	// edges - list of edges of this node;
	private GeographicPoint location;
	private Set<MapEdge> edges;

	private Double predictedDistance;
	private Double distance;

	public MapNode(GeographicPoint location) {
		this.location = location;
		this.edges = new HashSet<MapEdge>();
		predictedDistance = 0.0;
		distance = 0.0;
	}

	void addEdge(MapEdge edge) {
		edges.add(edge);
	}
	
	@Override
	public int compareTo(MapNode newMapNode) {
		Double thisNodesDistance = this.getPredictedDistance() + this.getDistance();
		Double byNewMapNodeDistance = newMapNode.getPredictedDistance() + newMapNode.getDistance();
		// TODO Auto-generated method stub
		return thisNodesDistance.compareTo(byNewMapNodeDistance);
	}

	Set<MapNode> getNeigbours() {
		Set<MapNode> neigbours = new HashSet<MapNode>();
		for (MapEdge edge : edges) {
			neigbours.add(edge.getNeigbour(this));
		}
		return neigbours;
	}

	public GeographicPoint getLocation() {
		return location;
	}

	public Set<MapEdge> getEdges() {
		return edges;
	}

	public Double getPredictedDistance() {
		return predictedDistance;
	}

	public void setPredictedDistance(Double predictedDistance) {
		this.predictedDistance = predictedDistance;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}
	
	public Double getDistanceToOtherNode(MapNode other) {
		GeographicPoint otherLocation = other.getLocation();
		return location.distance(otherLocation);
	}
}
