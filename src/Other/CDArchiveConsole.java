package Other;

import Other.CDRecord;
import Other.CDRecordStorage;
import Other.CDRecordTableModel;
import javax.swing.*;
import javax.swing.table.TableModel;
import Sorting.*;
import Trees.BinaryTree;
import Lists.DoubleLinkedList;
import Lists.DoubleLinkedList.Node;
import org.apache.commons.lang3.RandomStringUtils;
import java.awt.*;
import java.util.HashMap;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import Sockets.Client;
import Sockets.MessageListener;
import Sockets.MessageSender;
import Sockets.Server;
/**
 * Class: CD Archive Console, Developed 2020
 * @author Leon Taylor
 * Purpose: Creates the CD Archive Console GUI and its components, such as the server
 */
public class CDArchiveConsole extends JFrame implements ActionListener {
	JFrame window; // Declaration of the main program window
	
	// Declaration of the various labels used in the programs GUI
	JLabel searchLabel, titleLabel, sortLabel, titleName, dispBinTree, dispHashMap, showMsgLbls, recPanTitleLabel, authorLabel,
	sectionLabel, xLabel, yLabel, barcodeLabel, descriptionLabel, isEmployedLabel, actReqstTitleLabel, sortSectionLbl;
	
	// Declaration of the various text fields used i
	JTextField searchText, titleText, authorText, sectionText, xText, yText, barcodeText, sortSection;
	
	// Declaration of the various buttons used in the GUI
	JButton searchButton, sortByTitleButton, sortByBarcodeButton, sortByAuthorButton, processLogBtn, preOrderBtn, 
	   		postOrderBtn, inOrderBtn, saveBtn, displayBtn, newItemButton, saveUpdateButton, retrieveButton, removeButton, 
	   		returnButton, addToCollectionBtn, rndCollectionBtn, mstlySrtedBtn, rvrsOrderBtn, exitBtn;
	
	// Declaration of the table used to display the programs data
	JTable cdRecordTable;
	
	// Declaration of the various checkboxes used in the GUI
	JCheckBox chkMsgLbls, isEmployedBox;
	
	// Declaration of the various text areas used in the GUI
	JTextArea outputTxtFld, descriptionText;
	
	// Definition and declaration of the CDRecordStorage data structure used to store and create the programs data
	CDRecordStorage records = new CDRecordStorage();
	
	int currentCDRecordIndex; // This variable will be used to keep track of the record currently selected in the table
	boolean newRecordFlag = false; // This is a flag that will let the program know if a new record is being created, or an existing record is being edited
	BinaryTree recordsTree = new BinaryTree(); // Definition and declaration of a Binary Tree data structure
	HashMap<Integer, String> binTreeDatMap = new HashMap<Integer, String>(); // Definition and declaration of a Hashmap data structure
	private Server server; // Declaration of the server object that will allow communication with the automation console
	CDRecordTableModel tableData; // Definition of the TableModel to be used to create and use the table
	DoubleLinkedList processesLLLog = new DoubleLinkedList(); // Definition and declaration of a Doubly linked list 
	   
