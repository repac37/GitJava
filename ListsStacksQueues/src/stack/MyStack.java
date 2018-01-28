package stack;

import java.util.EmptyStackException;
import java.util.Iterator;

public class MyStack<E> implements Iterable<E> {

	private int size;
	private Node<E> start;
	private Node<E> end;

	private static class Node<E>{
		E data;
		Node<E> before;
		
		private Node(E value, Node<E> before){
			this.data = value;
			this.before = before;
		}
	}
	
	public MyStack(){
		clear();
	}
	
	
	public void clear() {
		start = new Node<E>(null, null);
		end = new Node<E>(null, start);
		size = 0;

	}

	public boolean isEmpty() {
		return size()==0;
	}

	public boolean add(E elem) {
		if(elem == null){
			return false;
		}
		if(size() == 0){
			Node<E> newNode = new Node<E>(elem,start); 
			end.before = newNode;
		}else{
			Node<E> newNode = new Node<E>(elem,end.before);
			end.before = newNode;
		}
		size++;

		return true;
	}

	public boolean contains(E elem) {
		Node<E> tmp = end.before;
		while(tmp.before != null ){
			if(tmp.data.equals(elem)){
				return true;
			}
			tmp = tmp.before;
		}
		return false;
	}

	public E peekFirst() {
		return end.before.data;
	}

	public E pop() {
		if(isEmpty()){
			throw new EmptyStackException();
		}
		Node<E> pop = end.before; 
		end.before = end.before.before;
		size--;
	
		return pop.data;
	}

	public void push(E elem) {
		add(elem);

	}

	public int size() {
		return size;
	}


	@Override
	public Iterator<E> iterator() {
		return new MyIterator();
	}
	
	private class MyIterator implements Iterator<E>{
		
		private Node<E> current = end.before;
		
		
		@Override
		public boolean hasNext() {
			
			return current != start;
		}

		@Override
		public E next() {
			if(!hasNext()){
				throw new java.util.NoSuchElementException(); 
			}
			E prevData = current.data;
			current = current.before;
			
			return prevData;
		}
		
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder("[ ");
		for(E i :  MyStack.this ){
			sb.append(i+", ");
		}
		sb.replace(sb.length()-2, sb.length(), " ]");		
		return sb.toString();
	}

}
