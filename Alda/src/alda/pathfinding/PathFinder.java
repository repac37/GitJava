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

	public class GBfCompator implements Comparator<Node<T>> {

		@Override
		public int compare(Node<T> n1, Node<T> n2) {
			if (n1.getH() > n2.getH()) {
				return 1;
			} else {
				return -1;
			}
		}

	}

	public void greedyBestFirst(T start, T goal) {
		Node<T> current = graph.getNode(start);
		Node<T> end = graph.getNode(goal);
		closedSet = new HashSet<>();
		openSet = new PriorityQueue<>(new GBfCompator());
		closedSet.add(current);
		do{
			for (Node<T> n : current.getAdj()) {
				if(closedSet.contains(n)){
					continue;
				}else{
					n.setPath(current);
					if(!openSet.contains(n)){
						n.setH(end);
						//System.out.println(n +" : "+ n.getH());
						openSet.add(n);
					}
				}
			}
			if(openSet.isEmpty()){
				break;
			}
			
			current = openSet.poll();
			closedSet.add(current);
		}while(!end.equals(current)); 
		
	}
	
	public class aStarCompator implements Comparator<Node<T>> {

		@Override
		public int compare(Node<T> n1, Node<T> n2) {
			if (n1.getF() > n2.getF()) {
				return 1;
			} else {
				return -1;
			}
		}

	}
	
	public void aStar(T start, T goal) {
		Node<T> current = graph.getNode(start);
		Node<T> end = graph.getNode(goal);
		closedSet = new HashSet<>();
		openSet = new PriorityQueue<>(new aStarCompator());
		current.setH(end);
		closedSet.add(current);
//		System.out.println(current +": "+"H="+ current.getH()+", G="+ current.getG()+", F="+current.getF());
//		System.out.println(end +": "+"H="+ end.getH()+", G="+ end.getG()+", F="+end.getF());
//		System.out.println();
		do{
			for (Node<T> n : current.getAdj()) {
				if(closedSet.contains(n)){
					continue;
				}else if(openSet.contains(n)){
					int costSofar = graph.getNodesEdgeDistance(n, current)+current.getG();
					if(costSofar<n.getG()){
						n.setPath(current);
						n.setG(costSofar);
						n.setF();
						//System.out.println("update: "+n +": "+"H="+ current.getH()+", G="+ n.getG()+", F="+n.getF());
						//System.out.println("update: "+n +": "+"H="+ current.getH()+", G="+ n.getG()+", F="+n.getF());
						openSet.add(openSet.poll());
					}
				}else{
					n.setPath(current);
					n.setH(end);
					n.setG(graph.getNodesEdgeDistance(n, current));
					n.setF();
					System.out.println(n +": "+"H="+ n.getH()+", G="+ n.getG()+", F="+n.getF());
					System.out.println(n +": "+ n.getStation());
					openSet.add(n);
				}
			}
			if(openSet.isEmpty()){
				break;
			}
			
			current = openSet.poll();
			closedSet.add(current);
		}while(!end.equals(current)); 
		
	}

	public void aStarSL(T start, T goal, int hour, int min) {
		Node<T> current = graph.getNode(start);
		Node<T> end = graph.getNode(goal);
		closedSet = new HashSet<>();
		openSet = new PriorityQueue<>(new aStarCompator());
		current.setH(end);
		
		closedSet.add(current);
//		System.out.println(current +": "+"H="+ current.getH()+", G="+ current.getG()+", F="+current.getF());
//		System.out.println(end +": "+"H="+ end.getH()+", G="+ end.getG()+", F="+end.getF());
//		System.out.println();
		do{
			for (Node<T> n : current.getAdj()) {
				if(closedSet.contains(n)){
					continue;
				}else if(openSet.contains(n)){
					int costSofar = graph.getNodesEdgeDistance(n, current)+current.getG();
					if(costSofar<n.getG()){
						n.setPath(current);
						n.setG(costSofar);
						n.setF();
						//System.out.println("update: "+n +": "+"H="+ current.getH()+", G="+ n.getG()+", F="+n.getF());
						//System.out.println("update: "+n +": "+"H="+ current.getH()+", G="+ n.getG()+", F="+n.getF());
						openSet.add(openSet.poll());
					}
				}else{
					n.setPath(current);
					n.setNextDepart(hour, min);
					n.setH(end);
					n.setG(graph.getNodesEdgeDistance(n, current));
					n.setF();
					System.out.println(n +": "+"H="+ n.getH()+", G="+ n.getG()+", F="+n.getF());
					System.out.println(n +": "+ n.getStation());
					openSet.add(n);
				}
			}
			if(openSet.isEmpty()){
				break;
			}
			
			current = openSet.poll();
			closedSet.add(current);
		}while(!end.equals(current)); 
		
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
		PathFinder path = new PathFinder(G);
		path.testTimeTable(path);
		
		
		
		// path.dijkstraTest(path);
		//path.greedyBestFirstTest(path);
		//path.test(path);

	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void testTimeTable(PathFinder path) {

		path.graph.add("T-Centralen", 8,9);

		path.graph.add("Vitabergen", 10,12);
		path.graph.add("Hammarby", 12,14,true,true);
		path.graph.add("Sickla", 14,14);
		path.graph.add("Älta", 16,14);
		path.graph.add("Täby", 16,17,true,true);

		path.graph.add("Medborgarplatsen", 11,9);
		path.graph.add("Gullmarsplan", 14,9,true,true);
		path.graph.add("Gloden", 15,7);
		path.graph.add("Rågsved", 18,7);

		path.graph.add("Odenplan", 9,4,true,true);
		path.graph.add("Fridemsplan", 6,4,true,true);
		path.graph.add("Blackberg", 6,1);
		path.graph.add("Hässleby", 3,1);

		path.graph.add("Rådhuset", 6,9);
		path.graph.add("Stadium", 6,11);
		path.graph.add("Tekniska Hög", 6,13,true,true);
		path.graph.add("Universitetet", 4,13);
		path.graph.add("Mörby", 4,16,true,true);

		path.graph.add("Klaraplan", 5,10,false,true);
		path.graph.add("Stadshagen", 5,7,false,true);

		path.graph.connect("T-Centralen", "Rådhuset", 5);
		path.graph.connect("T-Centralen", "Vitabergen", 10);
		path.graph.connect("T-Centralen", "Medborgarplatsen", 10);
		path.graph.connect("T-Centralen", "Odenplan", 12);

		path.graph.connect("Vitabergen", "Hammarby", 10);
		path.graph.connect("Hammarby", "Sickla", 5);
		path.graph.connect("Sickla", "Älta", 5);
		path.graph.connect("Täby", "Älta", 10);

		path.graph.connect("Medborgarplatsen", "Gullmarsplan", 10);
		path.graph.connect("Gullmarsplan", "Gloden", 5);
		path.graph.connect("Gloden", "Rågsved", 10);

		path.graph.connect("Odenplan", "Fridemsplan", 9);
		path.graph.connect("Fridemsplan", "Blackberg", 10);
		path.graph.connect("Blackberg", "Hässleby", 10);

		path.graph.connect("Rådhuset", "Stadium", 5);
		path.graph.connect("Stadium", "Tekniska Hög", 5);
		path.graph.connect("Tekniska Hög", "Universitetet", 5);
		path.graph.connect("Universitetet", "Mörby", 10);

		path.graph.connect("Tekniska Hög", "Klaraplan", 10,true);
		path.graph.connect("Klaraplan", "Stadshagen", 10,true);
		path.graph.connect("Stadshagen", "Fridemsplan", 10,true);

		path.graph.connect("Odenplan", "Gullmarsplan", 20, true);
		path.graph.connect("Gullmarsplan", "Hammarby", 20, true);
		path.graph.connect("Hammarby", "Tekniska Hög", 20, true);
		path.graph.connect("Mörby", "Täby", 25, true);

		path.graph.createSubTimeTable("Hässleby");
		path.graph.createBusTimeTable("Mörby");
		path.graph.createBusTimeTable("Fridemsplan");
		path.graph.createCombindSchedule();
				
//		System.out.println(path.graph.getNode("Hässleby").getStation().getSub());
//		System.out.println(path.graph.getNode("Blackberg").getStation().getSub());
//		System.out.println(path.graph.getNode("Fridemsplan").getStation().getSub());
//		System.out.println(path.graph.getNode("Odenplan").getStation().getSub());
//		System.out.println(path.graph.getNode("T-Centralen").getStation().getSub());
//		System.out.println(path.graph.getNode("Täby").getStation().getSub());
//		System.out.println(path.graph.getNode("Fridemsplan").getStation().getBus());
//		System.out.println(path.graph.getNode("Stadshagen").getStation().getBus());
//		System.out.println(path.graph.getNode("Klaraplan").getStation().getBus());
//		System.out.println(path.graph.getNode("Tekniska Hög").getStation().getBus());
//		System.out.println(path.graph.getNode("Hammarby").getStation().getBus());
		
		String start = "Universitetet";
		String goal = "Täby";
		path.aStarSL(start, goal, 12, 36);
		path.printPath(goal);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void test(PathFinder path) {

		path.graph.add("T-Centralen", 8,9);

		path.graph.add("Vitabergen", 10,12);
		path.graph.add("Hammarby", 12,14);
		path.graph.add("Sickla", 14,14);
		path.graph.add("Älta", 16,14);
		path.graph.add("Täby", 16,17);

		path.graph.add("Medborgarplatsen", 11,9);
		path.graph.add("Gullmarsplan", 14,9);
		path.graph.add("Gloden", 15,7);
		path.graph.add("Rågsved", 18,7);

		path.graph.add("Odenplan", 9,4);
		path.graph.add("Fridemsplan", 6,4);
		path.graph.add("Blackberg", 6,1);
		path.graph.add("Hässleby", 3,1);

		path.graph.add("Rådhuset", 6,9);
		path.graph.add("Stadium", 6,11);
		path.graph.add("Tekniska Hög", 6,13);
		path.graph.add("Universitetet", 4,13);
		path.graph.add("Mörby", 4,16);

		path.graph.add("Klaraplan", 5,10);
		path.graph.add("Stadshagen", 5,7);

		path.graph.connect("T-Centralen", "Rådhuset", 5);
		path.graph.connect("T-Centralen", "Vitabergen", 10);
		path.graph.connect("T-Centralen", "Medborgarplatsen", 10);
		path.graph.connect("T-Centralen", "Odenplan", 12);

		path.graph.connect("Vitabergen", "Hammarby", 10);
		path.graph.connect("Hammarby", "Sickla", 5);
		path.graph.connect("Sickla", "Älta", 5);
		path.graph.connect("Täby", "Älta", 10);

		path.graph.connect("Medborgarplatsen", "Gullmarsplan", 10);
		path.graph.connect("Gullmarsplan", "Gloden", 5);
		path.graph.connect("Gloden", "Rågsved", 10);

		path.graph.connect("Odenplan", "Fridemsplan", 9);
		path.graph.connect("Fridemsplan", "Blackberg", 10);
		path.graph.connect("Blackberg", "Hässleby", 10);

		path.graph.connect("Rådhuset", "Stadium", 5);
		path.graph.connect("Stadium", "Tekniska Hög", 5);
		path.graph.connect("Tekniska Hög", "Universitetet", 5);
		path.graph.connect("Universitetet", "Mörby", 5);

		path.graph.connect("Tekniska Hög", "Klaraplan", 10);
		path.graph.connect("Klaraplan", "Stadshagen", 10);
		path.graph.connect("Stadshagen", "Fridemsplan", 10);

		path.graph.connect("Odenplan", "Gullmarsplan", 20);
		path.graph.connect("Gullmarsplan", "Hammarby", 20);
		path.graph.connect("Hammarby", "Tekniska Hög", 20);
		path.graph.connect("Mörby", "Täby", 25);

		String start = "Universitetet";
		String goal = "Täby";
		path.aStar(start, goal);
		//path.dijkstra(start, goal);
		//path.greedyBestFirst(start, goal);
		//System.out.println(gbf.graph.getNode("Hässleby").equals(gbf.graph.getNode("Täby")));
		
		//double distance = heuristic(gbf.graph.getNode("Hässleby"),gbf.graph.getNode("Täby"));
		//System.out.println(distance);
		// System.out.println("size: "+path.size()+ "path: "+path);
		//System.out.println(graph.toString());
		
		path.printPath(goal);

		

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void dijkstraTest(PathFinder dijkstra) {

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
		// Map<T, Node<T>> noder = graph.getAllNodes();

		dijkstra.dijkstra("v1", "v6");

		// System.out.println("size: "+path.size()+ "path: "+path);
		System.out.println(graph.toString());
		dijkstra.printPath("v6");

		// Iterator<Node> it = path.iterator();
		// while (it.hasNext()) {
		// System.out.println(it.next().getDistance());
		// }

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void greedyBestFirstTest(PathFinder gbf) {

		gbf.graph.add("T-Centralen", 8,9);

		gbf.graph.add("Vitabergen", 10,12);
		gbf.graph.add("Hammarby", 12,14);
		gbf.graph.add("Sickla", 14,14);
		gbf.graph.add("Älta", 16,14);
		gbf.graph.add("Täby", 16,17);

		gbf.graph.add("Medborgarplatsen", 11,9);
		gbf.graph.add("Gullmarsplan", 14,9);
		gbf.graph.add("Gloden", 15,7);
		gbf.graph.add("Rågsved", 18,7);

		gbf.graph.add("Odenplan", 9,4);
		gbf.graph.add("Fridemsplan", 6,4);
		gbf.graph.add("Blackberg", 6,1);
		gbf.graph.add("Hässleby", 3,1);

		gbf.graph.add("Rådhuset", 6,9);
		gbf.graph.add("Stadium", 6,11);
		gbf.graph.add("Tekniska Hög", 6,13);
		gbf.graph.add("Universitetet", 4,13);
		gbf.graph.add("Mörby", 4,16);

		gbf.graph.add("Klaraplan", 5,10);
		gbf.graph.add("Stadshagen", 5,7);

		gbf.graph.connect("T-Centralen", "Rådhuset", 5);
		gbf.graph.connect("T-Centralen", "Vitabergen", 10);
		gbf.graph.connect("T-Centralen", "Medborgarplatsen", 10);
		gbf.graph.connect("T-Centralen", "Odenplan", 12);

		gbf.graph.connect("Vitabergen", "Hammarby", 10);
		gbf.graph.connect("Hammarby", "Sickla", 5);
		gbf.graph.connect("Sickla", "Älta", 5);
		gbf.graph.connect("Täby", "Älta", 10);

		gbf.graph.connect("Medborgarplatsen", "Gullmarsplan", 10);
		gbf.graph.connect("Gullmarsplan", "Gloden", 5);
		gbf.graph.connect("Gloden", "Rågsved", 10);

		gbf.graph.connect("Odenplan", "Fridemsplan", 10);
		gbf.graph.connect("Fridemsplan", "Blackberg", 10);
		gbf.graph.connect("Blackberg", "Hässleby", 10);

		gbf.graph.connect("Rådhuset", "Stadium", 5);
		gbf.graph.connect("Stadium", "Tekniska Hög", 5);
		gbf.graph.connect("Tekniska Hög", "Universitetet", 5);
		gbf.graph.connect("Universitetet", "Mörby", 5);

		gbf.graph.connect("Tekniska Hög", "Klaraplan", 10);
		gbf.graph.connect("Klaraplan", "Stadshagen", 10);
		gbf.graph.connect("Stadshagen", "Fridemsplan", 10);

		gbf.graph.connect("Odenplan", "Gullmarsplan", 20);
		gbf.graph.connect("Gullmarsplan", "Hammarby", 20);
		gbf.graph.connect("Hammarby", "Tekniska Hög", 20);
		gbf.graph.connect("Mörby", "Täby", 40);

		// Set<Node> path = dijkstra.dijkstraShortPath("v1");
		Map<T, Node<T>> noder = graph.getAllNodes();

		gbf.greedyBestFirst("Hässleby", "Hammarby");
		
		//System.out.println(gbf.graph.getNode("Hässleby").equals(gbf.graph.getNode("Täby")));
		
		//double distance = heuristic(gbf.graph.getNode("Hässleby"),gbf.graph.getNode("Täby"));
		//System.out.println(distance);
		// System.out.println("size: "+path.size()+ "path: "+path);
		//System.out.println(graph.toString());
		
		gbf.printPath("Hammarby");

		// Iterator<Node> it = path.iterator();
		// while (it.hasNext()) {
		// System.out.println(it.next().getDistance());
		// }

	}


}
