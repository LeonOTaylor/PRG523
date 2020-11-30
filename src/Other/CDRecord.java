package Other;

import java.util.List;

public class CDRecord
{
	String title;
	String author;
	String section;
	String description;
	
	int x, y;
	int barcode;
	boolean onLoan;
	
	// Pretty crap constructor, don't know what I can use it for just nullin 
	public CDRecord(int barry)
	{
		this.title = null;
		this.author = null;
		this.section = null;
		this.description = null;
		this.x = 0; this.y = 0;
		this.barcode = barry;
		this.onLoan = false;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isOnLoan() {
		return onLoan;
	}

	public void setOnLoan(boolean onLoan) {
		this.onLoan = onLoan;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getSection() {
		return section;
	}

	public CDRecord(String title, String author, String section, int x, int y, int barcode, String description, boolean onLoan) {
		this.title = title;
		this.author = author;
		this.section = section;
		this.x = x;
		this.y = y;
		this.barcode = barcode;
		this.description = description;
		this.onLoan = onLoan;
	}
	
	@Override
	public String toString()
	{
		return Integer.toString(this.barcode);
	}
	
	public static List<CDRecord> getTestData()
	{
		CDRecord[] records = new CDRecord[] {
				new CDRecord("Foo", "Bar", "A", 0, 0, 1001, "Album", false),
				new CDRecord("Soo", "Far", "B", 1, 2, 1241, "Album", false),
				new CDRecord("Aoo", "Qar", "C", 2, 4, 1741, "Album", false),
				new CDRecord("Goo", "Car", "D", 3, 6, 1253, "Album", false),
				new CDRecord("Eoo", "Zar", "E", 4, 8, 1341, "Album", false),
				new CDRecord("Boo", "Par", "F", 5, 9, 1641, "Album", false),
				new CDRecord("Woo", "Dar", "G", 6, 1, 1321, "Album", false),
		};
		
		return List.of(records);
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public void setAuthor(String author)
	{
		this.author = author;
	}
	
	public void setSection (String sect)
	{
		this.section = sect;
	}
	
	public void giveDescription(String descr)
	{
		this.description = descr;
	}
	
	public void setCoords(int xcoord, int ycoord)
	{
		this.x = xcoord;
		this.y = ycoord;
	}
	
	public void setBarcode(int bars)
	{
		this.barcode = bars;
	}
	
	public int getBarcode()
	{
		return this.barcode;
	}
	
	public void loan(boolean isIt)
	{
		this.onLoan = isIt;
	}
	
	/*public List<String> recToStringList()
	{
		
	}*/
	// add tostring
}