package myTreeMap;


import java.util.AbstractMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

/**
 * An academic map that does not support null keys or values. Implements a subset of the methods of the java.util.TreeMap class and several other methods.
 * Methods behave as described in the documentation for java.util.TreeMap with the exception of guaranteed O(log n) runtime for basic operations. Since this
 * implementation does not ensure that the tree is balanced, the run time is potentially O(n).
 */
public class MyTreeMap<K extends Comparable<K>, V>
{
	private TreeNode<Map.Entry<K, V>> root;
	private int size; // number of mappings
	private TreeNode<Map.Entry<K, V>> removedNode;

	public MyTreeMap()
	{
		root = null;
		removedNode = null;
	}
	

	public int size()
	{
		return size;
	}

	public boolean containsKey(K key)
	{
		return get(key) != null;
	}
	
	public V put(K key, V value)
	{
		TreeNode<Map.Entry<K, V>> newNode = new TreeNode(new AbstractMap.SimpleEntry<K, V>(key, value));
		if (root == null){
			root = newNode;
			size++;
			return null;
		}
		else
		{
			TreeNode<Map.Entry<K, V>> returned = put(new AbstractMap.SimpleEntry<K, V>(key, value), root);
			if (returned == null){
				return null;
			}
			return returned.getValue().getValue();
		}
		
	}
	
	public V get(K key)
	{
		TreeNode<Map.Entry<K, V>> get = getNode(key);
		if (get == null)
			return null;
		return get.getValue().getValue();
	}
	
	public V remove(K key)
	{
		removedNode = null;
		remove(key, root);
		if (removedNode == null)
			return null;
		
		size --;
		return removedNode.getValue().getValue();
	}
	
	public Set<K> keySet()
	{
		Set<K> setKey = new TreeSet<K>();
		keySet(root, setKey);
		return setKey;
	}

	/**
	 * @return the map's keys based on a preorder traversal of the tree
	 */
	public String keysPreorder()
	{
		return keysPreorder(root);
	}

	private String keysPreorder(TreeNode<Map.Entry<K, V>> node)
	{
		if (node == null)
			return "";
		
		String traversal = "";
		traversal += node.getValue().getKey() + " ";
		traversal += keysPreorder(node.getLeft());
		traversal += keysPreorder(node.getRight());
		
		return traversal;
	}

	/**
	 * @return the map's keys based on an inorder traversal of the tree
	 */
	public String keysInorder()
	{
		return keysInorder(root);
	}

	private String keysInorder(TreeNode<Map.Entry<K, V>> node)
	{
		if (node == null)
			return "";
		
		String traversal = "";
		traversal += keysInorder(node.getLeft());
		traversal += node.getValue().getKey() + " ";
		traversal += keysInorder(node.getRight());
		
		return traversal;
	}

	/**
	 * @return the TreeNode (or null) to which the pointer previously pointing to node should point
	 */
	private TreeNode<Map.Entry<K, V>> put(Map.Entry<K, V> entry, TreeNode<Map.Entry<K, V>> node)
	{
		if (entry.getKey().compareTo(node.getValue().getKey()) < 0){
			if (node.getLeft() == null){
				node.setLeft(new TreeNode<Map.Entry<K, V>>(entry));
				size++;
				return null;
			}
			else
				put(entry, node.getLeft());
		}
		
		else if (entry.getKey().compareTo(node.getValue().getKey()) > 0){
			if (node.getRight() == null){
				node.setRight(new TreeNode<Map.Entry<K, V>>(entry));
				size++;
				return null;
			}
			else
				put(entry, node.getRight());
		}
		else {
			TreeNode<Map.Entry<K, V>> value = new TreeNode<Map.Entry<K, V>>(node.getValue());
			node.setValue(entry);
			return value;
		}
		return null;
	}

	/**
	 * @return the TreeNode (or null) to which the pointer previously pointing to node should point
	 */
	private TreeNode<Map.Entry<K, V>> remove(K key, TreeNode<Map.Entry<K, V>> node)
	{
		if (node == null)
			return null;
		if (key.compareTo(node.getValue().getKey()) < 0)
			node.setLeft(remove(key, node.getLeft()));
		else if (key.compareTo(node.getValue().getKey()) > 0)
			node.setRight(remove(key, node.getRight()));
		else if (node == root){
			TreeNode<Map.Entry<K, V>> removeNode = new TreeNode<Map.Entry<K, V>> (node.getValue());
			root = root.getRight();
			removedNode = removeNode;
		}
		else{
			TreeNode<Map.Entry<K, V>> removeNode = new TreeNode<Map.Entry<K, V>> (node.getValue());
			if (node.getLeft() != null && node.getRight() != null){
				TreeNode<Map.Entry<K, V>> repNode = node.getRight();
				while (repNode.getLeft() != null)
					repNode = repNode.getLeft();
				node.setValue(repNode.getValue());
				node.setRight(remove(repNode.getValue().getKey(), node.getRight()));
			}
			else if (node.getLeft() != null)
				node = node.getLeft();
			else if(node.getRight() != null)
				node = node.getRight();
			else
				node = null;
			removedNode = removeNode;
		}
		return node;
		
	}

	/**
	 * Appends the keys from the nodes in the tree rooted at node to specified set.
	 */
	private void keySet(TreeNode<Map.Entry<K, V>> node, Set<K> keys)
	{
		if (node == null)
			return;
		
		keySet(node.getLeft(), keys);
		keys.add(node.getValue().getKey());
		keySet(node.getRight(), keys);
		
	}

	/**
	 * @return the node associated with the specified key, or null if the key is not in the map.
	 */
	private TreeNode<Map.Entry<K, V>> getNode(K key)
	{
		if (root == null)
			return null;
		return getNode(key, root);
	}

	/**
	 * @return the node in the tree rooted at node associated with the specified key, or null if the key is not in the tree rooted at node.
	 */
	private TreeNode<Map.Entry<K, V>> getNode(K key, TreeNode<Map.Entry<K, V>> node)
	{
		
		if (key.compareTo(node.getValue().getKey()) < 0){
			if (node.getLeft() == null)
				return null;
			else
				return getNode(key, node.getLeft());
		}
		
		else if (key.compareTo(node.getValue().getKey()) > 0){
			if (node.getRight() == null)
				return null;
			else
				return getNode(key, node.getRight());
		}
		else {
			return node;
		}

	}
}
