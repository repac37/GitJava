package alda.pathfinding;

public class Edge<T extends Comparable<T>> {

	private Node<T> node1;
	private Node<T> node2;
	private int distance;
	private boolean bus = false;

	
	public Edge(Node<T> node1, Node<T> node2, int distance) {
		this.node1 = node1;
		this.node2 = node2;
		this.distance = distance;
	}
	
	public Edge(Node<T> node1, Node<T> node2, int distance, boolean bus) {
		this.node1 = node1;
		this.node2 = node2;
		this.distance = distance;
		this.setBus(bus);
	}

	public Node<T> getNode1() {
		return this.node1;
	}

	public Node<T> getNode2() {
		return this.node2;
	}

	public int getDistance() {
		return this.distance;
	}
	
	public void setDistance(int dist) {
		this.distance = dist;
	}

	public Node<T> getAdjacentNode(Node<T> node) {
		return !node.equals(node1) ? node1 : node2;
	}

	
	public boolean equals(Edge<T> other) {
		return other.node1.equals(node1)&&other.node2.equals(node2);
	}

	@Override
	public int hashCode() {
		return (node1.getValue().hashCode() * node2.getValue().hashCode()) % 911;
	}

	public boolean isBus() {
		return bus;
	}

	public void setBus(boolean bus) {
		this.bus = bus;
	}
}
