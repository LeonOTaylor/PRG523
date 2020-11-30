package Other;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class CDRecordTableModel extends AbstractTableModel {
	
	List<CDRecord> records = CDRecordStorage.loadCDRecordList("Datafile");
	public static String[] columnNames = new String[]{"Title", "Author", "Section", "X", "Y", "Barcode", "Description", "On Loan"};
	
	//public Object[][] data = 
	public CDRecordTableModel(List<CDRecord> recordLst)
	{
		records = recordLst;
		//columnNames 
	}

	@Override
	public String getColumnName(int col)
	{
		return columnNames[col];
	}
	
	
	@Override
	public int getRowCount()
	{
		
		return records.size();
	}
	
	@Override
	public int getColumnCount()
	{
		// number of columsn - could maybe be fixed
		return columnNames.length;
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		//Object[] record = this.records.get(rowIndex); // Make sure this works!!
		CDRecord record = this.records.get(rowIndex);
		
		switch(columnIndex)
		{
			case 0: return record.getTitle();
			case 1: return record.getAuthor();
			case 2: return record.getSection();
			case 3: return record.getX();
			case 4: return record.getY();
			case 5: return record.getBarcode();
			case 6: return record.getDescription();
			case 7: return record.isOnLoan();
		}
		return null;
	}
}
