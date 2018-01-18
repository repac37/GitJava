package linkedlists;

import java.util.Iterator;

public class MyLinkedList <T> implements Iterable<T> {

	private int size = 0;
	private Node<T> head = null;
	private Node<T> tail = null;
	
	// Skapar node classen för representera datan; 
	@SuppressWarnings("hiding")
	private class Node<T>{
		T data;
		Node<T> prev,next;
		public Node(T data, Node<T> prev, Node<T> next){
			this.data = data;
			this.prev = prev;
			this.next = next;	
		}
		public String toString(){
			return data.toString();
		}
	}
	
	// tömmer listan
	public void clear(){
		Node<T> trav = head;
		while(trav != null){
			Node<T> next = trav.next;
			trav.prev = trav.next = null;
			trav.data = null;
			trav = next;
		}
		head = tail = trav = null;
		size = 0;
	}
	
	public int size(){
		return size;
	}
	
	public boolean isEmpty(){
		return size() == 0;
	}
	
	public void add(T elem){
		addLast(elem);
	}
	
	public void addFirst(T elem){
		if(isEmpty()){
			head = tail = new Node<T> (elem, null, null);
		}else{
			head.prev = new Node<T>(elem, null, head);
			head = head.prev;
		}
		 size++;
	}
	
	public void addLast(T elem){
		if(isEmpty()){
			head = tail = new Node<T> (elem, null, null);
		}else{
			tail.next = new Node<T>(elem, tail, null);
			tail = tail.next;
		}
		 size++;
	}
	
	
	
	public Iterator<T> iterator(){
		return null;
	}
}