	// This is the constructor for the program
	public CDArchiveConsole()
	{
		// Various properties and behaviours of the GUI window are defined here
		this.window = new JFrame("Archive Management Console");
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.window.addWindowListener((WindowListener) new WindowListener() {
			@Override
			public void windowOpened(WindowEvent windowEvent) {
				
			}

			@Override
			public void windowClosing(WindowEvent windowEvent) {

			}

			@Override
			public void windowClosed(WindowEvent windowEvent) {

			}

			@Override
			public void windowIconified(WindowEvent windowEvent) {
				
			}

			@Override
			public void windowDeiconified(WindowEvent windowEvent) {

			}

			public void windowActivated(WindowEvent windowEvent) {

			}

			public void windowDeactivated(WindowEvent windowEvent) {

			}
		});
		this.window.getContentPane().setLayout(new GridBagLayout());
	      
		// This is the creation of the server object, that client of the automation console connects to to provide communication
		// between the two GUIs
		server =  new Server(20000, new MessageListener() {
			@Override
			public void message(String mess, MessageSender senda)
			{
				//System.out.println("MESSAGE RECEIVED IN SERVER CLASS(ARCHIVE): " + mess);
				
				// THERE ARE LOGIC ERRORS HERE AND IN THE AUTOMATION CONSOLE!!!
				// Below is a series of loops that perform an action requested from the automation console,
				// since the messages sent from the auto console contain a capitalised word that is the instruction to be carried oit
				
				if(mess.contains("FLIPTHELOANBOOLEAN"))
	    		{
	    			System.out.println("FLIPPIN OUT");
	    			if(records.get(currentCDRecordIndex).isOnLoan() == true)
	    				records.get(currentCDRecordIndex).setOnLoan(false);
	    			else
	    				records.get(currentCDRecordIndex).setOnLoan(true);
	    			
	    		}
				else if(mess.contains("DELETETHECURRENTRECORDPLEASE")) // If the delete instruction is requested, delete the 
				{// NOTE THAT THIS WONT ADD THE WHOLE MESSAGE TO THE LITTLE BOX AT THE BOTTM
					records.delete(currentCDRecordIndex); // Maybe delete by barcode instead of what is selected currently
					//tableData.fireTableDataChanged();
	    		}
	    		else if(mess.contains("SORTTHECDRECORDSPLEASE")) // If a sorting instruction is requested from automation console, bubble sort will be applied
	    		{
	    			BubbleSort.sort(records);
	    		}
	    		else if(mess.contains("ADDTHENEWCDRECORDPLEASE"))
	    		{
	    			processesLLLog.append("New CD Record added from automation console...");
	    		}
	    		else
	    		{
	    			processesLLLog.append(mess); // The messages from the console are added to the doubly linked list
	    		}
				
	    		  
	    	 }
	      });
	       
		CDAutomationConsole autoCons = new CDAutomationConsole(); // After server has been created, we can create an automation console window
		createUI(); // Calls a method to create and configure the GUI components
		archiveListActionListeners(); // Creates the action listeners for the archive list panel
		recordPanelActionListeners(); // Creates the action listeners for the record panel 
		processLogPanelActionListeners(); // Creates the action listeners for the process log panel
		actionRequestActionListeners(); // Creates action listeners for the action request panel
		
		// Sets more properties of the GUI window, such as the size
		this.window.pack();
		this.window.setMinimumSize(new Dimension(720, 720));
		this.window.setSize(new Dimension(720, 720));
		this.window.setVisible(true);
	}
	
/**
 * Purpose: This method creates each of the GUI's panels and adds them to the main window
 */
   private void createUI()
   {
      JPanel archivePanel = createArchiveListPanel();
      addComponent(window.getContentPane(),
              archivePanel,
              GridBagConstraints.BOTH,
              0, 1, 3, 1,
              70, 40);

      JPanel processPanel = createProcessLogPanel();
      addComponent(window.getContentPane(), processPanel, GridBagConstraints.BOTH, 0, 2, 3, 1, 70, 40);

      JPanel recordPanel = createRecordPanel();
      addComponent(window.getContentPane(),
              recordPanel,
              GridBagConstraints.BOTH,
              3, 0, 1, 2,
              30, 40);

      JPanel actionRequestPanel = createActionRequestPanel();
      addComponent(window.getContentPane(),
              actionRequestPanel,
              GridBagConstraints.BOTH,
              3, 2, 1, 1,
              30, 40);
   }

   
   /**
    * Purpose: Adds the Swing components to the panel and places them in the correct location. The table is also configured here, 
    * with the TableModel being created and configured.
    * @return Returns the created panel
    */
   private JPanel createArchiveListPanel()
   {
      JPanel panel = new JPanel();
      //CDRecordStorage arcRecs = new CDRecordStorage();
      SpringLayout layout = new SpringLayout();
      panel.setLayout(new GridBagLayout());
      panel.setBackground(Color.BLUE);

      //JLabel titleLabel = new JLabel("Archive CDs");
      searchLabel = new JLabel("Search String:");
      addComponent(window.getContentPane(),
              searchLabel,
              GridBagConstraints.BOTH,
              0, 0,
              1, 1,
              0, 0);

      searchText = new JTextField();
      addComponent(window.getContentPane(),
              searchText,
              GridBagConstraints.BOTH,
              1, 0,
              1, 1,
              40, 0);

      searchButton = new JButton("Search");
      addComponent(window.getContentPane(),
              searchButton,
              GridBagConstraints.BOTH,
              2, 0,
              1, 1,
              20, 0);

      LibraryComponents.LocateAJLabel(panel, layout, "Archive CDs", 0, 0);
      
      // Table is configured - TableModel created and set to the table
      TableModel tableData = new CDRecordTableModel(records.getCDList()); 
      cdRecordTable = new JTable(tableData); //data, columnNames
      cdRecordTable.setModel(records.getTableModel());
      cdRecordTable.setFillsViewportHeight(true);
      JScrollPane cdRecordTableScrollPane = new JScrollPane(cdRecordTable);
      addComponent(panel, cdRecordTableScrollPane, GridBagConstraints.BOTH, 0, 1, 4, 1, 100, 10); // Yweight was 10

      sortLabel = new JLabel("Sort:");
      addComponent(panel, sortLabel, GridBagConstraints.BOTH, 0, 2, 1, 1, 0, 0);


      sortByTitleButton = new JButton("By Title");
      addComponent(panel, sortByTitleButton, GridBagConstraints.VERTICAL, 1, 2, 1, 1, 0, 0);

      sortByBarcodeButton = new JButton("By Barcode");
      addComponent(panel, sortByBarcodeButton,
    		  GridBagConstraints.VERTICAL, 2, 2, 1, 1, 0, 0, new Insets(0,10,0,0),  GridBagConstraints.WEST);

      sortByAuthorButton= new JButton("By Author");
      addComponent(panel, sortByAuthorButton, GridBagConstraints.VERTICAL, 3, 2, 1, 1, 0, 0);


      return panel;
   }
   
