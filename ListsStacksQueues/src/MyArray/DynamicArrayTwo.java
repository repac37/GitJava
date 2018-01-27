package MyArray;

import java.util.Iterator;



@SuppressWarnings("unchecked")
public class DynamicArrayTwo<T> implements Iterable<T> {
	private static final int DEFAULT_CAPACITY = 10;
	private int capacity = 10;
	private int size;
	T[] arr = null;
	

	public DynamicArrayTwo(){
		clear();
	}
	
	public void add(T value){
		checkSize();
			arr[size++] = value;
	}
	
	public void add(int idx, T value){
		if(idx<0 || idx >size() ){
			throw new IndexOutOfBoundsException();
		}
		arr[idx]=value;
	}
	
	public T remove(int idx){
		if(idx<0 || idx >size() ){
			throw new IndexOutOfBoundsException();
		}
		T tmp = arr[idx];
		for (int i = idx; i < size(); i++) {
			arr[i] = arr[i+1]; 
		}
		size--;
		return tmp;
	}
	
	public boolean remove(T value){
		int index = indexOf(value);
		if(index>-1){
			remove(index);
			return true;
		}
		return false;
	}
	
	public boolean contains(T value){
		for (int i = 0; i < size(); i++) {
			if(arr[i].equals(value)){
				return true;
			}
		}
		return false;
	}
	
	public int indexOf(T value){
		for (int i = 0; i < arr.length; ++i) {
			if(arr[i].equals(value)){
				return i;
			}
		}
		return -1;
	}
	
	public void clear(){
		arr = null;
		arr = (T[]) new Object[DEFAULT_CAPACITY];
		size=0;
	}
	
	public Iterator<T> iterator() {
		
		return new DynamicIterator();
	}
	
	private class DynamicIterator implements Iterator<T>{
		int current = 0;
			
			@Override
		public boolean hasNext() {
			return current < size();
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new java.util.NoSuchElementException();
			}
			return arr[current++];
		}
		
		 @Override
         public void remove() {
			 DynamicArrayTwo.this.remove(--current);
		 }
			 
	}



	
	public int size() {
		return size;
	}
	
	private void checkSize(){
		if(size+1== capacity){
			T[] tmp = (T[]) new Object[capacity*2];
			for(int i = 0; i < size(); i++){
				tmp[i] = arr[i];
			}
			arr = tmp;
		}
	}

}
