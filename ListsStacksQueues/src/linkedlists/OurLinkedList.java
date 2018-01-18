package linkedlists;

import java.util.Iterator;

public class OurLinkedList<E> implements Iterable<E> {
	
	private int theSize;
	private int modCount = 0;
	private Node<E> beginMarker;
	private Node<E> endMarker;
	
	
	private static class Node<E>{
		public E data;
		public Node<E> prev;
		public Node<E> next;
		
		public Node(E data, Node<E> prev, Node<E> next){
			this.data = data;
			this.prev = prev;
			this.next = next;
		}
	}
	
	public OurLinkedList(){
		doClear();
	}
	
	public void clear(){
		doClear();
	}
	private void doClear(){
		beginMarker = new Node<E>(null,null,null);
		endMarker = new Node<E>(null,beginMarker, null);
		beginMarker.next = endMarker;
		theSize = 0;
		modCount++;
	}	
	public int size(){ return theSize; }
	public boolean isEmpty(){ return size() == 0; }
	
	public boolean add(E element){
		add(size(), element);
		return true;
	}
	public void add(int index, E element){
		addBefore(getNode(index, 0, size()), element);
	}
	
	public E get(int index){
		return getNode(index).data;
	}
	public E set(int index, E newValue){
		Node<E> p = getNode(index);
		E oldValue = p.data;
		p.data = newValue;
		return oldValue;
	}

	public E remove(int index){
		return remove(getNode(index));
	}
	private E remove(Node<E> p){
		p.next.prev = p.prev;
		p.prev.next = p.next;
		theSize--;
		modCount++;
		return p.data;
	}
	
	private void addBefore(Node<E> p, E element) {
		Node<E> newNode = new Node<>(element ,p.prev, p);
		
		newNode.prev.next = newNode;
		p.prev = newNode;
		theSize++;
		modCount++;
	}
	private Node<E> getNode(int index){
		return getNode(index, 0, size()-1);
	}
	private Node<E> getNode(int index, int lower, int upper) {
		Node<E> p;
		if( index < lower || index > upper){
			throw new IndexOutOfBoundsException();
		}
		
		if(index < size()/2 ){
			p = beginMarker.next;
			for (int i = 0; i < index; i++) {
				p = p.next;
			}
		}else{
			p = endMarker;
			for (int i = size(); i > index ; i--) {
				p = p.prev;
			}
		}
		return p;
	}

	@Override
	public Iterator<E> iterator() {
		return new OurIterator();
	}
	
	private class OurIterator implements Iterator<E>{
		private Node<E> current = beginMarker.next;
		private int expectedModCount = modCount;
		private boolean okToRemove = false;
		
		@Override
		public boolean hasNext() {
			return current != endMarker;
		}
		
		@Override
		public E next() {
			if(modCount != expectedModCount){
				throw new java.util.ConcurrentModificationException();
			}
			if(!hasNext()){
				throw new java.util.NoSuchElementException();
			}
			
			E nextItem = current.data;
			current = current.next;
			okToRemove = true;
			return nextItem;
		}
		
		public void remove(){
			if(modCount != expectedModCount){
				throw new java.util.ConcurrentModificationException();
			}
			if(!okToRemove){
				throw new IllegalStateException();
			}
			
			OurLinkedList.this.remove(current.prev);
			expectedModCount++;
			okToRemove = false;
		}
		
	}

}