   /**
    * Purpose: This method adds the swing components to the Create Process panel, and 
    * @return Returns the created panel
    */
   private JPanel createProcessLogPanel()
   {
	   // Panel is configured
      JPanel panel = new JPanel();
      panel.setBackground(Color.MAGENTA);
      SpringLayout layoutt = new SpringLayout();
      panel.setLayout(layoutt);
      panel.setVisible(true);
      
      // Components are places
      titleName = LibraryComponents.LocateAJLabel(panel, layoutt, "Process Log:", 250, 0);
      dispBinTree = LibraryComponents.LocateAJLabel(panel, layoutt, "Binary Tree:", 4, 180);
      dispHashMap = LibraryComponents.LocateAJLabel(panel, layoutt, "HashMap/Set", 4, 210);
      preOrderBtn = LibraryComponents.LocateAJButton(panel, this, layoutt, "Pre-Order", 85, 180, 95, 25);
      inOrderBtn = LibraryComponents.LocateAJButton(panel, this, layoutt, "In-Order", 180, 180, 95, 25);
      postOrderBtn = LibraryComponents.LocateAJButton(panel, this, layoutt, "Post-Order", 275, 180, 100, 25);
      processLogBtn = LibraryComponents.LocateAJButton(panel, this, layoutt, "Process Log", 330, 0, 110, 25);
      saveBtn = LibraryComponents.LocateAJButton(panel, this, layoutt, "Save", 85, 210, 95, 25);
      displayBtn = LibraryComponents.LocateAJButton(panel, this, layoutt, "Display", 180, 210, 95, 25);
      outputTxtFld = LibraryComponents.LocateAJTextArea(panel, layoutt, 5, 30, 9, 39); //new JTextArea(10,24);
      chkMsgLbls = new JCheckBox("Show Message Labels");
      panel.add(chkMsgLbls);
      
      return panel;
   }

