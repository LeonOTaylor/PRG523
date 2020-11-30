package Sorting;

import Other.CDRecord;
import Other.CDRecordStorage;

import java.util.*;

public class Selection {			
	
	public static void sort(CDRecordStorage records)
	{
		for(int index=0; index < records.size() - 1; index++)
		{
			int smallest_index = index;
			for(int current_index = index+1; current_index < records.size(); current_index++)
			{
				//if(records.get(current_index).getBarcode() < records.get(smallest_index).getBarcode())
				if(records.get(current_index).getTitle().compareTo(records.get(smallest_index).getTitle()) < 0)
					smallest_index = current_index;
			}
			// Swap 
			CDRecord smallest_record = records.get(smallest_index);
			CDRecord index_record = records.get(index);
			records.update(index, smallest_record);
			records.update(smallest_index, index_record);
		}
		
	}

}
