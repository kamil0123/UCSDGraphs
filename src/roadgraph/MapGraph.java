/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 *         A class which represents a graph of geographic locations Nodes in the
 *         graph are intersections between
 *
 */
public class MapGraph {
	// DONE: Add your member variables here in WEEK 3
	private Map<GeographicPoint, MapNode> nodes;
	private Set<MapEdge> edges;

	/**
	 * Create a new empty MapGraph
	 */
	public MapGraph() {
		// DONE: Implement in this constructor in WEEK 3
		this.nodes = new HashMap<GeographicPoint, MapNode>();
		this.edges = new HashSet<MapEdge>();
	}

	/**
	 * Get the number of vertices (road intersections) in the graph
	 * 
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices() {
		// DONE: Implement this method in WEEK 3
		return nodes.values().size();
	}

	/**
	 * Return the intersections, which are the vertices in this graph.
	 * 
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices() {
		// DONE: Implement this method in WEEK 3
		Set<GeographicPoint> set = new HashSet<GeographicPoint>();
		set.addAll(nodes.keySet());
		return set;
	}

	/**
	 * Get the number of road segments in the graph
	 * 
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges() {
		// DONE: Implement this method in WEEK 3
		return edges.size();
	}

	/**
	 * Add a node corresponding to an intersection at a Geographic Point If the
	 * location is already in the graph or null, this method does not change the
	 * graph.
	 * 
	 * @param location
	 *            The location of the intersection
	 * @return true if a node was added, false if it was not (the node was
	 *         already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location) {
		// DONE: Implement this method in WEEK 3
		if (location == null || nodes.containsKey(location)) {
			return false;
		}

		MapNode newNode = new MapNode(location);
		nodes.put(location, newNode);
		return true;
	}

	/**
	 * Adds a directed edge to the graph from pt1 to pt2. Precondition: Both
	 * GeographicPoints have already been added to the graph
	 * 
	 * @param from
	 *            The starting point of the edge
	 * @param to
	 *            The ending point of the edge
	 * @param roadName
	 *            The name of the road
	 * @param roadType
	 *            The type of the road
	 * @param length
	 *            The length of the road, in km
	 * @throws IllegalArgumentException
	 *             If the points have not already been added as nodes to the
	 *             graph, if any of the arguments is null, or if the length is
	 *             less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName, String roadType, double length)
			throws IllegalArgumentException {

		// DONE: Implement this method in WEEK 3
		if (!geographicPointVeryfied(from) || !geographicPointVeryfied(to)
				|| !roadParamsVeryfied(roadName, roadType, length))
			throw new IllegalArgumentException();

		MapNode nodeFrom = nodes.get(from);
		MapNode nodeTo = nodes.get(to);
		Road road = new Road(roadName, roadType, length);

		MapEdge edge = new MapEdge(nodeFrom, nodeTo, road);
		edges.add(edge);
		nodeFrom.addEdge(edge);
	}

	private boolean geographicPointVeryfied(GeographicPoint point) {
		if (point == null || !nodes.containsKey(point))
			return false;
		else
			return true;
	}

	private boolean roadParamsVeryfied(String roadName, String roadType, double length) {
		if (roadName == null || roadType == null || length < 0)
			return false;
		else
			return true;
	}

	/**
	 * Find the path from start to goal using breadth first search
	 * 
	 * @param start
	 *            The starting location
	 * @param goal
	 *            The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *         path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		Consumer<GeographicPoint> temp = (x) -> {
		};
		return bfs(start, goal, temp);
	}

	/**
	 * Find the path from start to goal using breadth first search
	 * 
	 * @param start
	 *            The starting location
	 * @param goal
	 *            The goal location
	 * @param nodeSearched
	 *            A hook for visualization. See assignment instructions for how
	 *            to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *         path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal,
			Consumer<GeographicPoint> nodeSearched) {
		// DONE: Implement this method in WEEK 3

		if (!geographicPointVeryfied(start) || !geographicPointVeryfied(goal))
			throw new IllegalArgumentException();

		Queue<MapNode> pointsToSearch = new LinkedList<MapNode>();
		Set<MapNode> checked = new HashSet<MapNode>();
		Map<MapNode, MapNode> parentsMap = new HashMap<MapNode, MapNode>();

		MapNode startNode = nodes.get(start);
		MapNode goalNode = nodes.get(goal);

		pointsToSearch.add(startNode);
		MapNode current = null;

		while (!pointsToSearch.isEmpty()) {
			current = pointsToSearch.poll();

			// Hook for visualization. See writeup.
			nodeSearched.accept(current.getLocation());

			if (goalIsFound(current, goalNode)) {
				return getPath(startNode, goalNode, parentsMap);
			}

			for (MapNode node : current.getNeigbours()) {
				if (checked.contains(node)) {
					continue;
				}
				checked.add(node);
				pointsToSearch.add(node);
				parentsMap.put(node, current);
			}
		}
		return null;
	}

	private boolean goalIsFound(MapNode current, MapNode goalNode) {
		if (current.getLocation().x == goalNode.getLocation().x && current.getLocation().y == goalNode.getLocation().y)
			return true;
		else
			return false;
	}

	private List<GeographicPoint> getPath(MapNode startNode, MapNode endNode, Map<MapNode, MapNode> parentsMap) {
		List<GeographicPoint> path = new LinkedList<GeographicPoint>();

		while (!endNode.equals(startNode)) {
			path.add(0, endNode.getLocation());
			endNode = parentsMap.get(endNode);
		}
		path.add(0, startNode.getLocation());
		return path;
	}

	/**
	 * Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start
	 *            The starting location
	 * @param goal
	 *            The goal location
	 * @return The list of intersections that form the shortest path from start
	 *         to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
		Consumer<GeographicPoint> temp = (x) -> {
		};
		return dijkstra(start, goal, temp);
	}

	/**
	 * Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start
	 *            The starting location
	 * @param goal
	 *            The goal location
	 * @param nodeSearched
	 *            A hook for visualization. See assignment instructions for how
	 *            to use it.
	 * @return The list of intersections that form the shortest path from start
	 *         to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal,
			Consumer<GeographicPoint> nodeSearched) {
		// DONE: Implement this method in WEEK 4

		if (!geographicPointVeryfied(start) || !geographicPointVeryfied(goal))
			throw new IllegalArgumentException();

		PriorityQueue<MapNode> pointsToSearch = new PriorityQueue<MapNode>();
		Set<MapNode> checked = new HashSet<MapNode>();
		Map<MapNode, MapNode> parentsMap = new HashMap<MapNode, MapNode>();

		for (MapNode node : nodes.values()) {
			node.setDistance(Double.MAX_VALUE);
		}

		MapNode startNode = nodes.get(start);
		MapNode goalNode = nodes.get(goal);
		startNode.setDistance(0.0);

		pointsToSearch.add(startNode);
		MapNode current = null;

		while (!pointsToSearch.isEmpty()) {
			current = pointsToSearch.poll();

			if (checked.contains(current))
				continue;
			else {

				checked.add(current);

				// Hook for visualization. See writeup.
				nodeSearched.accept(current.getLocation());

				if (goalIsFound(current, goalNode)) {
					return getPath(startNode, goalNode, parentsMap);
				}
				
				HashMap<MapNode, Double> curDistances = new HashMap<MapNode, Double>();
				for (MapEdge edge : current.getEdges()) {
					curDistances.put(edge.getNeigbour(current), edge.getRoad().getLength());
				}

				for (MapNode node : current.getNeigbours()) {
					if (checked.contains(node)) {
						continue;
					}
					Double nodesDistance = current.getDistance() + curDistances.get(node); 
					if (nodesDistance < node.getDistance()) {
						node.setDistance(nodesDistance);
						parentsMap.put(node, current);
						pointsToSearch.add(node);
					}
				}
			}
		}
		return null;
	}

	/**
	 * Find the path from start to goal using A-Star search
	 * 
	 * @param start
	 *            The starting location
	 * @param goal
	 *            The goal location
	 * @return The list of intersections that form the shortest path from start
	 *         to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		Consumer<GeographicPoint> temp = (x) -> {
		};
		return aStarSearch(start, goal, temp);
	}

	/**
	 * Find the path from start to goal using A-Star search
	 * 
	 * @param start
	 *            The starting location
	 * @param goal
	 *            The goal location
	 * @param nodeSearched
	 *            A hook for visualization. See assignment instructions for how
	 *            to use it.
	 * @return The list of intersections that form the shortest path from start
	 *         to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal,
			Consumer<GeographicPoint> nodeSearched) {
		// TODO: Implement this method in WEEK 4

		if (!geographicPointVeryfied(start) || !geographicPointVeryfied(goal))
			throw new IllegalArgumentException();

		PriorityQueue<MapNode> pointsToSearch = new PriorityQueue<MapNode>();
		Set<MapNode> checked = new HashSet<MapNode>();
		Map<MapNode, MapNode> parentsMap = new HashMap<MapNode, MapNode>();

		for (MapNode node : nodes.values()) {
			node.setDistance(Double.MAX_VALUE);
		}

		MapNode startNode = nodes.get(start);
		MapNode goalNode = nodes.get(goal);
		startNode.setDistance(0.0);

		pointsToSearch.add(startNode);
		MapNode current = null;

		while (!pointsToSearch.isEmpty()) {
			current = pointsToSearch.poll();

			if (checked.contains(current))
				continue;
			else {

				checked.add(current);

				// Hook for visualization. See writeup.
				nodeSearched.accept(current.getLocation());

				if (goalIsFound(current, goalNode)) {
					return getPath(startNode, goalNode, parentsMap);
				}
				
				HashMap<MapNode, Double> curDistances = new HashMap<MapNode, Double>();
				for (MapEdge edge : current.getEdges()) {
					curDistances.put(edge.getNeigbour(current), edge.getRoad().getLength());
				}

				for (MapNode node : current.getNeigbours()) {
					if (checked.contains(node)) {
						continue;
					}
					Double predictedDistance = node.getDistanceToOtherNode(goalNode);
					Double nodesDistance = current.getDistance() + curDistances.get(node); 
					// TODO
					// finish distance calculations
					if (nodesDistance + predictedDistance < node.getDistance()) {
						node.setDistance(nodesDistance);
						parentsMap.put(node, current);
						pointsToSearch.add(node);
					}
				}
			}
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		System.out.println("DONE.");
		
		MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		
		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5"); 
		List<GeographicPoint> testRoute = simpleTestMap.dijkstra(testStart,testEnd);
		System.out.println("Dijkstra: " + testRoute.size());
		
		

		// You can use this method for testing.

		/*
		 * Here are some test cases you should try before you attempt the Week 3
		 * End of Week Quiz, EVEN IF you score 100% on the programming
		 * assignment.
		 */
		/*
		 * MapGraph simpleTestMap = new MapGraph();
		 * GraphLoader.loadRoadMap("data/testdata/simpletest.map",
		 * simpleTestMap);
		 * 
		 * GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		 * GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		 * 
		 * System.out.println(
		 * "Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5"
		 * ); List<GeographicPoint> testroute =
		 * simpleTestMap.dijkstra(testStart,testEnd); List<GeographicPoint>
		 * testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
		 * 
		 * 
		 * MapGraph testMap = new MapGraph();
		 * GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		 * 
		 * // A very simple test using real data testStart = new
		 * GeographicPoint(32.869423, -117.220917); testEnd = new
		 * GeographicPoint(32.869255, -117.216927); System.out.println(
		 * "Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		 * testroute = testMap.dijkstra(testStart,testEnd); testroute2 =
		 * testMap.aStarSearch(testStart,testEnd);
		 * 
		 * 
		 * // A slightly more complex test using real data testStart = new
		 * GeographicPoint(32.8674388, -117.2190213); testEnd = new
		 * GeographicPoint(32.8697828, -117.2244506); System.out.println(
		 * "Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		 * testroute = testMap.dijkstra(testStart,testEnd); testroute2 =
		 * testMap.aStarSearch(testStart,testEnd);
		 */

		/* Use this code in Week 3 End of Week Quiz */
		/*
		 * MapGraph theMap = new MapGraph(); System.out.print(
		 * "DONE. \nLoading the map...");
		 * GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		 * System.out.println("DONE.");
		 * 
		 * GeographicPoint start = new GeographicPoint(32.8648772,
		 * -117.2254046); GeographicPoint end = new GeographicPoint(32.8660691,
		 * -117.217393);
		 * 
		 * 
		 * List<GeographicPoint> route = theMap.dijkstra(start,end);
		 * List<GeographicPoint> route2 = theMap.aStarSearch(start,end);
		 * 
		 */

	}

}