   /**
    * Purpose: This method creates and configures the Create Record panel, and places Swing components on the panel
    * @return Returns the created panel
    */
   private JPanel createRecordPanel()
   {
	  // Creates the panel, sets layout and color
      JPanel panel = new JPanel();
      panel.setLayout(new GridBagLayout());
      panel.setBackground(Color.GREEN);

      
      // Components are placed on the panel
      recPanTitleLabel= new JLabel("Title:");
      addComponent(panel, recPanTitleLabel,GridBagConstraints.BOTH,0, 0,1, 1,0, 0);

      titleText= new JTextField();
      addComponent(panel,titleText,GridBagConstraints.BOTH,1, 0,2, 1,40, 0);

      authorLabel = new JLabel("Author:");
      addComponent(panel, authorLabel,GridBagConstraints.BOTH, 0, 2, 1, 1,0, 0);

      authorText = new JTextField();
      addComponent(panel,authorText,GridBagConstraints.BOTH,1, 2,2, 1,40, 0);

      sectionLabel = new JLabel("Section:");
      addComponent(panel, sectionLabel,GridBagConstraints.BOTH, 0, 3,1, 1, 0, 0);

      sectionText = new JTextField();
      addComponent(panel,sectionText,GridBagConstraints.BOTH, 1, 3,2, 1,40, 0);

      xLabel= new JLabel("X:");
      addComponent(panel, xLabel,GridBagConstraints.BOTH,0, 4,1, 1,0, 0);

      xText= new JTextField();
      addComponent(panel,xText,GridBagConstraints.BOTH,1, 4,2, 1,40, 0);

      yLabel = new JLabel("Y:");
      addComponent(panel,yLabel,GridBagConstraints.BOTH,0, 5,1, 1,0, 0);

      yText= new JTextField();
      addComponent(panel, yText,GridBagConstraints.BOTH,1, 5,2, 1,40, 0);

      barcodeLabel = new JLabel("Barcode:");
      addComponent(panel, barcodeLabel,GridBagConstraints.BOTH, 0, 6,1, 1,0, 0);

      barcodeText= new JTextField();
      addComponent(panel,barcodeText,GridBagConstraints.BOTH, 1, 6,2, 1, 40, 0);

      descriptionLabel = new JLabel("Description:");
      addComponent(panel, descriptionLabel,GridBagConstraints.BOTH,0, 7,1, 1, 0, 25); //0, 25);
      
      descriptionText = new JTextArea();
      descriptionText.setLineWrap(true);
      JScrollPane painful = new JScrollPane(descriptionText); // This will allow us to scroll if the description can't fit in the text area
      addComponent(panel, painful, GridBagConstraints.BOTH, 1, 7, 2 , 1, 40, 0);
      
      isEmployedLabel = new JLabel("On Loan:");
      addComponent(panel, isEmployedLabel, GridBagConstraints.BOTH, 0, 11, 1, 1, 0, 0);
      
      isEmployedBox = new JCheckBox();
      addComponent(panel, isEmployedBox, GridBagConstraints.BOTH, 1, 11, 1, 1, 0, 0);
      
      newItemButton = new JButton("New Item");
      addComponent(panel, newItemButton, GridBagConstraints.VERTICAL, 0, 12, 1, 1, 0, 0);
      
      saveUpdateButton = new JButton("Save/Update");
      addComponent(panel, saveUpdateButton, GridBagConstraints.VERTICAL, 2, 12, 1, 1, 0, 0);
      
      return panel;
   }

   /**
    * Purpose: This method creates and configures the action request panel, and places Swing components on it
    * @return Returns the created panel
    */
   private JPanel createActionRequestPanel()
   {
	  // Panel is created and configured 
      JPanel panel = new JPanel();
      SpringLayout springLayout = new SpringLayout();
      panel.setLayout(springLayout);
      panel.setBackground(Color.YELLOW);
      panel.setPreferredSize(new Dimension(200, 250)); // Window size is 700 X 700
      
      actReqstTitleLabel = new JLabel("Automation request for the item above:");
      addComponent(panel, actReqstTitleLabel, GridBagConstraints.BOTH, 0,0, 4, 1, 100, 0);
      
      retrieveButton = LibraryComponents.LocateAJButton(panel, this, springLayout, "Retrieve", 10, 25, 85, 25);
      removeButton = LibraryComponents.LocateAJButton(panel, this, springLayout, "Remove", 150, 25, 85, 25);
      returnButton = LibraryComponents.LocateAJButton(panel, this, springLayout, "Return", 10, 60, 85, 25);
      addToCollectionBtn = LibraryComponents.LocateAJButton(panel, this, springLayout, "Add to Collection", 110, 60, 130, 25);
      sortSection = LibraryComponents.LocateAJTextField(panel, springLayout, 5, 75, 90);
      sortSectionLbl = LibraryComponents.LocateAJLabel(panel, springLayout, "Sort Section:" , 0, 90);
      rndCollectionBtn = LibraryComponents.LocateAJButton(panel, this, springLayout, "Random Collection Sort", 10, 120, 200, 25);
      mstlySrtedBtn = LibraryComponents.LocateAJButton(panel, this, springLayout, "Mostly Sorted Sort", 10, 155, 200, 25);
      rvrsOrderBtn = LibraryComponents.LocateAJButton(panel, this, springLayout, "Reverse Order Sort", 10, 185, 200, 25);
      exitBtn = LibraryComponents.LocateAJButton(panel, this, springLayout, "Exit", 140, 220, 100, 25);
      
      return panel;
   }
	   
   /**
    * Purpose: This method fills the record panel with the data from a specific CDRecord item, allowing the user to view the details
    * @param theRecord The CDRecord object we wish to view the details of
    */
   public void setCDRecordPanel(CDRecord theRecord)
   {
	   
	   titleText.setText(theRecord.getTitle());
	   authorText.setText(theRecord.getAuthor());
	   sectionText.setText(theRecord.getSection());
	   xText.setText(Integer.toString(theRecord.getX()));
	   yText.setText(Integer.toString(theRecord.getY()));
	   barcodeText.setText(Integer.toString(theRecord.getBarcode()));
	   descriptionText.setText(theRecord.getDescription());
	   isEmployedBox.setSelected(theRecord.isOnLoan());
   }
	   
