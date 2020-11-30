package Lists;

import Lists.LinkedList.Node;

public class LinkedList {

	
	public static void main(String[] args) {
		LinkedList myList = new LinkedList();
		System.out.println(myList.toString());
		myList.append(new LinkedList.Node("a"));
		myList.append(new LinkedList.Node("b"));
		myList.append(new LinkedList.Node("c"));
		
		
		myList.prepend(new LinkedList.Node("x"));
		myList.prepend(new LinkedList.Node("y"));
		myList.prepend(new LinkedList.Node("z"));
		
		System.out.println(myList.toString());
		
		myList.find("b").setData("B"); 
		
		System.out.println(myList.toString());
		myList.insertAfter(myList.find("x"), new LinkedList.Node("X"));
		myList.insertAfter(myList.find("a"), new LinkedList.Node("A"));
		System.out.println(myList.toString());
		
		myList.remove(myList.find("x"));
		System.out.println(myList.toString());
		}
	
	Node head, tail;
	
	// Class inside a class - child class
	public static class Node {
		Object data;
		Node next;
		
		public Node(Object data)
		{
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
	}
	
	public void prepend(Node node)
	{
		if(this.head == null || this.tail == null)
		{
			this.head = node;
			this.tail = node;
		} else {
			node.next = this.head; // Make new first item
			this.head = node;
		}
	}
	
	public void append(Node node)
	{
		if(this.head == null || this.tail == null)
		{
			this.head = node;
			this.tail = node;
		} else {
			this.tail.next = node; // Sets the next node after tail to point to the node to be appended -
			this.tail = node; // Sets the tail to node - WHAT HAPPENS TO THE 2ND TO LAST VALUE OR OLD TAIL - NOT FOROGTTEN BECAUSE STILL REFERENCE TO IT
		}
	}
	
	public Node find(Object data)
	{
		Node focusNode = this.head; // Sets focusnode to start of list
		
		while(focusNode.data != null) // Loops through the list
		{
			if(focusNode.data == data)
				return focusNode;
			focusNode = focusNode.next;
		}
		return null;
	}
	// Node after - node to be inserted after, before NEW NODE
	public void insertAfter(Node nodeBefore, Node nodeAfter)
	{
		if(nodeBefore == null)
		{
			append(nodeAfter);
			return;
		}
		
		if(this.tail == nodeBefore)
			this.tail = nodeAfter;
		
		nodeAfter.next = nodeBefore.next;
		nodeBefore.next = nodeAfter;
		
	}
	
	public void remove(Node node)
	{
		if(node == this.head) {
			this.head = node.next;
		}
		
		if(node == this.tail)
		{
			this.tail = null;
		}
		
		Node focusNode = this.head;
		Node previousNode = null;
		
		while(focusNode != null)
		{
			if(focusNode == node)
				previousNode.next = node.next;
			
			previousNode = focusNode;
			focusNode = focusNode.next;
		}
	}
	
	public String toString() {
		Node focusNode = this.head;
		String str = "LinkedList: ";
		
		while(focusNode != null)
		{
			str += focusNode.data.toString();
			if(focusNode.next != null)
			{
				str += " -> ";
			}
			focusNode = focusNode.next;
		}
		
		return str;
	}

}
