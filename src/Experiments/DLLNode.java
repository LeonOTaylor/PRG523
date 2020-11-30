package Experiments;

public class DLLNode {

	private Object data;
	private DLLNode next;
	private DLLNode previous; 
	
	public DLLNode(Object data)
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
	
	public void append(DLLNode node)
	{
		DLLNode tailNode = this;
		
		while(tailNode.next != null)
		{
			tailNode = tailNode.next;
		}
		node.previous = tailNode;
		tailNode.next = node;
	}
	
	public String toString()
	{
		String str = "Doubly Linked List: ";
		DLLNode focusNode = this;
		
		while(focusNode.previous != null)
			focusNode = focusNode.previous;
		
		while(focusNode != null)
		{
			str += focusNode.data.toString();
			if(focusNode.next != null)
			{
				str += " <=> ";
			}
			focusNode = focusNode.next;
		}
		
		return str;
	}
	
	public static void main(String[] args) {
		DLLNode myList = new DLLNode(1);
		myList.append(new DLLNode(2));
		myList.append(new DLLNode(3));
		myList.append(new DLLNode(4));
		DLLNode myLastNode = new DLLNode(5);
		myList.append(myLastNode);
		
		System.out.println(myList);
		System.out.println(myLastNode);

	}

}