   /**
    * Purpose: This method clears the text fields, allowing the details of a new record to be entered
    */
   public void wipeCDRecordPanel()
   {
	   titleText.setText("");
	   authorText.setText("");
	   sectionText.setText("");
	   xText.setText("");
	   yText.setText("");
	   barcodeText.setText("");
	   descriptionText.setText("");
	   isEmployedBox.setText("");
   }
	   
   
   /**
    * Purpose: This method calls the sendMessage method of the Server to send a message to the automation console containing the 
    * command to be executed as well as the data from a particular CDRecord object
    * @param rec The CDRecord object to send to the automation console
    * @param command The command to be executed by the automation console
    */
   public void sendCDRecord(CDRecord rec, String command)
   {
	   server.sendMessage(command + ";" + rec.getTitle() + ";" +
			   rec.getAuthor() + ";" + rec.getSection() + ";" + rec.getX() + ";" + rec.getY() + ";" + rec.getBarcode() + 
			   ";" + rec.getDescription() + ";" + (rec.isOnLoan() ? "Yes" : "No"));
   }
	   
   
   /**
    * Purpose: This method fills the binary tree with the barcodes and titles of the CDRecords
    * @param records The List of CDRecord objects to be inserted into the binary tree
    */
   public void plantTree(List<CDRecord> records)
   {
	   for(CDRecord rec : records)
	   {
		   recordsTree.insert(new BinaryTree.Node(rec.getBarcode(), rec.getTitle()));
	   }
   }
   
   /**
    * Purpose: This method prints the contents of the binary tree to the Archive's display console
    * @param nodes The ArrayList of Binary Tree nodes to be printed 
    */
   public void printTree(ArrayList<BinaryTree.Node> nodes)
   {
	   // For every BinaryTree node in the ArrayList of nodes passed to the method, append it to the Archive consoles text display
	   for(BinaryTree.Node node : nodes)
		   outputTxtFld.append(node.getKey() + "  ->  " + node.getData() + "\n");
   }
	   
   /**
    * This method contains the action listeners for the Process Log panel that allow the user to interact with the program.
    * It also contains methods that fill the HashMap with data, and that display the messages from the client to the text display
    */
   private void processLogPanelActionListeners()
   {
	   // Prints the binary tree records to the display console in pre-order
	   preOrderBtn.addActionListener(new ActionListener() {
		   @Override
		   public void actionPerformed(ActionEvent actionEvent)
		   {
			   outputTxtFld.setText("");
			   outputTxtFld.setText("Binary tree in pre-order... \n");
			   printTree(recordsTree.traversePreOrder()); 
		   }
	   });
	   
	// Prints the binary tree records to the display console in order
	   inOrderBtn.addActionListener(new ActionListener() {
		   @Override
		   public void actionPerformed(ActionEvent actionEvent)
		   {
			   outputTxtFld.setText("");
			   outputTxtFld.setText("Binary tree in in-order... \n"); // MAKE SURE THIS WORKS THE WAY IT IS SUPPOST TO
			   printTree(recordsTree.traverseInOrder());
		   }
	   });

	// Prints the binary tree records to the display console in post-order
	   postOrderBtn.addActionListener(new ActionListener() {
		   @Override
		   public void actionPerformed(ActionEvent actionEvent)
		   {
			   outputTxtFld.setText("");
			   outputTxtFld.setText("Binary tree in post-order... \n");
			   printTree(recordsTree.traversePostOrder());
		   }
	   });
	   
	   // This method fills the HashMap with data from the binary tree 
	   saveBtn.addActionListener(new ActionListener() {
		   @Override
		   public void actionPerformed(ActionEvent actionEvent)
		   {
			   // For each BinaryTree node in the records binary tree sorted in order, place the key and data of each node into the hashmap
			   for(BinaryTree.Node node : recordsTree.traverseInOrder())
			   {
				   binTreeDatMap.put(node.getKey(), node.getData().toString());
			   }
		   }
	   });
	   
	   // This method displays the contents of the linked list containing a log of the messages from the server and client to the text area
	   processLogBtn.addActionListener(new ActionListener() {
		   @Override
		   public void actionPerformed(ActionEvent actionEvent)
		   {
			   // Calls a method in the linked list class returning a string containing contents of the linked list, and displays it on the text area
			   outputTxtFld.append(processesLLLog.toString()); 
		   }
	   });
	   
	   // This method prints the contents of the hashmap to the text field of the Archive console
	   displayBtn.addActionListener(new ActionListener() {
		   @Override
		   public void actionPerformed(ActionEvent actionEvent)
		   {
			   outputTxtFld.setText(""); // First clear the mao
			   outputTxtFld.setText("Hash map: \n");
			   for(Map.Entry<Integer, String> entry : binTreeDatMap.entrySet()) // For each entry in the hashmap
			   {
				   // Print it to the Archive Console text field 
				   outputTxtFld.append(entry.getKey().toString() + "   --->   " + entry.getValue() + "\n");
			   }
		   }
	   });
	   
	   
   }
	   
