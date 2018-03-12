package avl.tree;

public class AVLNode<T extends Comparable<T>> {
	private T data;
	private AVLNode<T> left;
	private AVLNode<T> right;
	private AVLNode<T> parent;

	public AVLNode(T data) {
		this.data = data;
		setParent(null);
		setRight(null);
		setLeft(null);
	}

	public T getValue() {
		return this.data;
	}

	public AVLNode<T> getRight() {
		return right;
	}

	public void setRight(AVLNode<T> right) {
		this.right = right;
	}

	public AVLNode<T> getLeft() {
		return left;
	}

	public void setLeft(AVLNode<T> left) {
		this.left = left;
	}

	public void setValue(T data) {
		this.data = data;
	}

	public AVLNode<T> getParent() {
		return parent;
	}

	public void setParent(AVLNode<T> parent) {
		this.parent = parent;
	}

	public String print() {
		return this.getValue().toString();
	}

	public String toString() {
		StringBuilder s = printTree(this);
		s.replace(s.length() - 2, s.length(), "");
		return s.toString();
	}

	private StringBuilder printTree(AVLNode<T> node) {
		StringBuilder s = new StringBuilder();
		if (node != null) {
			s.append(printTree(node.getLeft()));
			s.append(node.getValue() + ", ");
			s.append(printTree(node.getRight()));
		}
		return s;

	}

}
