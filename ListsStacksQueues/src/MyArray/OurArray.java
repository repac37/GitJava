package MyArray;

import java.util.Iterator;

@SuppressWarnings("unchecked")
public class OurArray<E> implements Iterable<E> {

	
	private int theSize;
	private int capacity = 0;
	private E arr[];
	
	
	public OurArray(){
		capacity = 10;
		arr = (E[]) new Object[capacity];
		theSize = 0;
		
	}
	
	public OurArray(int capacity){
		if(capacity < 1){
			throw new IllegalArgumentException("FÖR LITEN STORLEK: " + capacity);
		}
		this.capacity = capacity;
		theSize = 0;
		arr = (E[]) new Object[capacity];
	}
	
	private void increaseCapacity() {
		capacity *= 2;
		E nyarr[] = (E[]) new Object[capacity];
		for( int i = 0 ; i < theSize ; i++){
			nyarr[i] = arr[i];
			
		}
		arr = nyarr;
	}
	
	public int size(){
		return theSize;
	}
	
	public boolean add(E element){
		if(size() + 1 > capacity){
			increaseCapacity();
		}
		arr[size()] = element;
		theSize++;
		return true;
	}
	
	public E addAtIndex(E element, int index){
		if(index < 0 && index > size()-1 ){
			throw new IndexOutOfBoundsException();
		}
		E item = arr[index];
		arr[index] = element;
		
		return item;		
	}
	
	public boolean removeLast(){
		if(size()-1>0){
			arr[size()-1] = null;
			theSize--;
			return true;
		}else{
			return false;
		}
		
	}
	
	public E removeAtIndex(int index){
		if(index < 0 && index >=theSize){
			throw new IndexOutOfBoundsException();
		}
		E item = arr[index];
		E nyarr[] = (E[])new Object[capacity];
		for(int i = 0; i < size()-1; i++){
			if(i < index){
				nyarr[i] = arr[i];
				
			}else{
				nyarr[i] = arr[i+1];
			}
		}
		arr = nyarr;
		theSize--;
		return item;
	}
	

	@Override
	public Iterator<E> iterator() {
		return new OurIterator();
	}
	
	private class OurIterator implements Iterator<E>{
		// int i = 0; 
		int index = 0;
		
		// i<size;
		@Override
		public boolean hasNext() {
			return index < size();
		}
		
		// i++;
		@Override
		public E next() {
			return arr[index++];
		}
		
	}
	
	



}
