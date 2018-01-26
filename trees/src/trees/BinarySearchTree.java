package trees;

public class BinarySearchTree<T extends Comparable<? super T>> {
	
	
	private Node<T> root;
	
	
	
	private static class Node<T>{
		T data;
		Node<T> leftChild;
		Node<T> rightChild;	
		
		Node(T data){
			this(data,null,null);
		}
		Node(T data, Node<T> lc, Node<T> rc){
			this.data = data;
			leftChild = lc;
			rightChild = rc;
		}
	}
	
	public void getRoot(){
		System.out.println(root.data);
	}
	
	public BinarySearchTree(){
		makeEmpty();
	}
	void makeEmpty(){
		root = null;
	}
	
	public boolean isEmpty(){	
		return root == null;
	}
	public boolean contains(T elem){
		return contains(elem,root);
	}
	public T findMin(){
		if(isEmpty()){
			throw new NullPointerException("UnderflowException");
		}
		return findMin(root).data;
	}
	public T findMax(){
		if(isEmpty()){
			throw new NullPointerException("UnderflowException");
		}
		return findMax(root).data;
		
	}
	public void insert(T elem){
		root = insert(elem,root);
	}

	public void remove(T elem){
		root = remove(elem,root);
	}
	public void printTree(){
		if(isEmpty()){
			System.out.println("Empty träd");
		}else{
			printTree(root);
		}
	}
	

	private void printTree(Node<T> t) {
		if(t!=null){
			printTree(t.leftChild);
			System.out.println(t.data);
			printTree(t.rightChild);
		}
		
	}
	private Node<T> remove(T elem, Node<T> t) {
		if(t == null){
			return t;
		}
		
		int compareResult = elem.compareTo(t.data);
		
		if(compareResult<0){
			t.leftChild = remove(elem, t.leftChild); 
		}else if(compareResult>0){
			t.rightChild = remove(elem, t.rightChild);
		}else if( t.leftChild != null && t.rightChild != null){
			t.data = findMin(t.rightChild).data;
			t.rightChild = remove(t.data, t.rightChild);
		}else{
			t = (t.leftChild != null) ? t.leftChild : t.rightChild;
		}
		
		return t;
	}
	
	private boolean contains(T elem, Node<T> t){
		if(t == null){
			return false;
		}
		int compareResult = elem.compareTo(t.data);
		if(compareResult < 0){
			return contains(elem,t.leftChild);
		}else if(compareResult > 0){
			return contains(elem,t.rightChild);
		}else{
		return true;
		}
	}
	private Node<T> findMin(Node<T> t){
		if(t == null){
			return null;
		}else if(t.leftChild == null){
			return t;
		}
		return findMin(t.leftChild);
	}
	
	private Node<T> findMax(Node<T> t){
		if(t != null){
			while(t.rightChild != null){
				t = t.rightChild;
			}
		}
		return t;
	}
	
	private Node<T> insert(T elem, Node<T> t) {
		if(t == null){
			return new Node<T>(elem,null,null);
		}
		int compareResult = elem.compareTo(t.data);
		if(compareResult < 0){
			t.leftChild = insert(elem,t.leftChild);
			
		}else if(compareResult > 0){
			t.rightChild = insert(elem,t.rightChild);
		}
		else; // tabort =???? (Dubpliace notinrg)
		return t;
	}
}
