package Experiments;

public class LLNode {
	
	public static void main(String[] args) {
		LLNode myNode = new LLNode("This");
		myNode.append(new LLNode("Is"));
		myNode.append(new LLNode("a"));
		myNode.append(new LLNode("Linked"));
		myNode.append(new LLNode("List"));
		
		//LLNode foundNode = myNode.find("Linked");
		//foundNode.setData("not a doubly linked");
		
		System.out.println(myNode);
	}
	
	private Object data; // Anything can be stored in an Object!
	private LLNode next; // Is null until it is connected to other links
	
	public LLNode(Object data)
	{
		this.data = data;
	}
	
	public void setData(Object data)
	{
		this.data = data;
	}
	
	public Object getData()
	{
		return this.data;
	}
	
	// How is the first node found?
	public void append(LLNode node)
	{
		//this.next = node;
		LLNode tailNode = this;
		// If the next node is null, append to it - it will be the tail node otherewise move to next node
		while(tailNode.next != null) // Does the current node have a link to the next node
		{
			//Set the next node to be checked next
			//System.out.println("ARE WE SHUFFLING?" + tailNode.data);
			tailNode = tailNode.next;
		}
		//System.out.println("DATA IS " + tailNode.data);
		//System.out.println();
		
		tailNode.next = node;
	}
	
	public LLNode find(Object searchNodeData)
	{
		LLNode focusNode = this; // this is the node that we call find on! - what we start on?????
		
		while(focusNode != null)
		{
			if(focusNode.data == searchNodeData)
				return focusNode;
			else
				focusNode = focusNode.next;
		}
		
		return null;
	}
	
	public String toString()
	{
		
		String str = "Linked List: ";
		
		LLNode focusNode = this;
		
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