   /**
    * This method contains the methods of the Action Request panel, and contains methods that save data to the datafile, as well
    * as statements that send commands to the automation console
    */
   private void actionRequestActionListeners()
   {
	   
	   // This method saves the CDRecord data to the datafile before closing the program
	   exitBtn.addActionListener(new ActionListener() {
		   @Override
		   public void actionPerformed(ActionEvent actionEvent)
		   {
			   CDRecordStorage.saveCDRecordList("Datafile", records.getCDList());
			   System.exit(0);
		   }
	   });
	   
	   // This method sends a command to the automation console to sort the CD records in the section specified in the section text 
	   // field in reverse order
	   rvrsOrderBtn.addActionListener(new ActionListener() {
		   @Override
		   public void actionPerformed(ActionEvent actionEvent)
		   {
			   CDRecord reqRec = records.get(currentCDRecordIndex); // Fetches the CD record currently selected in the table
			   if(reqRec == null)
				   return; // Returns from the method if the CD record is non-existent
			   sendCDRecord(reqRec, "sort:" + sortSection.getText());
			   // Appends the message to the processes linked list log
			   processesLLLog.append("SORT: REVERSE ORDER SORT - " + Integer.toString(reqRec.getBarcode()));
		   }
	   });
	   
	   // This method sends a command to the automation console to sort the CD records in the section specified in the section text 
	   // field in random order
	   rndCollectionBtn.addActionListener(new ActionListener() {
		   @Override
		   public void actionPerformed(ActionEvent actionEvent)
		   {
			   CDRecord reqRec = records.get(currentCDRecordIndex); // Fetches the CD record currently selected in the table
			   if(reqRec == null)
				   return; // Returns from the method if the CD record is non-existent
			   sendCDRecord(reqRec, "sort:" + sortSection.getText());
			   // Appends the message to the processes linked list log
			   processesLLLog.append("SORT: RANDOM COLLECTION SORT - " + Integer.toString(reqRec.getBarcode()));
		   }
	   });
	   
	   // This method sends a command to the automation console to sort the CD records in the section specified in the section text 
	   // field in a mostly sorted order
	   mstlySrtedBtn.addActionListener(new ActionListener() {
		   @Override
		   public void actionPerformed(ActionEvent actionEvent)
		   {
			   CDRecord reqRec = records.get(currentCDRecordIndex); // Fetches the CD record currently selected in the table
			   if(reqRec == null)
				   return; // Returns from the method if the CD record is non-existent
			   sendCDRecord(reqRec, "sort:" + sortSection.getText());
			   // Appends the message to the processes linked list log
			   processesLLLog.append("SORT: MOSTLY SORTED SORT - " + Integer.toString(reqRec.getBarcode()));
		   }
	   });
	   
	   // This method sends a command to the automation to retreive a particlar CD
	   retrieveButton.addActionListener(new ActionListener() {
		   @Override
		   public void actionPerformed(ActionEvent actionEvent)
		   {
			   CDRecord reqRec = records.get(currentCDRecordIndex); // Fetches the CD record currently selected in the table
			   if(reqRec == null)
				   return; // Returns from the method if the CD record is non-existent
			   
			   // Appends the message to the processes linked list log
			   processesLLLog.append("SENT: Retrieve Item - " + Integer.toString(reqRec.getBarcode()) + "\n");
			   sendCDRecord(reqRec, "retrieve"); // Sends the CD record and command to the automation console
		   }
	   });
	   
	   // This method sends a command to the automation console to remove a particular CD
	   removeButton.addActionListener(new ActionListener() {
		   @Override
		   public void actionPerformed(ActionEvent actionEvent)
		   {
			   CDRecord reqRec = records.get(currentCDRecordIndex); // Fetches the CD record currently selected in the table
			   System.out.println("CURRENT THING: " + reqRec.getBarcode());
			   if(reqRec == null)
				   return; // Returns from the method if the CD record is non-existent
			   // Appends the message to the processes linked list log
			   processesLLLog.append("SENT: Remove Item - " + Integer.toString(reqRec.getBarcode()) + "\n");
			   sendCDRecord(reqRec, "remove"); // Sends the CD record and command to the automation console
		   }
	   });
	   
	   // Appends the message to the processes linked list log
	   returnButton.addActionListener(new ActionListener() {
		   @Override
		   public void actionPerformed(ActionEvent actionEvent)
		   {
			   CDRecord reqRec = records.get(currentCDRecordIndex); // Fetches the CD record currently selected in the table
			   System.out.println("CURRENT THING: " + reqRec.getBarcode());
			   if(reqRec == null)
				   return; // Returns from the method if the CD record is non-existent
			   // Appends the message to the processes linked list log
			   processesLLLog.append("SENT: Return Item - " + Integer.toString(reqRec.getBarcode()) + "\n");
			   sendCDRecord(reqRec, "return"); // Sends the CD record and command to the automation console
		   }
	   });
	   
	   // Appends the message to the processes linked list log
	   addToCollectionBtn.addActionListener(new ActionListener() {
		   @Override
		   public void actionPerformed(ActionEvent actionEvent)
		   {
			   CDRecord reqRec = records.get(currentCDRecordIndex); // Fetches the CD record currently selected in the table
			  // System.out.println("ADDING TING!");
			   if(reqRec == null)
				   return; // Returns from the method if the CD record is non-existent
			   // Appends the message to the processes linked list log
			   processesLLLog.append("BLANK CD BEING HANDED TO ROBOTIC ARM\n");
			   sendCDRecord(reqRec, "add"); // Sends the CD record and command to the automation console
		   }
	   });
   }
	   
