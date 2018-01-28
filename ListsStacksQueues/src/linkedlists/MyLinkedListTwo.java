package linkedlists;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;




public class MyLinkedListTwo<T> implements Iterable<T> {
	private int size;
	private int modCount;
	private Node<T> head;
	
	private static class Node<T>{
		T data;
		Node<T> next;
		
		public Node(T value, Node<T> next){
			this.data = value;
			this.next = next; 
		}
	}
	
	public MyLinkedListTwo(){
		clear();
	}
	
	public int size(){
		return size;
	}
	
	private int modCount(){
		return modCount;
	}
	
	public void add(T elem){
		
		if(size() == 0){
			head.next = new Node<T>(elem, null);
		}else{
			Node<T> tmp = head.next;
			while(tmp.next != null){
				tmp = tmp.next;
			}
			tmp.next = new Node<T>(elem, null);
		}
		size++;
		modCount++;
	}
	
	public void add(int idx, T elem){
		if(idx<0 || idx>size()){
			throw new IndexOutOfBoundsException("index: "+idx);
		}
		Node<T> beforeIndex = getOneBeforeIndex(idx);
		Node<T> addNode = new Node<T>(elem,beforeIndex.next.next);
		beforeIndex.next = addNode;
		modCount++;
	}
	
	public void addToFront(T elem){
		if(elem != null){
			if(size() == 0){
				add(elem);
			}else{
				Node<T> newNode = new Node<T>(elem, head.next);
				head.next = newNode;
				size++;
				modCount++;
			}
		}
	}
	
	public T remove(int idx){
		if(idx<0 || idx>size()){
			throw new IndexOutOfBoundsException("index: "+idx);
		}
		Node<T> beforeIndex = getOneBeforeIndex(idx);
		System.out.println("beforeIndex: " + beforeIndex.data);
		Node<T> delNode = beforeIndex.next;
		beforeIndex.next = beforeIndex.next.next;
		size--;
		modCount++;
		return delNode.data;
	}

	public boolean remove(T elem){
		if(elem == null){
			throw new IllegalArgumentException("IllegalArgumentException: Object is null");
		}
		Node<T> tmp = head.next;
		Node<T> preTmp = head;
		while(tmp.next != null && !tmp.data.equals(elem)) {
			tmp = tmp.next;
			preTmp = preTmp.next;
		}
		
		if(tmp.data.equals(elem)){
			preTmp.next = tmp.next;
			size--;
			modCount++;
			return true;
		}
		
		return false;
	}

	public T get(int idx){
		if(idx<0 || idx>size()){
			throw new IndexOutOfBoundsException("index: "+idx);
		}
		return getOneBeforeIndex(idx).next.data;
	}
	
	public boolean contains(T elem){
		if(elem == null){
			throw new IllegalArgumentException("IllegalArgumentException: Object is null");
		}
		Node<T> tmp = head.next;
		for(int i = 0; i<size(); i++){
			if(tmp.data.equals(elem)){
				return true;
			}
			tmp = tmp.next;
		}
		return false;
	}
	
	public int indexOf(T elem){
		if(elem == null){
			throw new IllegalArgumentException("IllegalArgumentException: Object is null");
		}
		Node<T> tmp = head.next;
		for(int i = 0; i<size(); i++){
			if(tmp.data.equals(elem)){
				return i;
			}
			tmp = tmp.next;
		}
		return -1;
	}
	
	public void clear(){
		head = new Node<T>(null, null);
		size = 0;
		modCount++;
	}
	
	private Node<T> getOneBeforeIndex(int idx) {
		Node<T> tmp = head;
		for(int i = 0; i < idx; i++){
			tmp = tmp.next;	
		}
		return tmp;
	}

	@Override
	public Iterator<T> iterator() {
		return new MyIterator();
	}
	
	private class MyIterator implements Iterator<T>{
		private Node<T> current = head.next;
		// För att kunna spara undan för radera
		private Node<T> remove = null;		
		private int expectedModCount = modCount(); 
		private boolean okToRemove = false;
		
		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public T next() {
			if(expectedModCount != modCount){
				throw new ConcurrentModificationException();
			}
			if(!hasNext()){
				throw new NoSuchElementException();
			}
			// kopiera data
			T linkNode = current.data;
			// spara undan för att raderas
			remove = current;
			// Kopiera vidare för iteration
			current = current.next;
			
			okToRemove = true;
			return linkNode;
		}
		
		public void remove(){
			if(!okToRemove){
				throw new IllegalStateException(); 
			}
			MyLinkedListTwo.this.remove(remove.data);
			remove = null;
			expectedModCount = modCount(); 
			okToRemove = false;
		}
		
	}

}
