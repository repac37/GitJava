package trees;

import java.util.Comparator;

public class MyAVLTree<T> {

	private int currennSize;
	private Node<T> root;
	private Comparator<? super T> cmp; // vad händer???
	
	private static class Node<T>{	
		private T data;
		private Node<T> left, right, parent;
		
		public Node(T obj){
			this.data = obj;
			Node<T> parent, left, right  = null;
		}		
	}
	
	public MyAVLTree(){
		currennSize = 0;
		root = null;
		cmp = null;
	}
	
	public MyAVLTree( Comparator<? super T> c ){ // vad händer??
		currennSize = 0;
		root = null;
		cmp = c;
	}
	
	private int myCompare(T lhs, T rhs ){ // vad händer??
		if(cmp != null){
			return cmp.compare(lhs, rhs);
		}else{
			return (( Comparable ) lhs ).compareTo( rhs );
		}
	}

	public void add(T data){
		Node<T> node = new Node<T>(data);
		if(root == null){
			root = node;
			currennSize++;
			return;
		}
		add(root, node);
	}

	private void add(Node<T> parent, Node<T> newNode) {
		if( myCompare( parent.data, newNode.data ) > 0 ){
			if(parent.right == null){
				parent.right = newNode;
			}
		}
		
	}
}
