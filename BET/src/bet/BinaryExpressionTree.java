package bet;

import java.util.Scanner;

public class BinaryExpressionTree {
	
	private TreeNode<String> root;
	
	public BinaryExpressionTree(String prefix){
		root = null;
		Scanner s = new Scanner(prefix);
		root = new TreeNode<String>(s.next());
		push(root, s);
		s.close();
	}
	
	public TreeNode<String> push(TreeNode<String> node, Scanner s){
		if (node != root)
			node = new TreeNode<String>(s.next());
		
		if (!isDouble(node.getValue())){
			node.setLeft(push(node.getLeft(), s));
			node.setRight(push(node.getRight(), s));
		}
		return node;
	}
	
	private boolean isDouble(String key){
		try{
			Double.parseDouble(key);
			return true;
		}
		catch(NumberFormatException e){
			return false;
		}
	}
	
	public String inorder(){
		String thing = "";
		thing += root.getValue() + " ";
		thing += root.getLeft().getValue() + " ";
		thing += root.getLeft().getLeft().getValue() + " ";
		thing += root.getLeft().getLeft().getLeft().getValue() + " ";
		thing += root.getLeft().getLeft().getRight().getValue() + " ";
		thing += root.getLeft().getRight().getValue() + " ";
		thing += root.getLeft().getRight().getLeft().getValue() + " ";
		thing += root.getLeft().getRight().getRight().getValue() + " ";
		thing += root.getRight().getValue() + " ";
		return thing;
	}
	
	
}