   /**
    * Purpose: This method implements the action listeners for the Record Panel
    */
   private void recordPanelActionListeners()
   {
	   // This method clears the data in the record panels text areas by calling the wipeCDRecordPanel method
	   newItemButton.addActionListener(new ActionListener() {
		   @Override
		   public void actionPerformed(ActionEvent actionEvent)
		   {
			   wipeCDRecordPanel(); // Text areas are wiped
			   // A new barcode is randomly generated and assigned using the Apache Commons library, and placed in the barcode textfield
			   barcodeText.setText(RandomStringUtils.randomNumeric(6)); 
			   newRecordFlag = true; // The new record flag (used in the next method) is set to true to indicate a new record is being created
		   }
	   });
	   
	   // This method saves a new record and adds it to the dataset, or it updates the details of an existing CD record
	   saveUpdateButton.addActionListener(new ActionListener() {
		   @Override
		   public void actionPerformed(ActionEvent actionEvent)
		   {
			   // If any details are left out, the new record wont be created/updated, the method will terminate 
			   if(titleText.getText().isEmpty() || authorText.getText().isEmpty() ||
					   sectionText.getText().isEmpty() || xText.getText().isEmpty() ||
					   yText.getText().isEmpty() || barcodeText.getText().isEmpty() ||
					   descriptionText.getText().isEmpty())
				   return;
			   
			   // Creates a new CD record with the updated data
			   CDRecord newRec = new CDRecord(titleText.getText(), authorText.getText(), sectionText.getText(),
					   Integer.parseInt(xText.getText()), Integer.parseInt(yText.getText()), Integer.parseInt(barcodeText.getText()), 
					   descriptionText.getText(), isEmployedBox.isSelected());
			   
			   // If the newrecord flag is true, the new record is added to the CDRecordStorageModel, or an existing record is updated
			   // using the data from the CDRecord created above
			   if(!newRecordFlag && currentCDRecordIndex != -1)
				   records.update(currentCDRecordIndex, newRec);
			   else
				   records.create(newRec);
		   }
	   });
   }

