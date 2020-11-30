package Other;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;
import java.util.*;
import Sockets.Server;
import Sockets.Client;
import Sockets.MessageListener;
import Sockets.MessageSender;
import javax.swing.*;

import Sorting.BubbleSort;
import Sorting.Selection;
/**
 * Class: CD Automation Console, developed 2020
 * @author Leon Taylor
 * Purpose: Creates the CD Automation console, and its components such as the Client
 */
public class CDAutomationConsole extends JFrame implements ActionListener {
	// Declaration of Swing components such as the window, panel, buttons, labels, textfields and the layout
	private JFrame window;
	private JPanel panel; // For simplicities sayk
	private JLabel currentRequestAction, selectedItemBarcode, sectionLbl;
	private JButton processBtn, addItemBtn, exitBtn;
	private JTextField itemBarcode, sectionTxt, requestedAction;
	CDRecordStorage records = new CDRecordStorage(); 
	private SpringLayout layout = new SpringLayout();
	private Client client; // This Client object will be used to communicate with the Server object in the Archive console
	
	// Various Objects and variables used in the program are also defined
	CDRecord rec;
	String[] dataCols;
	String[] sortSek;
	boolean loaned;
	
	// Main constructor
	public CDAutomationConsole()
	{
		// Various properties and behaviours of the GUI are given here
		this.window = new JFrame("Automation Console");
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
		this.panel = new JPanel();
		this.panel.setLayout(layout);
		
		
		// This is the creation of the client object, that receives messages from the Server object of the Archive console. 
		// This method contains the message processing logic that appropriately sets values to the programs text area.
		client = new Client("localhost:20000", new MessageListener() {
			@Override
			public void message(String msg, MessageSender sender)
			{
				System.out.println("MESSAGE RECEIVED IN CLIENT CLASS (AUTO): " + msg);
				dataCols = msg.split(";"); // The message is spilt and used to create a new CDRecord object containing the fragments of the message
				// Special treatment for the boolean value - strings are used as is and integers are extracted using parseInt
				loaned = dataCols[8].equalsIgnoreCase("Yes");
				System.out.println("DATACOLS SIZE: " + dataCols[0]);
				rec = new CDRecord(
						dataCols[1], dataCols[2], dataCols[3],
						Integer.parseInt(dataCols[4]), Integer.parseInt(dataCols[5]), Integer.parseInt(dataCols[6]),
						dataCols[7], loaned); // Change this

				if(!dataCols[3].contains("null"))
					sectionTxt.setText(dataCols[3]); // MAKE SURE THE CORRECT THING IS BEING SENT
				if(!dataCols[6].contains("null"))
					itemBarcode.setText(dataCols[6]);
				
				// Set the requested action text area to display the command that was requested
				if(dataCols[0].contains("retrieve"))
				{
					requestedAction.setText("Retrieve");
				}
				else if(dataCols[0].contains("return"))
				{
					requestedAction.setText("Return");
				}
				else if(dataCols[0].contains("remove"))
				{
					requestedAction.setText("Removed");
				}
				else if(dataCols[0].contains("add"))
				{
					requestedAction.setText("Add");
				}
				else if(dataCols[0].contains("sort"))
				{
					// sortSek is used to spilt a the message since a sort message also contains the section to be sorted
					sortSek = dataCols[0].split(":");
					requestedAction.setText("Sort");
					sectionTxt.setText(sortSek[1]);
				}
			}
		});
		
		createUI(); // This method places the GUI components on the window
		actionnListeners(); // This method implements the action listeners of the programs GUI components
		
		this.window.pack();
		this.window.setMinimumSize(new Dimension(500, 220));
	    this.window.setSize(new Dimension(500, 220));
	    this.window.add(panel); 
	    this.window.setVisible(true);
	}
	
	/*
	 * Purpose: This method contains the action listeners for the program, and also sends messages back to the Archive Console server
	 */
	public void actionnListeners()
	{
		// This is the implementation for the process button, and is also responsible for sending messages to the archive console server 
		processBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent)
			   {
				//System.out.println("dataCols[0]: " + dataCols[0]);
				// Details about the status of the requested commands will be sent back to the server
				if(dataCols[0].contains("retrieve"))
				{
					client.sendMessage("Retreived: - item found: " + rec.getBarcode());
					
					if(rec.isOnLoan() == true)
					{
						client.sendMessage("The CD: " + rec.getBarcode() + " is already on loan...");
					}
					else if(rec.isOnLoan() == false)
					{
						client.sendMessage("The CD: " + rec.getBarcode() + " is now loaned out...");
						client.sendMessage("FLIPTHELOANBOOLEAN");
					}
				}
				else if(dataCols[0].contains("return"))
				{
					client.sendMessage("Returned - item returned: " + rec.getBarcode());
					if(rec.isOnLoan() == true)
					{
						client.sendMessage("The CD: " + rec.getBarcode() + " has now been returned");
						client.sendMessage("FLIPTHELOANBOOLEAN");
					}
					else if(rec.isOnLoan() == false)
					{
						client.sendMessage("The CD: " + rec.getBarcode() + " hasn't been loaned out...");
					}
				}
				else if(dataCols[0].contains("remove"))
				{
					client.sendMessage("DELETE - item removed: " + rec.getBarcode());
					client.sendMessage("DELETETHECURRENTRECORDPLEASE");
				}
				else if(dataCols[0].contains("add"))
				{
					client.sendMessage("ADDED - item added: " + rec.getBarcode());
					client.sendMessage("ADDTHENEWCDRECORDPLEASE");
				}
				else if(dataCols[0].contains("sort"))
				{
					client.sendMessage("SORTED: item sorted: " + rec.getBarcode());
					client.sendMessage("SORTTHECDRECORDSPLEASE");
				}
			   }
		});
	}
	
	public static void main(String[] args)
	{
		// ADD SOME SHIT HERE
		SwingUtilities.invokeLater(new Runnable() {
	         @Override
	         public void run() {
	            new CDAutomationConsole();
	            
	         }
	      });
	}
	
	/**
	 * This method is responsible for placing the Swing components on the GUI window
	 */
	public void createUI()
	{
		// All the button code goes here
		currentRequestAction = LibraryComponents.LocateAJLabel(panel, layout, "Current Requested Action:", 0, 30);
		//selectBox = LibraryComponents.LocateAJComboBox(panel, this, layout, actions, 175, 25, 100, 25);
		processBtn = LibraryComponents.LocateAJButton(panel, this, layout, "Process", 360, 25, 85, 25);
		selectedItemBarcode = LibraryComponents.LocateAJLabel(panel, layout, "Barcode of Selected Item:", 0, 80);
		itemBarcode = LibraryComponents.LocateAJTextField(panel, layout, 10, 155, 80);
		sectionLbl = LibraryComponents.LocateAJLabel(panel, layout, "Section:", 280, 80);
		sectionTxt = LibraryComponents.LocateAJTextField(panel, layout, 2, 330, 80);
		requestedAction = LibraryComponents.LocateAJTextField(panel, layout, 10, 175, 25);
		addItemBtn = LibraryComponents.LocateAJButton(panel, this, layout, "Add Item", 360, 80, 85, 25);
		//selectBox.setBounds(100, 25, 100, 25);
		//panel.add(selectBox);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}