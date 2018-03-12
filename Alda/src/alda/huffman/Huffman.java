package alda.huffman;

import java.util.*;
import java.util.Scanner;

/**
 * 
 * This class compresses a string of the users choice with Huffman’s algorithm
 * and then decompresses it.
 * 
 * @author Mikael Tofvesson: mito2023@student.su.se & Emil Oja:
 *         emoj8928@student.su.se
 * @version 1.0
 * @since 2018-03-09
 */

public class Huffman {

	private Map<Character, Node> charFrequencys = new HashMap<>();
	private Node root;
	private String encode;
	private Map<Character, String> codeIndex = new HashMap<>();
	private Map<String, Character> decodeIndex = new HashMap<>();

	private static class Node implements Comparable<Node> {

		private char data;
		private Node left;
		private Node right;
		private int frequency;

		private Node(char data) {
			this.data = data;
			frequency++;

		}

		private Node(Node left, Node right) {
			this.left = left;
			this.right = right;
			data = '\0';
			frequency = left.frequency + right.frequency;

		}

		@Override
		public int compareTo(Node other) {
			if (frequency == other.frequency) {
				return this.data - other.data;
			}
			return frequency - other.frequency;
		}

	}

	public void input(String message) {
		if (message.isEmpty()) {
			throw new IllegalArgumentException("Strängen får inte var tom");
		}
		emptyMaps();

		charFrequencys(message);
		createHuffmanTree();

		if (charFrequencys.size() == 1) {
			codeIndex.put(root.data, "0");
			decodeIndex.put("0", root.data);
		} else {
			createCodes(root, "");
		}

		encode(message);

	}

	private void charFrequencys(String message) {
		for (char e : message.toCharArray()) {
			if (charFrequencys.containsKey(e)) {
				charFrequencys.get(e).frequency++;
			} else {
				charFrequencys.put(e, new Node(e));
			}
		}
	}

	private void createHuffmanTree() {
		PriorityQueue<Node> pq = new PriorityQueue<>(charFrequencys.values());
		while (pq.size() > 1) {
			Node left = pq.poll();
			Node right = pq.poll();
			Node t = new Node(left, right);
			pq.add(t);
		}
		root = pq.poll();
	}

	private void createCodes(Node node, String code) {
		if (node.left != null) {
			createCodes(node.left, code + "0");
		}

		if (node.right != null) {
			createCodes(node.right, code + "1");
		}

		if (node.left == null && node.right == null) {
			codeIndex.put(node.data, code);
			decodeIndex.put(code, node.data);
		}
	}

	private void encode(String message) {
		StringBuilder sbEncode = new StringBuilder();
		for (char e : message.toCharArray()) {
			sbEncode.append(codeIndex.get(e));
		}

		encode = sbEncode.toString();
		System.out.println("encode: " + encode);
	}

	/**
	 * This method decodes the stored "binary" String <code>encode</code> and
	 * returns it to the original state. This method uses two StringBuilder
	 * objects, <code>tmp</code> and <code>scDecode</code>. Iteration is done
	 * over the String <code>encode</code>. During iteration each char is
	 * appened to <code>tmp</code>. If the Map <code>decodeIndex</code> contains
	 * a key equal to <code>tmp</code>, its value is appended to
	 * <code>sbcodeDecode</code> and <code>tmp</code> is emptied. When the
	 * iteration is done the <code>sbDecode.toString()</code> is returned.
	 * 
	 * @return String - This return a decoded message stored in this class as
	 *         string.
	 * @throws NullPointerException
	 *             If the stored String "encode" is null or empty
	 */
	public String decode() {
		if (encode == null || encode.isEmpty()) {
			throw new NullPointerException("Det finns inget att avkoda.");
		}
		StringBuilder tmp = new StringBuilder();
		StringBuilder sbDecode = new StringBuilder();
		for (char e : encode.toCharArray()) {
			tmp.append(e);
			if (decodeIndex.containsKey(tmp.toString())) {
				sbDecode.append(decodeIndex.get(tmp.toString()));
				tmp.setLength(0);
			}
		}
		return sbDecode.toString();
	}

	private void emptyMaps() {
		charFrequencys.clear();
		codeIndex.clear();
		decodeIndex.clear();
		;

	}

	public static void main(String args[]) {
		new Huffman().run();
	}

	private void run() {
		Huffman hm = new Huffman();
		Scanner scanner = new Scanner(System.in);
		boolean exit = false;
		while (!exit) {
			System.out.print("Enter something: ");
			String inputString = scanner.nextLine();
			hm.input(inputString);
			System.out.println("decode: " + hm.decode() + "\n");
			System.out.print("Quit? (Y/N): ");
			inputString = scanner.nextLine();
			if (inputString.toLowerCase().equals("y") || inputString.toLowerCase().equals("yes")) {
				System.out.println("Bye..");
				break;
			}
			System.out.println();
		}

		scanner.close();

	}
}
