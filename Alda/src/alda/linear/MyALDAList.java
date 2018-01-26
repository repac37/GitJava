 //	Mikael Tofvesson: mito2023@student.su.se, Emil Oja: emoj8928@student.su.se

package alda.linear;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyALDAList<E> implements ALDAList<E> {

	private int size;
	private Node<E> head;

	private static class Node<E> {
		E data;
		Node<E> next;
		public Node(E data, Node<E> next) {

			this.data = data;
			this.next = next;

		}
	}
	
	public MyALDAList() {
		head = new Node<E>(null, null);
		size = 0;
	}

	@Override
	public void add(E element) {

		if (size() == 0) {
			head.next = new Node<E>(element, null);
		} else {
			Node<E> temp = head.next;
			while (temp.next != null) {
				temp = temp.next;
			}

			temp.next = new Node<E>(element, null);

		}
		size++;

	}

	@Override
	public void add(int index, E element) {
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException("Index utanför bounds! " + index);
		}
		Node<E> nb = getNodeBefore(index);
		Node<E> newNode = new Node<E>(element, nb.next);
		nb.next = newNode;
		size++;
	}

	@Override
	public E remove(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException("Index utanför bounds! " + index);
		}

		Node<E> beforRemove = getNodeBefore(index);
		Node<E> remove = beforRemove.next;
		beforRemove.next = remove.next;
		size--;
		return remove.data;
	}

	private Node<E> getNodeBefore(int index) {
		Node<E> theNode = head;
		for (int i = 0; i < index; i++) {
			theNode = theNode.next;
		}

		return theNode;
	}

	@Override
	public boolean remove(E element) {

		Node<E> current = head.next;

		for (int i = 0; i < size(); i++) {
			if (current.data == element) {
				remove(i);
				return true;
			}
			current = current.next;
		}
		return false;
	}

	@Override
	public E get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException("Index out of bounds! " + index);
		}
		Node<E> current = head.next;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		return current.data;
	}

	@Override
	public boolean contains(E element) {
		if (size() == 0) {
			throw new IllegalStateException("List is empty");
		}
		Node<E> current = head.next;

		for (int i = 0; i < size(); i++) {
			if (current.data == element) {
				return true;
			}
			current = current.next;
		}
		return false;
	}

	@Override
	public int indexOf(E element) {
		Node<E> current = head.next;

		for (int i = 0; i < size(); i++) {
			if (current.data == element) {
				return i;
			}
			current = current.next;
		}

		return -1;
	}

	@Override
	public void clear() {
		Node<E> temp = new Node<E>(null, null);
		head = temp;
		size = 0;
	}

	@Override
	public int size() {
		return this.size;
	}

	public boolean testEmpty() {
		return size() == 0;
	}

	@Override
	public Iterator<E> iterator() {
		if (size() == 0) {
			throw new NoSuchElementException("List is empty");
		}
		return new AldaIterator();
	}

	private class AldaIterator implements java.util.Iterator<E> {
		private Node<E> current = head.next;
		private Node<E> remove = null;
		private boolean  isRemovable = false;
	

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public E next() {
			if (!hasNext()) {
				throw new java.util.NoSuchElementException();
			}
			E nextItem = current.data;
			remove = current;
			current = current.next;

			isRemovable = true;
			return nextItem;
		}

		public void remove() {
			if (!isRemovable) {
				throw new IllegalStateException();
			}
			MyALDAList.this.remove(remove.data);
			isRemovable = false;
		}
	}

	public String toString() {
		if (size == 0)
			return "[]";
		String summary = "[";
		Node<E> tmp = head.next;
		while (tmp.next != null) {
			summary += tmp.data.toString() + ", ";
			tmp = tmp.next;
		}

		return summary += tmp.data.toString() + "]";
	}

}
