package alda.pathfinding;

import java.util.*;

public class Node<T extends Comparable<T>> implements Comparable<Node<T>>,  Comparator<Node<T>>  {
	
	private T data;
	private int distance;
	private boolean known;
	private Node<T> path;
	private List<Node<T>> adj;
	private Node<T> parent;
	private int x,y;
	private int nextDepart;
	private int f = Integer.MAX_VALUE;
	private int g = Integer.MAX_VALUE;
	private double h = Integer.MAX_VALUE;
	private Station station;
	
	public Node(T node){
		this.data = node;
		this.setDistance(Integer.MAX_VALUE);
		this.known = false;
		this.path = null;
	}
	
	public Node(T node, int x, int y){
		this(node);
		this.setX(x);
		this.setY(y);
		setStation(new Station());
	}
	
	public Node(T node, int x, int y, boolean sub, boolean bus){
		this(node,x,y);
		setStation(new Station(sub,bus));
	}
	
	public T getValue() {
		return data;
	}

	public int getDistance() {
		
		return (this.known!=false&&distance==Integer.MAX_VALUE)?0:distance;
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
	
	@Override
	public int compare(Node<T> n1, Node<T> n2) {
		if (n1.getDistance() > n2.getDistance()) {
			return 1;
		} else {
			return -1;
		}
	}
	
	public boolean equals(Node<T> other) {
		return this.getValue().equals(other.getValue());
	}
	
	@Override
	public String toString() {
		return this.data.toString();
	}

	public Node<T> getParent() {
		return parent;
	}

	public void setParent(Node<T> parent) {
		this.parent = parent;
	}
	

	public double getH() {
		return this.h;
	}
	
	public void setH(Node<T> goal) {
		
		this.h= Math.sqrt(Math.pow(goal.getX()-this.getX(),2)+Math.pow(goal.getY()-this.getY(),2));
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getF() {
		return f;
	}

	public void setF() {
		this.f =  this.g + (int) Math.round(this.h) + nextDepart;
	}
	
	public void setNextDepart(int hour, int min){
		nextDepart = this.station.getSchedule().nextDepature(hour, min);
	}
	
	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public void combindSchedule() {
		this.station.combind();
		
	}

	public void setSchedule() {
		this.station.setSchedule();
	}
	
	
	
	
}
