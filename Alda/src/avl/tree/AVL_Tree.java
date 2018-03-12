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

	private void add(AVLNode<T> parent, AVLNode<T> newNode) {
		if (newNode.getValue().compareTo(parent.getValue()) > 0) {
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

		if (balanceFactor(node) > 1||balanceFactor(node)<-1) {
			rebalance(node);
		}
		if (node.getParent() == null) {
			return;
		}
		checkBalance(node.getParent());
	}

	private void rebalance(AVLNode<T> node) {
		if (balanceFactor(node) > 1) {
			if (rotationFactorCaseOne(node)) {
				
				node = rightRotate(node);
			} else {
				
				node = doubelLeftRightRotate(node);
//				System.out.println(node.getValue());
//				System.out.println(node.getRight().getValue());
//				System.out.println(node.getRight().getRight().getValue());
			}
		} else {
			if (rotationFactorCaseTwo(node)) {
				node = leftRotate(node);
				
			} else {
				node = doubelRightLeftRotate(node);
				
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
		System.out.println("left"+tmp.getValue());
		return tmp;
	}

	private AVLNode<T> doubelLeftRightRotate(AVLNode<T> node) {
		AVLNode<T> tmp = leftRotate(node.getLeft());
		System.out.println(tmp.getParent().getRight());
		node.setLeft(tmp.getLeft());
		
//		System.out.println(node.getRight().getValue());
//		System.out.println(node.getValue());
//		System.out.println(node.getLeft().getValue());
//		System.out.println(node.getLeft().getLeft().getValue());
		return rightRotate(node);
	}

	private AVLNode<T> doubelRightLeftRotate(AVLNode<T> node) {
		node.setRight(rightRotate(node.getRight()));
		return leftRotate(node);
	}
	
	private boolean rotationFactorCaseTwo(AVLNode<T> node) {
		return height(node.getRight().getRight()) > height(node.getRight().getLeft());
	}

	private boolean rotationFactorCaseOne(AVLNode<T> node) {
		return height(node.getLeft().getLeft()) > height(node.getLeft().getRight());
	}
	
	private int balanceFactor(AVLNode<T> node) {
		return height(node.getLeft()) - height(node.getRight());
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
		}
		
		int leftHeight = height(node.getLeft()) + 1;
		int rightHeight = height(node.getRight()) + 1;
		return leftHeight > rightHeight ? leftHeight : rightHeight;
	}

	public T getRootValue() {
		return root.getValue();
	}

	public boolean contains(T data) {
		return root == null ? false : contains(data, root);
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
		if (node != null) {
			while (node.getRight() != null) {
				node = node.getRight();
			}
		}
		return node;
	}

	public int size() {
		return currentSize;
	}

	public void clear() {
		this.root = null;
		currentSize = 0;
	}

	public String toString() {
		return "[" + (root == null ? "" : root.toString()) + "]";
	}

}
