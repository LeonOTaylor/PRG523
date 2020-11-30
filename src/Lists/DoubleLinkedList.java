package Lists;

import Lists.LinkedList.Node;; // THIS SHOULD BE DOUBLELINKEDLIST?!?!?

public class DoubleLinkedList {
	
	Node head, tail = null; // SHOULD THESE BE NULL!?!?!?!
	
	public DoubleLinkedList() {}
	
	public DoubleLinkedList(Object data)
	{
		this.head = new Node(data);
	}
	
	public static class Node {
		Object data; // Data to be stored
		Node previous; // Will store reference to previous item
		Node next; // Will store reference to next item
		
		public Node(Object data)
		{
			//Node prev; // Reference to previous node - head should be set to null?
			//Node next; // Reference to next node - tail should be set to null?
			this.data = data;
		}
		
		public Object getData()
		{
			return data;
		}
		
		public void setData(Object data)
		{
			this.data = data;
		}
		
		public Node getNext()
		{
			return next;
		}
		
		public Node getPrevious()
		{
			return previous;
		}
	}
	
	public Node traverse(Node node)
	{
		while(node.next != null)
		{
			//System.out.println("TraverseAAL: " + node.getData());
			node = node.next;
		}
		//System.out.println("TraversALL LAST: " + node.getData());
		return node;
	}
	
	// Method: Prepend
	public void prepend(Node node)
	{
		if(this.head == null || this.tail == null) // If there aren't any links in the list yet
		{
			this.head = node;
			this.tail = node;
		}else {
			node.next = head; // this.head
			node.previous = null;
			head.previous = node;
			this.head = node; // SHOULD IT BE this.head?????
		}
		
	}
	
	// Method: Append 
	public void append(Object data)
	{
		Node node = new Node(data);
		if(this.head == null || this.tail == null) // MAKE SURE THIS WORKS AS EXPECTED
		{
			this.head = node;
			this.tail = node;
		} else {
			this.tail.next = node;
			node.previous = this.tail; //REALLY A GUESSSS -
			node.next = null; 
			this.tail = node;
			
		}
	}
	
	public Node find(Object data)
	{
		Node node = this.head;
		while(node != null)
		{
			if(node.data == data)
				return node;
			node = node.next;
		}
		return null; // If data not found
	}
	
	public Node delete(Object data)
	{
		Node node = find(data);
		
		if(node == null || head == null)
			return null; // Nothing to delete
		
		if(node.previous == null) // Add comments to describe everything that the method does
			head = node.next;
		
		if(node.next != null)
			node.next.previous = node.previous;
		
		if(node.previous != null)
			node.previous.next = node.next;
		
		return node;
	}
	
	// Method: Insert/After
	public void insertAfter(Node nodeBefore, Node newNode)
	{
		if(nodeBefore == null)
		{
			append(newNode);
			return;
		}
		
		if(this.tail == nodeBefore)
			this.tail = newNode;
		
		newNode.next = nodeBefore.next;  //sets next ptr of newnode to the next ptr of the previous node
		nodeBefore.next = newNode; // sets the beforenodes next pointer to point to the newnode
		newNode.previous = nodeBefore; // sets the previous pointer of the newnode to the nodebefore
		if(newNode.next != null) //
			newNode.next.previous = newNode; // IS THIS RIGHT???? - is it nodeBefore.next.previous = newNode;
		
		//if(nodeBefore.next != null)
			//nodeBefore.next.previous = newNode;
	}
	
	public String toString()
	{
		Node theNode = this.head;
		String str = "";
		
		while(theNode != null)
		{
			str += theNode.data.toString();
			if(theNode.next != null)
			{
				str += "\n";
			}
			theNode = theNode.next;
		}
		
		return str;
	}
	
	// Method: To string reverse - backwards order!
	public String toStringReverse()
	{
		Node theNode = this.tail;
		String str = "REVERSED DOUBLY LINKED LIST: ";
		
		while(theNode != null)
		{
			str += theNode.data.toString();
			if(theNode.previous != null)
			{
				str += " -> ";
			}
			theNode = theNode.previous;
		}
		
		return str;
	}
	
	
	public static void main(String[] args) {
		DoubleLinkedList list = new DoubleLinkedList();
		//list.head.previous = null; // Should this go here?!?!
		//list.head.data = 2;
		//list.head.next = list.tail;
		list.prepend(new DoubleLinkedList.Node(6));
		list.prepend(new DoubleLinkedList.Node(7));
		list.prepend(new DoubleLinkedList.Node(8));
		list.insertAfter(list.head, new DoubleLinkedList.Node(111));
		list.append(new DoubleLinkedList.Node(44));
		list.prepend(new DoubleLinkedList.Node(9));
		list.append(new DoubleLinkedList.Node(33));
		System.out.println(list.toString());
		System.out.println(list.toStringReverse());
		list.delete(44);
		list.traverse(list.head);

	}

}
