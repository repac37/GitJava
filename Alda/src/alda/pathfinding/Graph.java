package alda.pathfinding;

import java.util.*;

// the graph object and dijkstra algorithm
public class Graph<T extends Comparable<T>> {

	private Map<T, Node<T>> nodeMap = new HashMap<>();
	private Map<Integer, Edge<T>> edgeMap = new TreeMap<>();

	public boolean add(T node) {
		if (node == null) {
			return false;
		}
		nodeMap.put(node, new Node<T>(node));
		return true;

	}

	public boolean add(T node, int x, int y) {
		if (node == null) {
			return false;
		}
		nodeMap.put(node, new Node<T>(node, x, y));
		return true;

	}

	public boolean add(T node, int x, int y, boolean sub, boolean bus) {
		if (node == null) {
			return false;
		}
		nodeMap.put(node, new Node<T>(node, x, y, sub, bus));
		return true;

	}

	public boolean connect(T node1, T node2, int distance) {
		if (node1 == null || node2 == null) {
			throw new NullPointerException("Either of the 2 nodes is null.");
		}
		if (distance < 0) {
			throw new IllegalArgumentException(" The distance cannot be negative. ");
		}
		Node<T> n1, n2;

		if ((n1 = nodeMap.get(node1)) == null || (n2 = nodeMap.get(node2)) == null) {
			throw new NullPointerException("Either of the 2 nodes is null.");
		}

		Edge<T> edge = new Edge<T>(n1, n2, distance);

		if (edgeMap.containsKey(edge.hashCode())) {
			edge.setDistance(distance);
			return true;
		}
		edgeMap.put(edge.hashCode(), edge);
		n1.addToAdj(n2);
		n2.addToAdj(n1);

		return true;
	}

	public boolean connect(T node1, T node2, int distance, boolean bus) {
		if (node1 == null || node2 == null) {
			throw new NullPointerException("Either of the 2 nodes is null.");
		}
		if (distance < 0) {
			throw new IllegalArgumentException(" The distance cannot be negative. ");
		}
		Node<T> n1, n2;

		if ((n1 = nodeMap.get(node1)) == null || (n2 = nodeMap.get(node2)) == null) {
			throw new NullPointerException("Either of the 2 nodes is null.");
		}

		Edge<T> edge = new Edge<T>(n1, n2, distance, bus);

		if (edgeMap.containsKey(edge.hashCode())) {
			edge.setDistance(distance);
			return true;
		}
		edgeMap.put(edge.hashCode(), edge);
		n1.addToAdj(n2);
		n2.addToAdj(n1);

		return true;
	}

	public boolean isConnected(Node<T> n1, Node<T> n2) {
		return edgeMap.containsKey(egdeHashCodeGenerator(n1, n2));
	}

	public Map<T, Node<T>> getAllNodes() {
		return nodeMap;
	}

	public List<Edge<T>> getNodesEdges(T node) {
		Node<T> realNode = nodeMap.get(node);
		List<Edge<T>> edges = new ArrayList<>();
		for (Node<T> n : realNode.getAdj()) {
			Edge<T> edge = edgeMap.get(egdeHashCodeGenerator(realNode, n));
			edges.add(edge);
		}
		return edges;
	}

	private int egdeHashCodeGenerator(Node<T> n1, Node<T> n2) {
		return (n1.getValue().hashCode() * n2.getValue().hashCode()) % 911;
	}

	public int getNodesEdgeDistance(Node<T> n1, Node<T> n2) {
		return edgeMap.get(egdeHashCodeGenerator(n1, n2)).getDistance();
	}

	public int getNrOfNodes() {
		return nodeMap.size();
	}

	public Node<T> getNode(T node) {
		return nodeMap.get(node);
	}

	public void printPath(T node) {
		printPath(nodeMap.get(node));

	}

	private void printPath(Node<T> node) {
		if (node.getPath() != null) {
			printPath(node.getPath());
			System.out.println(" to ");
		}
		System.out.println(node);
	}

	public class TCompator implements Comparator<T> {
		@Override
		public int compare(T n1, T n2) {
			return n1.compareTo(n2);
		}

	}

	@Override
	public String toString() {
		Set<T> v = new TreeSet<>();
		v.addAll(nodeMap.keySet());
		StringBuilder sb = new StringBuilder("-----------------------------\n");
		sb.append("v\tknown\td:v\tp:v\n");
		sb.append("-----------------------------\n");
		for (T n : v) {
			Node<T> tmp = this.getNode(n);
			sb.append(tmp.getValue() + "\t" + ((tmp.isKnown() == true) ? "T" : "F") + "\t"
					+ ((tmp.getDistance() == Integer.MAX_VALUE) ? Character.toString('\u221E') : tmp.getDistance())
					+ "\t" + ((tmp.getPath() == null) ? 0 : tmp.getPath()) + "\n");
		}
		sb.append("-----------------------------\n");
		return sb.toString();
	}

	public void createSubTimeTable(T groundZero) {
		Node<T> zero = nodeMap.get(groundZero);
		if (zero.getStation().isSubwayStation()) {
			zero.getStation().setSub(new Schedule());
			zero.getStation().setSubTimeSet(true);
			createSubTimeTable(zero, 0);
		}

	}

	private void createSubTimeTable(Node<T> current, int offset) {

		if (current.getAdj() != null) {
			for (Node<T> adj : current.getAdj()) {
				if (!adj.getStation().isSubTimeSet() && !edgeMap.get(egdeHashCodeGenerator(adj, current)).isBus()) {
					if (adj.getStation().isSubwayStation()) {
						adj.getStation().setSub(new Schedule(getNodesEdgeDistance(adj, current) + offset));
						adj.getStation().setSubTimeSet(true);
						createSubTimeTable(adj, getNodesEdgeDistance(adj, current) + offset);
					}
				}
			}
		}
	}

	public void createBusTimeTable(T groundZero) {
		Node<T> zero = nodeMap.get(groundZero);
		if (zero.getStation().isBusStation()) {
			zero.getStation().setBus(new Schedule(0,true));
			zero.getStation().setBusTimeSet(true);
			createBusTimeTable(zero, 0);
		}

	}
	private void createBusTimeTable(Node<T> current, int offset) {
		if (current.getAdj() != null) {
			for (Node<T> adj : current.getAdj()) {
				if (!adj.getStation().isBusTimeSet() && edgeMap.get(egdeHashCodeGenerator(adj, current)).isBus()) {
					if (adj.getStation().isBusStation()) {
						adj.getStation().setBus(new Schedule(getNodesEdgeDistance(adj, current) + offset,true));
						adj.getStation().setBusTimeSet(true);
						createBusTimeTable(adj, getNodesEdgeDistance(adj, current) + offset);
					}
				}
			}
		}

	}

	public void createCombindSchedule() {
		for(Node<T> n :nodeMap.values()){
			if(n.getStation().isBusStation()&&n.getStation().isSubwayStation()){
				n.combindSchedule();
			}else{
				n.setSchedule();
			}
			
		}
		
	}

}