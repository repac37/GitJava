package alda.dijkstra;

import java.util.*;


public class Dijkstra<T extends Comparable<T>> {
	private Graph<T> graph;

	public Dijkstra(Graph<T> graph) {
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

	public Set<Node<T>> dijkstraShortPath(T source) {
		Queue<Node<T>> queue = new PriorityQueue<>(new NodeCompator());
		Node<T> s = graph.getNode(source);

		if (s == null) {
			throw new IllegalArgumentException("The node: " + source + " does not exist ");
		}
		s.setDistance(0);
		
		queue.addAll(graph.getAllNodes().values());

		Set<Node<T>> visited = new HashSet<>();
		
		while (!queue.isEmpty()) {
			Node<T> v = queue.poll();
			visited.add(v);
			v.setKnown(true);
			for(Node<T> w : v.getAdj()){
				if(!visited.contains(w)){
					int cvw = graph.getNodesEdgeDistance(v, w);
					if(v.getDistance()+cvw < w.getDistance()){
						w.setDistance(v.getDistance()+cvw);
						w.setPath(v);
					}
				}
			}
			
		}
		return visited;
	}
	
	public void printPath(T node){
		printPath(graph.getNode(node));
		
	}

	private void printPath(Node<T> node) {
		if(node.getPath() != null){
			printPath(node.getPath());
			System.out.println(" to ");
		}
		System.out.println(node);
	}
	
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
	    Graph G = new Graph<>();
		Dijkstra dijkstra = new Dijkstra(G);
	    dijkstra.test(dijkstra);
	    

	}
	
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 private void test(Dijkstra dijkstra) {
		
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


	  
	    Set<Node> path = dijkstra.dijkstraShortPath("v1");
	    System.out.println(graph.toString());
	    dijkstra.printPath("v6");
	    
//	    Iterator<Node> it = path.iterator();
//	    while (it.hasNext()) {
//	        System.out.println(it.next().getDistance());
//	    }
		
	}



}
