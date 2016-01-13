package huffmantree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class HuffmanTree {
	
	private HuffmanTreeNode root;
	
	public HuffmanTree(){
		root = new HuffmanTreeNode(null);
	}
	
	public HuffmanTree(String encodeScheme) throws FileNotFoundException{
		root = new HuffmanTreeNode(null);
		addTree(encodeScheme);
	}
	
	public void addTree(String encodeScheme) throws FileNotFoundException{
		File f = new File(encodeScheme);
		Scanner s = new Scanner(f);
		while(s.hasNext()){
			String encodeLine = s.nextLine();
			String letter = encodeLine.substring(0, 1);
			String place = encodeLine.substring(1);
			addNode(letter, place, root);
		}
		s.close();
	}
	
	private void addNode(String value, String place, HuffmanTreeNode currNode){
		if (place.length() == 1){
			if (place.startsWith("1")){
				HuffmanTreeNode newNode = new HuffmanTreeNode(value);
				currNode.right = newNode;
			}
			else{
				HuffmanTreeNode newNode = new HuffmanTreeNode(value);
				currNode.left = newNode;
			}
			return;
		}
		
		else if(place.startsWith("1")){
			if(currNode.right == null)
				currNode.right = new HuffmanTreeNode("blah");
			addNode(value, place.substring(1), currNode.right);
		}
		else if(place.startsWith("0")){
			if(currNode.left == null){
				
				currNode.left = new HuffmanTreeNode("blah");
			}
			addNode(value, place.substring(1), currNode.left);
		}
	}
	
	public String decodeMessage(String messagePath) throws FileNotFoundException{
		File m = new File(messagePath);
		Scanner s = new Scanner(m);
		String decodeMessage = "";
		
		while (s.hasNextLine()){
		String message = s.nextLine();
		
		HuffmanTreeNode currNode = root;
		for (int i = 0; i < message.length(); i++){
			if (message.substring(i, i + 1).equals("1")){
				if (currNode.right.value.equals("blah"))
					currNode = currNode.right;
				else{
					decodeMessage += currNode.right.value;
					currNode = root;
				}
			}
			else{
				if (currNode.left.value.equals("blah"))
					currNode = currNode.left;
				else{
					decodeMessage += currNode.left.value;
					currNode = root;
				}
			}
			
		}
		decodeMessage += "\n";
		}
		
		return decodeMessage;
	}
	
	public class HuffmanTreeNode {
		private String value;
		private HuffmanTreeNode left;
		private HuffmanTreeNode right;

		public HuffmanTreeNode(String initValue) {
			value = initValue;
			left = null;
			right = null;
		}

		public HuffmanTreeNode(String initValue, HuffmanTreeNode initLeft, HuffmanTreeNode initRight) {
			value = initValue;
			left = initLeft;
			right = initRight;
		}

		public String getValue() {
			return value;
		}

		public HuffmanTreeNode getLeft() {
			return left;
		}

		public HuffmanTreeNode getRight() {
			return right;
		}

		public void setValue(String theNewValue) {
			value = theNewValue;
		}

		public void setLeft(HuffmanTreeNode theNewLeft) {
			left = theNewLeft;
		}

		public void setRight(HuffmanTreeNode theNewRight) {
			right = theNewRight;
		}
	}
}
