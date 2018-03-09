package alda.pathfinding;

import java.util.*;

public class PathFinder<T extends Comparable<T>> {
	private Graph<T> graph;
	Set<Node<T>> closedSet;
	Queue<Node<T>> openSet;

	public PathFinder(Graph<T> graph) {
		if (graph == null) {
			throw new NullPointerException("The input graph cannot be null");
		}
		this.graph = graph;
	}

	public class NodeCompator implements Comparator<Node<T>> {

		@Override
		public int compare(Node<T> n1, Node<T> n2) {
			if (n1.getDistance() > n2.getDistance()) {
				return 1;
			} else {
				return -1;
			}
		}

	}

	public void dijkstra(T start, T end) {
		Queue<Node<T>> queue = new PriorityQueue<>(new NodeCompator());
		Set<Node<T>> queueCheck = new HashSet<>();
		Node<T> startNode = graph.getNode(start);
		Node<T> endNode = graph.getNode(end);

		if (startNode == null || endNode == null) {
			throw new IllegalArgumentException("The node: " + start + " or " + end + " does not exist ");
		}
		startNode.setDistance(0);
		queue.add(startNode);
		queueCheck.add(startNode);// instead of decrease, better or worse???
		// queue.addAll(graph.getAllNodes().values());

		Set<Node<T>> visited = new HashSet<>();

		while (!queue.isEmpty()) {
			System.out.println(queue);
			Node<T> v = queue.poll();
			queueCheck.remove(v);
			v.setKnown(true);
			visited.add(v);
			if (v == endNode) {
				break;
			}
			if (v.getAdj() != null) {
				for (Node<T> w : v.getAdj()) {
					if (!visited.contains(w)) {
						int cvw = graph.getNodesEdgeDistance(v, w);
						if (v.getDistance() + cvw < w.getDistance()) {
							w.setDistance(v.getDistance() + cvw);
							w.setPath(v);
							if (!queueCheck.contains(w)) {
								queueCheck.add(w);
								queue.add(w);
							}
						}
					}
				}
			}
		}
		queueCheck.clear();
	}

	public void greedyBestFirst(T start, T goal) {
		Node<T> current = graph.getNode(start);
		closedSet.add(current);
		openSet = new PriorityQueue<>();

		for (Node<T> n : current.getAdj()) {
			if (closedSet.contains(n)) {
				continue;
			} else {
				n.setParent(current);
				if (!openSet.contains(n)) {
					n.compute();
				}
			}
		}
	}

	public void printPath(T node) {
		printPath(graph.getNode(node));

	}

	private void printPath(Node<T> node) {
		if (node.getPath() != null) {
			printPath(node.getPath());
			System.out.print(" to ");
		}
		System.out.print(node);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		Graph G = new Graph<>();
		PathFinder dijkstra = new PathFinder(G);
		dijkstra.test(dijkstra);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void test(PathFinder dijkstra) {

		dijkstra.graph.add("v1");
		dijkstra.graph.add("v2");
		dijkstra.graph.add("v3");
		dijkstra.graph.add("v4");
		dijkstra.graph.add("v5");
		dijkstra.graph.add("v6");
		dijkstra.graph.add("v7");

		dijkstra.graph.connect("v1", "v2", 2);
		dijkstra.graph.connect("v1", "v4", 1);
		dijkstra.graph.connect("v2", "v4", 3);
		dijkstra.graph.connect("v2", "v5", 10);
		dijkstra.graph.connect("v3", "v1", 4);
		dijkstra.graph.connect("v3", "v6", 5);
		dijkstra.graph.connect("v4", "v3", 2);
		dijkstra.graph.connect("v4", "v5", 2);
		dijkstra.graph.connect("v4", "v6", 8);
		dijkstra.graph.connect("v4", "v7", 4);
		dijkstra.graph.connect("v5", "v7", 6);
		dijkstra.graph.connect("v7", "v6", 1);

		// Set<Node> path = dijkstra.dijkstraShortPath("v1");
		Map<T, Node<T>> noder = graph.getAllNodes();

		dijkstra.dijkstra("v1", "v6");

		// System.out.println("size: "+path.size()+ "path: "+path);
		System.out.println(graph.toString());
		dijkstra.printPath("v6");

		// Iterator<Node> it = path.iterator();
		// while (it.hasNext()) {
		// System.out.println(it.next().getDistance());
		// }

	}

}
