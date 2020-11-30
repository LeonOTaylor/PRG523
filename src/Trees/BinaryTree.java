package Trees;

import java.util.*;

public class BinaryTree {
	Node root;
		
	public static class Node {
		int key; // Node key valie
		Node left, right; // References to a particular nodes neighbours
		Object data; // The data being stored in the node
		
		// Below is the constructor for the Node class
		public Node(int key, Object data)
		{
			this.key = key;
			this.data = data;
		}
		
		// Returns the key
		public int getKey()
		{
			return key;
		}
		
		public void setData(Object data)
		{
			this.data = data;
		}
		
		public Object getData()
		{
			return data;
		}
		
		public String toString()
		{
			return Integer.toString(this.key) + " = " + data.toString();
		}
	}
		
	public BinaryTree() {}
	
	public BinaryTree(List<Node> nodes)
	{
		for(Node node: nodes)
			this.insert(node);
	}
	
	// Constructor from list
	
	public Node find(int key)
	{
		return find(this.root, key);
	}
	
	// IF NODE DOESN'T EXIST - IT GIVES ERRORSSSS
	private Node find(Node focusNode, int key)
	{
		if(focusNode.key == key)
			return focusNode;
		else if(key < focusNode.key)
		{
			return find(focusNode.left, key);
		}
		else if(key > focusNode.key)
		{
			return find(focusNode.right, key);
		}
		else
			return null;
	}

	public void insert(Node insertNode)
	{
		if (this.root == null)
		{
			this.root = insertNode;
		}
		else
		{
			insert(this.root, insertNode); // There is already a root node
		}
	}
	
	public void insert(Node focusNode, Node insertNode)
	{
		// FOR KEYS LESS THAN FOCUS NODE
		if(insertNode.key < focusNode.key) // IS USING KEY CORRECT?!?!?!?!
		{
			if(focusNode.left == null)
				focusNode.left = insertNode;
			else
				insert(focusNode.left, insertNode);
		}
		else if(insertNode.key > focusNode.key)
		{
			if(focusNode.right == null)
				focusNode.right = insertNode;
			else
				insert(focusNode.right, insertNode);
		}
	}
	
	public ArrayList<Node> traversePreOrder()
	{
		return traversePreOrder(this.root);
	}
	
	private ArrayList<Node> traversePreOrder(Node focusNode)
	{
		ArrayList<Node> nodes = new ArrayList<>();
		
		if(focusNode != null)
		{
			nodes.add(focusNode);
			nodes.addAll(traversePreOrder(focusNode.left));
			nodes.addAll(traversePreOrder(focusNode.right));
		}
		
		return nodes;
	}
	
	// Safer, will always use the root node
	public ArrayList<Node> traverseInOrder()
	{
		return traverseInOrder(this.root);
	}
	
	private ArrayList<Node> traverseInOrder(Node focusNode)
	{
		ArrayList<Node> nodes = new ArrayList<>();
		
		if(focusNode != null)
		{
			nodes.addAll(traverseInOrder(focusNode.left));
			nodes.add(focusNode);
			nodes.addAll(traverseInOrder(focusNode.right));
		}
		
		return nodes;
	}
	
	public ArrayList<Node> traversePostOrder()
	{
		return traversePostOrder(this.root);
	}
		
	private ArrayList<Node> traversePostOrder(Node focusNode)
	{
		ArrayList<Node> nodes = new ArrayList<>();
		
		if(focusNode != null)
		{
			nodes.addAll(traversePostOrder(focusNode.left));
			nodes.addAll(traversePostOrder(focusNode.right));
			nodes.add(focusNode);
		}
		
		return nodes;
	}
	
	public static void main(String[] args)
	{
		BinaryTree tree = new BinaryTree();
		
		tree.insert(new Node(40, "Hello")); // tree.root is where the recursive algorithm starts - IS DANGEROUS
		tree.insert(new Node(30, "Goodbye"));
		tree.insert(new Node(50, "Goodhi"));
		tree.insert(new Node(55, "Gday"));
		tree.insert(new Node(20, "NIHOW"));
		tree.insert(new Node(10, "Konnichiwa"));
		
		/*Node[] nodes = new Node[] {
				new Node(44, "Maybe"),
				new Node(26, "Baby"),
				new Node(32, "Rabie")
		};
		BinaryTree tree = new BinaryTree(List.of(nodes));*/
		
		System.out.println("In order: " + tree.traverseInOrder());
		System.out.println("Preorder: " + tree.traversePreOrder());
		System.out.println("Postorder: " + tree.traversePostOrder());
		
		//tree.find(30).data = "GOODBYE"; // GIVES ERROR
		System.out.println(tree.traverseInOrder());
	}
}
