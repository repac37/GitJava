package avl.tree;

public class AVL_Tree<T extends Comparable<T>> {
	private int currentSize;
	private AVLNode<T> root;

	public AVL_Tree() {
		root = null;
		currentSize = 0;
	}

	public boolean add(T data) {

		AVLNode<T> node = new AVLNode<T>(data);
		if (currentSize > 0 && contains(data)) {
			return false;
		}
		if (root == null) {
			root = node;
			currentSize++;
			return true;
		}
		add(root, node);
		return true;
	}

	public int size() {
		return currentSize;
	}

	public void clear() {
		this.root = null;
		currentSize = 0;
	}

	private void add(AVLNode<T> parent, AVLNode<T> newNode) {
		if (newNode.compareTo(parent) > 0) {
			if (parent.getRight() == null) {
				parent.setRight(newNode);
				newNode.setParent(parent);
				currentSize++;
			} else {
				add(parent.getRight(), newNode);
			}
		} else {
			if (parent.getLeft() == null) {
				parent.setLeft(newNode);
				newNode.setParent(parent);
				currentSize++;
			} else {
				add(parent.getLeft(), newNode);
			}
		}
		checkBalance(newNode);
	}

	private void checkBalance(AVLNode<T> node) {

		if ((height(node.getLeft()) - height(node.getRight())) > 1) {
			rebalance(node);
		}
		if (node.getParent() == null) {
			return;
		}
		checkBalance(node.getParent());
	}

	public int depth() {
		return height();
	}

	private int height() {
		if (root == null) {
			return -1;
		} else {
			return height(root) - 1;
		}
	}

	private int height(AVLNode<T> node) {
		if (node == null) {
			return 0;
		} else {
			
			int leftHeight = height(node.getLeft())+1;
			int rightHeight = height(node.getRight()) + 1;
			
			return leftHeight>rightHeight?leftHeight:rightHeight;
		}
	}

	private void rebalance(AVLNode<T> node) {
		if (height(node.getLeft()) - height(node.getRight()) > 1) {
			if (height(node.getLeft().getLeft()) > height(node.getLeft().getRight())) {
				node = rightRotate(node);
			} else {
				node = leftRightRotate(node);
			}
		} else {
			if (height(node.getRight().getRight()) > height(node.getRight().getLeft())) {
				node = leftRotate(node);
			} else {
				node = rightLeftRotate(node);
			}
		}
		if (node.getParent() == null) {
			root = node;
		}
	}

	private AVLNode<T> rightRotate(AVLNode<T> node) {

		AVLNode<T> tmp = node.getLeft();
		if (node.getParent() != null) {
			node.getParent().setLeft(tmp);
		}
		node.setLeft(tmp.getRight());
		tmp.setRight(node);
		tmp.setParent(node.getParent());
		node.setParent(tmp);

		return tmp;
	}

	private AVLNode<T> leftRotate(AVLNode<T> node) {

		AVLNode<T> tmp = node.getRight();
		if (node.getParent() != null) {
			node.getParent().setRight(tmp);
		}
		node.setRight(tmp.getLeft());
		tmp.setLeft(node);
		tmp.setParent(node.getParent());
		node.setParent(tmp);

		return tmp;
	}

	private AVLNode<T> leftRightRotate(AVLNode<T> node) {
		node.setLeft(leftRotate(node.getLeft()));
		return rightRotate(node);
	}

	private AVLNode<T> rightLeftRotate(AVLNode<T> node) {
		node.setRight(rightRotate(node.getRight()));
		return leftRotate(node);
	}

	public T getRootValue() {
		return root.getValue();
	}

	public boolean contains(T data) {
		return root == null ? false : contains(data,root);
	}

	private boolean contains(T data, AVLNode<T> node) {
		if (node == null) {
			return false;
		}
		int compareResult = data.compareTo(node.getValue());

		if (compareResult < 0) {
			return contains(data, node.getLeft());
		} else if (compareResult > 0) {
			return contains(data, node.getRight());
		} else {
			return true;
		}
	}

	public boolean remove(T data) {
	
		int originalSize = size();
		AVLNode<T> tmp = null;
		if (root != null) {
			tmp = remove(data, root);
		}
		if (tmp != null) {
			currentSize--;
		}
		return size() < originalSize;
	}

	private AVLNode<T> remove(T data, AVLNode<T> node) {
		if (node == null) {
			return node;
		}

		int compareResult = data.compareTo(node.getValue());

		if (compareResult < 0) {
			node.setLeft(remove(data, node.getLeft()));
		} else if (compareResult > 0) {
			node.setRight(remove(data, node.getRight()));
		} else if (node.getLeft() != null && node.getRight() != null) {
			node.setValue(findMin(node.getRight()).getValue());
			node.setRight(remove(node.getValue(), node.getRight()));

		} else {

			node = (node.getLeft() != null) ? node.getLeft() : node.getRight();
		}

		checkBalance(root);
		return node;
	}

	public AVLNode<T> findMin(AVLNode<T> node) {
		if (node == null) {
			return null;
		} else if (node.getLeft() == null) {
			return node;
		}
		return findMin(node.getLeft());
	}
	
	public AVLNode<T> findMax(AVLNode<T> node) {
		if(node != null){
			while(node.getRight() != null){
				node = node.getRight();
			}
		}
		return node;
	}
	
	public String toString() {
		return "[" + (root == null ? "" : root.toString()) + "]";
	}


}
