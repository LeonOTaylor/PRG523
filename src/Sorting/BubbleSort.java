package Sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Other.CDRecord;
import Other.CDRecordStorage;

public class BubbleSort {

	public static void sort(CDRecordStorage records) // NON STATIC - DON'T NEED AN INSTANCE (OF AN OBJECT) OF A CLASS
	{
		boolean swapped = true;
		
		// The list is sorted once it can be traversed without swapping any values
		while(swapped)
		{
			swapped = false;
			for(int i=1; i < records.size(); i++)
			{
				//int leftBarcode = records.get(i-1).getBarcode();
				CDRecord left = records.get(i-1);
				CDRecord right = records.get(i);
				//int S = left.getAuthor().compareTo(right.getAuthor());
				if(left.getBarcode() > right.getBarcode()) //if()
				{
					records.update(i, left);
					records.update(i-1, right);
					swapped = true;
				}
			}
		}
	}
	
	public void reverseSort(CDRecordStorage records, String section)
	{
		boolean swapped = true;
		
		// The list is sorted once it can be traversed without swapping any values
		while(swapped)
		{
			swapped = false;
			for(int i=1; i < records.size(); i++)
			{
				//int leftBarcode = records.get(i-1).getBarcode();
				CDRecord left = records.get(i-1);
				CDRecord right = records.get(i);
				//int S = left.getAuthor().compareTo(right.getAuthor());
				// TEST IF BELOW WORKS!!! -- NOT EVEN SORTING IN REVERSE ORDER
				if(left.getBarcode() > right.getBarcode() && (left.getSection().contains(section) && right.getSection().contains(section))) //if()
				{
					records.update(i, left);
					records.update(i-1, right);
					swapped = true;
				}
			}
		}
	}

}
