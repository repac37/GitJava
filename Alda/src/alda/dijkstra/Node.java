package alda.dijkstra;

import java.util.*;

public class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
	
	private T data;
	private int distance;
	private boolean known;
	private Node<T> path;
	private List<Node<T>> adj;
	
	public Node(T node){
		this.data = node;
		this.setDistance(Integer.MAX_VALUE);
		this.known = false;
		this.path = null;
		
	}

	public T getValue() {
		return data;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public boolean isKnown() {
		return known;
	}

	public void setKnown(boolean known) {
		this.known = known;
	}

	public Node<T> getPath() {
		return path;
	}

	public void setPath(Node<T> path) {
		this.path = path;
	}

	public List<Node<T>> getAdj() {
		return adj;
	}

	public void addToAdj(Node<T> node){
		if(adj == null){
			adj = new ArrayList<>();
		}
		if(!adj.contains(node)){
			adj.add(node);
		}
	}
	
	@Override
	public int compareTo(Node<T> node) {
		return node.data.compareTo(this.data);
	}
	
	public boolean equals(Node<T> other) {
		return other.data.equals(this.data);
	}
	
	@Override
	public String toString() {
		return this.data.toString();
	}
	
	
}
