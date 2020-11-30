package Other;
import java.io.*;
import java.util.*;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CDRecordStorage {

	ArrayList<CDRecord> records;
	CDRecordTableModel tableModel;
	
	public CDRecordStorage() // Is this the only constructor that will be needed
	{
		//records = loadCDRecordList("C:\\Users\\leont\\Dropbox\\Code\\PRG523PROJECT\\Datafile"); 
		records =  loadCDRecordList("Datafile");
		tableModel = new CDRecordTableModel(records);
	}
	
	// WAS LIST
	public static ArrayList<CDRecord> loadCDRecordList(String fileName)
	{
		ArrayList<CDRecord> recordz = new ArrayList<>(); // Maybe using arraylist is causing problems????
		//List<Object[]> records = new ArrayList<>(); 
		try
		{
			FileReader reader = new FileReader(fileName);
			BufferedReader bReader = new BufferedReader(reader);
			System.out.println("READING!");
			String line;
			int lineCount = 0;
			boolean isEmployedBool;
			while((line = bReader.readLine()) != null)
			{
				//boolean isEmployedBool = false; // does this belong here???
				//isEmployedBool = false;
				++lineCount;
				//System.out.println("LINECOUNT: " + lineCount);
				//Object[] dataa = new Object[8]; //I'm thinking this will be recreated each time the file is read, and we can fill it and and it to the list
				String[] dataColumns = line.split(";");
				//System.out.println("DATACOULUM *::::: " + dataColumns[2]);
				if(dataColumns[8].equalsIgnoreCase("Yes")  == true)
					isEmployedBool = true;
				else 
					isEmployedBool = false; // Maybe do a el
				
				CDRecord record = new CDRecord(
						dataColumns[1],
						dataColumns[2],
						dataColumns[3],
						Integer.parseInt(dataColumns[4]),
						Integer.parseInt(dataColumns[5]),
						Integer.parseInt(dataColumns[6]),
						dataColumns[7],
						isEmployedBool);
				
				recordz.add(record);
				/*System.out.println("Author: " + record.author);
				System.out.println("Barcode: " + record.barcode);
				System.out.println("Title: " + record.title);
				System.out.println("Description: " + record.description);
				System.out.println("Section: " + record.section); //System.out.println("dataColumns[4]: " + Integer.parseInt(dataColumns[4]));
				System.out.println("X: " + record.x);
				System.out.println("Y: " + record.y);
				System.out.println("On loan: " + record.onLoan);
				
				System.out.println("###################################");*/
			}
			//for(int i=0; i<dataColumns.)
		} catch (Exception e)
		{
			System.out.println("Failed to lead file: " + e.getMessage());
		}
		
		return recordz;
	}
	
	public void create(CDRecord record)
	{
		records.add(record);
		tableModel.fireTableDataChanged();
	}
	
	public void update(int idx, CDRecord record)
	{
		records.set(idx, record);
		tableModel.fireTableDataChanged();
	}
	
	public void delete(int idx)
	{
		records.remove(idx);
		tableModel.fireTableDataChanged();
	}
	
	public CDRecord get(int idx)
	{
		return this.records.get(idx);
	}
	
	public int size()
	{
		return records.size();
	}

	public ArrayList<CDRecord> getCDList()
	{
		return records;
	}
	
	public static void saveCDRecordList(String fileName, List<CDRecord> records)
	{
		try
		{
			FileWriter fw = new FileWriter(fileName);
			BufferedWriter bw = new BufferedWriter(fw);
			CDRecord record;
			
			for(int i=0; i<records.size(); i++)
			{
				record = records.get(i);
				bw.write(i+1 + ";" + record.getTitle() + ";" + record.getAuthor() + ";" + record.getSection() + ";" + record.getX()
				+ ";" + record.getY() + ";" + record.getBarcode() + ";" + record.getDescription() + ";" + (record.isOnLoan() ? "Yes" : "No"));
				bw.newLine();
			}
			bw.close();
		}catch (Exception e)
		{
			System.out.println("COULDN'T SAVE CD RECORD LIST TO FILE");
		}
	}
	
	public CDRecordTableModel getTableModel()
	{
		return tableModel;
	}
	
	public void updateTable()
	{
		tableModel.fireTableDataChanged();
	}
	
	// Think some methods such as Write CSV and getTableModel are missing
}
