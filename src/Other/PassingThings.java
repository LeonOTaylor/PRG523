package Other;

import java.util.List;

public class PassingThings {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int myInputNumber = 10;
		byValue(myInputNumber);
		System.out.println(myInputNumber);
		
		RefInt myInputString = new RefInt(10);
		byRef(myInputString);
		System.out.println(myInputString);

	}
	
	static void byValue(int myNumber)
	{
		myNumber = 20;
	}
	
	/*static List<Object[]> experiment()
	{
		
	}*/
	
	static void byRef(RefInt myInputString)
	{
		myInputString.number = 20;
	}
	
	static class RefInt
	{
		public int number;
		
		RefInt(int number) {
			this.number = number;
			}
		
		@Override
		public String toString() {
			return Integer.toString(this.number);
		}
	}

}
