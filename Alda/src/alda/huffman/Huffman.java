 /**
  * 	Mikael Tofvesson: mito2023@student.su.se och Emil Oja: emoj8928@student.su.se
  */

package alda.huffman;

import java.util.*;
import java.util.Scanner;

public class Huffman {

	private Scanner input = new Scanner(System.in);

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
		emptyMaps();
		if (message.isEmpty()) {
			throw new IllegalArgumentException("Strängen får inte var tom");
		}
		for (char e : message.toCharArray()) {
			if (charFrequencys.containsKey(e)) {
				charFrequencys.get(e).frequency++;
			} else {
				charFrequencys.put(e, new Node(e));
			}
		}

		//printFreq();
		createHuffmanTree();
		if (charFrequencys.size() == 1) {
			codeIndex.put(root.data, "0");
			decodeIndex.put("0", root.data);
		} else {
			createCodes(root, "");
		}
		encode(message);

	}

	private void printFreq() {
		System.out.println("Frequency tabel:");

		for (Map.Entry<Character, Node> set : charFrequencys.entrySet()) {
			System.out.println(set.getKey() + " : " + set.getValue().frequency);
		}
		System.out.println("Size:" + charFrequencys.size());

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

	private void encode(String message) {
		StringBuilder sbEncode = new StringBuilder();
		for (char e : message.toCharArray()) {
			sbEncode.append(codeIndex.get(e));
		}

		encode = sbEncode.toString();
		System.out.println("encode: " + encode);
	}

	public String decode() {
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
		decodeIndex.clear();;
		
	}

	public static void main(String args[]) {
		new Huffman().run();
	}

	private void run() {
		Huffman hm = new Huffman();
		boolean exit = false;
		while (!exit) {
			System.out.print("Enter something: ");
			String inputString = input.nextLine();
			hm.input(inputString);
			System.out.println("decode: " + hm.decode()+"\n");
			System.out.print("Quit? (Y/N): ");
			inputString = input.nextLine();
			if(inputString.toLowerCase().equals("y") || inputString.toLowerCase().equals("yes")){
				System.out.println("Bye..");
				break; 
			} 
			System.out.println();
		}
		
		input.close();

	}
}
