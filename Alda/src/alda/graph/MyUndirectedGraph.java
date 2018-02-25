 /**
  * 	Mikael Tofvesson: mito2023@student.su.se och Emil Oja: emoj8928@student.su.se
  */

package alda.graph;

import java.util.*;

public class MyUndirectedGraph<T> implements UndirectedGraph<T> {

	private Map<Integer, Edge<T>> edges = new HashMap<>();
	private Map<T, Node<T>> noder = new HashMap<>();
	private final static int INFINITY = Integer.MAX_VALUE;
	
	public static class Node<T>{ 
		private T data;
		private T path;
		private int dist = INFINITY;
		private Set<T> adjacent;

		private Node(T data) {
			this.data = data;
			adjacent = new HashSet<>();
		}

	}

	private static class Edge<T> implements Comparable<Edge<T>> {
		private Node<T> node1;
		private Node<T> node2;
		private int cost = 0;

		private Edge(Node<T> node1, Node<T> node2, int cost) {
			this.node1 = node1;
			this.node2 = node2;
			this.cost = cost;
		}

		@Override
		public int hashCode() {
			return (node1.data.hashCode() * node2.data.hashCode()) % 911;
		}

		@Override
		public int compareTo(Edge<T> other) {
			if (other.cost < cost) {
				return 1;
			} else if (other.cost > cost) {
				return -1;
			} else {
				return 0;
			}
		}

	}

	@Override
	public int getNumberOfNodes() {
		return noder.size();
	}

	@Override
	public int getNumberOfEdges() {
		return edges.size();
	}

	@Override
	public boolean add(T newNode) {
		if (noder.containsKey(newNode)) {
			return false;
		} else {
			noder.put(newNode, new Node<T>(newNode));
			return true;
		}
	}

	@Override
	public boolean connect(T node1, T node2, int cost) {
		if (cost <= 0) {
			return false;
		}
		if (nodeMissing(node1, node2)) {
			return false;
		}

		Edge<T> tmpEdge;
		if ((tmpEdge = getEdge(node1, node2)) != null) {
			tmpEdge.cost = cost;
			return true;
		}

		tmpEdge = new Edge<T>(noder.get(node1), noder.get(node2), cost);
		edges.put(tmpEdge.hashCode(), tmpEdge);
		noder.get(node1).adjacent.add(node2);
		noder.get(node2).adjacent.add(node1);

		return true;
	}

	private Edge<T> getEdge(T node1, T node2) {
		return edges.get(getEdgeHash(node1, node2));
	}

	private int getEdgeHash(T node1, T node2) {
		int code = (node1.hashCode() * node2.hashCode()) % 911;
		return code;
	}

	@Override
	public boolean isConnected(T node1, T node2) {
		return edges.get(getEdgeHash(node1, node2)) != null;
	}

	@Override
	public int getCost(T node1, T node2) {
		int code = getEdgeHash(node1, node2);
		Edge<T> tmpEdge;
		if ((tmpEdge = edges.get(code)) != null) {
			return tmpEdge.cost;
		} else {
			return -1;
		}
	}

	@Override
	public List<T> depthFirstSearch(T start, T end) {
		if (nodeMissing(start, end)) {
			throw new IllegalArgumentException();
		}

		Stack<T> stack = new Stack<>();
		LinkedList<T> result = new LinkedList<>();

		stack.push(start);
		if (start.equals(end)) {
			return stack;
		}

		stack = DFS(stack, end);

		while (!stack.isEmpty()) {
			result.addFirst(stack.pop());
		}

		return result;
	}

	private Stack<T> DFS(Stack<T> stack, T end) {
		HashSet<T> visited = new HashSet<>();
		visited.add(stack.peek());
		T current = stack.peek();
		while (!stack.isEmpty()) {
			if (isAllAdjacentVisited(current, visited) && !current.equals(end)) {
				stack.pop();
				current = stack.peek();
			} else {
				for (T adjacent : noder.get(current).adjacent) {
					if (!visited.contains(adjacent) && isConnected(adjacent, current)) {
						visited.add(adjacent);
						stack.push(adjacent);
					}
					current = stack.peek();
				}
			}
			if (current.equals(end)) {
				break;
			}
		}
		return stack;
	}

	private boolean isAllAdjacentVisited(T current, HashSet<T> visited) {
		for (T adjacent : noder.get(current).adjacent) {
			if (!visited.contains(adjacent)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public List<T> breadthFirstSearch(T start, T end) {

		if (nodeMissing(start, end)) {
			throw new IllegalArgumentException();
		}
		
		PriorityQueue<T> priorityQueue = new PriorityQueue<>();
		LinkedList<T> returnList = new LinkedList<>();

		for(Node<T> node : noder.values()){
			node.dist = INFINITY;
			node.path = null;		
		}

		noder.get(start).dist = 0;
		priorityQueue.add(start);
		
		returnList.add(end);
		if(start.equals(end)){
			return returnList;
		}

		while (!priorityQueue.isEmpty()) {
			T current = priorityQueue.remove();
			Node<T> currentNode = noder.get(current);
			for (T node : currentNode.adjacent) {
				Node<T> tmp = noder.get(node);
				if(tmp.dist == INFINITY){
					tmp.dist = currentNode.dist+1;
					tmp.path = current;
					priorityQueue.add(node);
				}
			}
		}

		Node<T> nodeTmp = noder.get(end);
		while(!nodeTmp.path.equals(start)) {
			returnList.addFirst(nodeTmp.path);
			nodeTmp = noder.get(nodeTmp.path);
		}
		returnList.addFirst(start);
		
		return returnList;
	}

	private boolean nodeMissing(T node1, T node2) {
		if (!noder.containsKey(node1) || !noder.containsKey(node2)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public UndirectedGraph<T> minimumSpanningTree() {

		PriorityQueue<Edge<T>> priorityQueue = new PriorityQueue<>(edges.values());
		Map<T, Set<T>> disjoint  = getDisjoint();
		List<Edge<T>> mst = new ArrayList<>();
		
		
		while (mst.size() != noder.size()-1) {
			Edge<T> currentEdge = priorityQueue.remove(); 
			
			Set<T> uset = disjoint.get(currentEdge.node1.data);
			Set<T> vset = disjoint.get(currentEdge.node2.data);
			
			if(uset != vset){
				mst.add(currentEdge);	
				vset.addAll(uset);
				for (T node : vset) {
					disjoint.put(node, vset);
				}
			}
		}
		
		
		return createGraphFromMst(mst);
	
	}

	private MyUndirectedGraph<T> createGraphFromMst(List<Edge<T>> mst) {
		MyUndirectedGraph<T> minSpanTree = new MyUndirectedGraph<T>(); 
		for (Edge<T> edge : mst) {
			T node1 = edge.node1.data;
			T node2 = edge.node2.data;
			
			minSpanTree.add(node1);
			minSpanTree.add(node2);
			minSpanTree.connect(node1, node2, edge.cost);
		}
		return minSpanTree;
	}

	private HashMap<T, Set<T>> getDisjoint() {
		HashMap<T, Set<T>> disjoint = new HashMap<>();
	
		for (T node : noder.keySet()) {
			Set<T> adjecent = new HashSet<>();
			adjecent.add(node);
			disjoint.put(node, adjecent);
		}
		return disjoint;
	}

}
