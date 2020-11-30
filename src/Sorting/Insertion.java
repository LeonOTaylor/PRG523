package Sorting;

import Other.CDRecord;
import Other.CDRecordStorage;

import java.util.*;

public class Insertion {

	/*public static void main(String[] args) {
		List<CDRecord> records = new ArrayList<>();
		
		for(int i=0; i<20; i++)
		{
			int randomBarcode = (int)(Math.random()*100);
			records.add(new CDRecord(randomBarcode));
		}
		
		System.out.println("Before sort: " + records.toString());
		Insertion.sort(records);
		System.out.println("After sort" + records.toString());
	}*/
	
	/*public static void sort(List<CDRecord> records)
	{
		for(int index=1; index < records.size(); index++)
		{
			CDRecord indexRecord = records.get(index);
			int previousIndex = index-1;
			
			while(previousIndex >= 0 && records.get(previousIndex).getBarcode() > indexRecord.getBarcode())
			{
				CDRecord previousRecord = records.get(previousIndex);
				records.set(previousIndex + 1, previousRecord);
				previousIndex--;
			}
			records.set(previousIndex+1, indexRecord);
		}
	}*/
	public static void sort(CDRecordStorage records)
    {
        for (int i = 1; i < records.size(); i++)
        {
        	
        	CDRecord indexRecord = records.get(i);
        	int previousIndex = i - 1;
        	//System.out.println("before comparison: i: " + i + " records.get(i).getAuthor(): " + indexRecord.getAuthor());
        	//System.out.println(" records.get(i-1).getauthor: " + records.get(previousIndex).getAuthor());
        	System.out.println("BEFORE SORT: ");
            for(CDRecord rec : records.getCDList())
            {
         	   System.out.println(rec.getAuthor());
            }
        	while (previousIndex >= 0)
        	{
        		
        		if(indexRecord.getAuthor().compareTo(records.get(previousIndex).getAuthor()) > 0)
        		{
        			break;
        		}
        		CDRecord previousRecord = records.get(previousIndex);
        		records.update(previousIndex + 1, previousRecord);
        		previousIndex--;
           }
           records.update(previousIndex + 1, indexRecord);
           
           System.out.println("AFTER SORT: ");
           for(CDRecord rec : records.getCDList())
           {
        	   System.out.println(rec.getAuthor());
           }
           
        }
    }
	

}
