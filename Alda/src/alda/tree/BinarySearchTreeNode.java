package alda.tree;

/**
 * 
 * Detta är den enda av de tre klasserna ni ska göra några ändringar i. (Om ni
 * inte vill lägga till fler testfall.) De ändringar som är tillåtna är dock
 * begränsade av följande:
 * <ul>
 * <li>Ni får INTE lägga till några fler instansvariabler.
 * <li>Ni får INTE lägga till några statiska variabler.
 * <li>Ni får INTE använda några loopar någonstans.
 * <li>Ni FÅR lägga till fler metoder, dessa ska då vara privata.
 * </ul>
 * 
 * @author henrikbe
 * 
 * @param <T>
 */

public class BinarySearchTreeNode<T extends Comparable<T>> {

	private T data;
	private BinarySearchTreeNode<T> left;
	private BinarySearchTreeNode<T> right;

	public BinarySearchTreeNode(T data) {
		this.data = data;
	}

	public boolean add(T data) {
		if (!contains(data)) {
			insert(data, this);
			return true;
		} else {
			return false;
		}
	}

	private BinarySearchTreeNode<T> insert(T data, BinarySearchTreeNode<T> node) {
		if (node == null) {
			return new BinarySearchTreeNode<T>(data);
		}
		int compareResult = data.compareTo(node.data);

		if (compareResult < 0) {
			node.left = insert(data, node.left);
		} else if (compareResult > 0) {
			node.right = insert(data, node.right);
		} else {
			System.out.println("hej");
			return null;
		}

		return node;
	}

	private BinarySearchTreeNode<T> findMin(BinarySearchTreeNode<T> node) {
		if(node == null){
			return node;
		}else if(node.left ==  null){
			return node;
		}
		return findMin(node.left);
	}

	public BinarySearchTreeNode<T> remove(T data) {
		if(data == null){
			throw new NullPointerException();
		}
		if(contains(data)){
		return remove(data,this);
		}
		return this;
	}
		
	private BinarySearchTreeNode<T> remove(T data, BinarySearchTreeNode<T> node){
		
		int compareResult = data.compareTo(node.data);
		
		if(compareResult < 0){
			node.left = remove(data, node.left);
		}else if(compareResult > 0){
			node.right = remove(data, node.right);
		}else if(node.left != null && node.right != null){
			node.data = findMin(node.right).data;
			node.right= remove(node.data, node.right);
		}else if(node.left != null && node.right == null){
				node = node.left;
		}else{
			node = node.right;
		}
		return node;
	}

	public boolean contains(T data) {

		return contains(data, this);
	}

	private boolean contains(T data, BinarySearchTreeNode<T> node) {
		if (node == null) {
			return false;
		}
		int compareResult = data.compareTo(node.data);

		if (compareResult < 0) {
			return contains(data, node.left);
		} else if (compareResult > 0) {
			return contains(data, node.right);

		} else {
			return true;
		}
	}

	public int size() {
		return size(this);
	}

	private int size(BinarySearchTreeNode<T> node) {
		int count = 0;
		if (node != null) {
			count += size(node.left);
			count++;
			count += size(node.right);
		}
		return count;
	}

	public int depth() {

		return depth(this);
	}

	private int depth(BinarySearchTreeNode<T> node) {
		if (node == null)
			return -1;
		else {
			
			int leftDepth = depth(node.left);		
			int rightDepth = depth(node.right);
			
			if (leftDepth > rightDepth){
				return (leftDepth + 1);
			} else 
				return (rightDepth + 1);
		}
	}

	public String toString() {
		StringBuilder s = printTree(this);
		s.replace(s.length() - 2, s.length(), "");
		return s.toString();
	}

	private StringBuilder printTree(BinarySearchTreeNode<T> node) {
		StringBuilder s = new StringBuilder();
		if (node != null) {
			s.append(printTree(node.left));
			s.append(node.data + ", ");
			s.append(printTree(node.right));
		}
		return s;

	}

}
