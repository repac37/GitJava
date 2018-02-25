package trees;

public class Tree {
	public static void main(String[] arg){
		BinarySearchTree<Integer> BST = new BinarySearchTree<>();
		BST.insert(3);
		BST.insert(37);
		BST.insert(23);
		BST.insert(2);
		BST.insert(34);
		BST.insert(32);
		BST.insert(123);
		BST.insert(45);
		BST.insert(656);
		BST.insert(565);
		BST.insert(56);
		BST.insert(33);
		BST.insert(4);
		BST.insert(22);
		BST.insert(12);
		BST.insert(1);
		BST.insert(35);
	
		System.out.println();
		BST.remove(34);
	
		
	
		
	}
}