   /**
    * Purpose: this method implements the action listeners for the archive list panel
    */
   private void archiveListActionListeners()
   {
	   // This method loops through the CD Records to find a record with a title that matches the text in the searchText text field
	   searchButton.addActionListener(new ActionListener() {
		   @Override
		   public void actionPerformed(ActionEvent actionEvent)
		   {
			   CDRecord record;
			   // Loop through the records
			   for(int i=0; i<records.size(); i++)
			   {
				   record = records.get(i);
				   // If a record has a title that matches the search critera, highlight and display the CDRecord in the record panel
				   if(record.getTitle().equalsIgnoreCase(searchText.getText()))
				   {
					   currentCDRecordIndex = -1;
					   cdRecordTable.clearSelection();
					   cdRecordTable.addRowSelectionInterval(i, i);
					   setCDRecordPanel(record);
					   break;
				   }
			   }
		   }
	   });
	   // This implements an action listener for the mouse that allows the table to be used with a mouse
	   cdRecordTable.addMouseListener(new MouseListener() {
		   @Override
		   public void mouseClicked(MouseEvent mEvent)
		   {
			   currentCDRecordIndex = cdRecordTable.getSelectedRow();
			   CDRecord toSet = records.get(currentCDRecordIndex);
			   setCDRecordPanel(toSet);
		   }

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	   });
	   
	   // This method uses the bubble sort algorithm to sort data in the table by the Barcodes
	   sortByBarcodeButton.addActionListener(new ActionListener() {
		   @Override
		   public void actionPerformed(ActionEvent actionEvent)
		   {
			   BubbleSort.sort(records);
		   }
	   });
	   
	   // This method uses the insertation sort algorithm to sort data in the table by the Authors
	   sortByAuthorButton.addActionListener(new ActionListener() {
		   @Override
		   public void actionPerformed(ActionEvent actionEvent)
		   {
			   Insertion.sort(records);
		   }
	   });
	   
	   // This method uses the selection sort algorithm to sort the CD records by the title, and it also fills the
	   // binary tree with data
	   sortByTitleButton.addActionListener(new ActionListener() {
		   @Override
		   public void actionPerformed(ActionEvent actionEvent)
		   {
			   Selection.sort(records);
			   plantTree(records.getCDList());
		   }
	   });
	   
   }
	   
   /**
    * Purpose: this method allows Swing components to be easily placed using the GridBagLayout
    * @param <C> The Swing Component to add
    * @param contentPane The JPanel to add the component on
    * @param component The swing component to add
    * @param fill  The GridBag constraints
    * @param gridX The X coordinate of the component
    * @param gridY THe Y coordinate of the component
    * @param gridWidth // The width of the component
    * @param gridHeight // The height of the component
    * @param weightX // How much extra horizontal space should be distributed
    * @param weightY // How much extra vertical space should be distributed
    */
   private <C extends Component> void addComponent(
           Container contentPane,
           C component,
           int fill, int gridX, int gridY,
           int gridWidth, int gridHeight,
           float weightX, float weightY
   )
   {
      GridBagConstraints constraints = new GridBagConstraints();
      constraints.fill = fill;
      constraints.gridx = gridX;
      constraints.gridy = gridY;
      constraints.gridwidth = gridWidth;
      constraints.gridheight = gridHeight;
      constraints.weightx = weightX;
      constraints.weighty = weightY;

      contentPane.add(component, constraints);
   }

   /**
    * Purpose: this method allows Swing components to be easily placed using the GridBagLayout
    * @param <C> The Swing Component to add
    * @param contentPane The JPanel to add the component on
    * @param component The swing component to add
    * @param fill  The GridBag constraints
    * @param gridX The X coordinate of the component
    * @param gridY THe Y coordinate of the component
    * @param gridWidth // The width of the component
    * @param gridHeight // The height of the component
    * @param weightX // How much extra horizontal space should be distributed
    * @param weightY // How much extra vertical space should be distributed
    * @param insets // Specifies the minimum amount of space (padding) between components
    * @param anchor // Where the component should be placed if it can't fit in the panel/window
    */
   private <C extends Component> void addComponent(
           Container contentPane,
           C component,
           int fill, int gridX, int gridY,
           int gridWidth, int gridHeight,
           float weightX, float weightY,
           Insets insets, int anchor
   )
   {
      GridBagConstraints constraints = new GridBagConstraints();
      constraints.fill = fill;
      constraints.gridx = gridX;
      constraints.gridy = gridY;
      constraints.gridwidth = gridWidth;
      constraints.gridheight = gridHeight;
      constraints.weightx = weightX;
      constraints.weighty = weightY;
      constraints.insets = insets;
      constraints.anchor = anchor;

      contentPane.add(component, constraints);
   }

   // This is the main method that runs the program
   public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            new CDArchiveConsole();
         }
      });
   }
	   
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}
	}