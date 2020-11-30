package Other;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.Dimension;



public class LibraryComponents
{
    
    /** --------------------------------------------------------
    * Purpose: Locate a single JLabel within the JFrame.
    * @param   myJPanel, myJLabelLayout, JLabelCaption, Width, x, y
    * @returns The JLabel.
    * ----------------------------------------------------------
    */
    public static JLabel LocateAJLabel(JPanel myJPanel, SpringLayout myJLabelLayout, String JLabelCaption, int x, int y)
    {
	// Declare and Instantiate the JLabel
        JLabel myJLabel = new JLabel(JLabelCaption);
	// Add the JLabel to the screen
        //myJFrame.add(myJLabel);
        myJPanel.add(myJLabel);
	// Set the position of the JLabel (From left hand side of the JFrame (West), and from top of JFrame (North))
        myJLabelLayout.putConstraint(SpringLayout.WEST, myJLabel, x, SpringLayout.WEST, myJPanel);
        myJLabelLayout.putConstraint(SpringLayout.NORTH, myJLabel, y, SpringLayout.NORTH, myJPanel);
	// Return the label to the calling method
        return myJLabel;
    }
   
        
    /** --------------------------------------------------------
    * Purpose: Locate a single JTextField within the JFrame.
    * @param   myJPanel, myJTextFieldLayout, width, x, y
    * @returns The JTextField.
    * ----------------------------------------------------------
    */
    public static JTextField LocateAJTextField(JPanel myJPanel, SpringLayout myJTextFieldLayout, int width, int x, int y)
    {
        JTextField myJTextField = new JTextField(width);
        //myJFrame.add(myJTextField);
        myJPanel.add(myJTextField);
        myJTextFieldLayout.putConstraint(SpringLayout.WEST, myJTextField, x, SpringLayout.WEST, myJPanel);
        myJTextFieldLayout.putConstraint(SpringLayout.NORTH, myJTextField, y, SpringLayout.NORTH, myJPanel);
        return myJTextField;
    }

        
    /** --------------------------------------------------------
    * Purpose: Locate a single JButton within the JFrame.
    * @param   myJPanel, myActnLstnr, myJButtonLayout, JButtonCaption, x, y, w, h
    * @returns The JButton.
    * ----------------------------------------------------------
    */
    public static JButton LocateAJButton(JPanel myJPanel, ActionListener myActnLstnr, SpringLayout myJButtonLayout, String  JButtonCaption, int x, int y, int w, int h)
    {    
        JButton myJButton = new JButton(JButtonCaption);
        //myJFrame.add(myJButton);
        myJPanel.add(myJButton);
        myJButton.addActionListener(myActnLstnr);
        myJButtonLayout.putConstraint(SpringLayout.WEST, myJButton, x, SpringLayout.WEST, myJPanel);
        myJButtonLayout.putConstraint(SpringLayout.NORTH, myJButton, y, SpringLayout.NORTH, myJPanel);
        myJButton.setPreferredSize(new Dimension(w,h));
        return myJButton;
    }

    public static JComboBox LocateAJComboBox(JPanel myJPanel, ActionListener myActnLstnr, SpringLayout myJComboBoxLayout, String[] boxData, int x, int y, int w, int h)
    {
    	JComboBox myBox = new JComboBox(boxData);
    	myJPanel.add(myBox);
    	myBox.addActionListener(myActnLstnr);
    	myJComboBoxLayout.putConstraint(SpringLayout.WEST, myBox, x, SpringLayout.WEST, myJPanel);
    	myJComboBoxLayout.putConstraint(SpringLayout.NORTH, myBox, y, SpringLayout.NORTH, myJPanel);
    	return myBox;
    }
    /** --------------------------------------------------------
    * Purpose: Locate a single JTextArea within the JFrame.
    * @param   myJPanel, myLayout, x, y, w, h
    * @returns The JTextArea.
    * ----------------------------------------------------------
    */
    public static JTextArea LocateAJTextArea(JPanel myJPanel, SpringLayout myLayout, int x, int y, int w, int h)
    {    
        JTextArea myJTextArea = new JTextArea(w,h);
        myJTextArea.setWrapStyleWord(true);
        myJTextArea.setLineWrap(true);
        //myJTextArea.
        JScrollPane painInThe = new JScrollPane(myJTextArea);
        myJPanel.add(painInThe);
        myLayout.putConstraint(SpringLayout.WEST, myJTextArea, x, SpringLayout.WEST, myJPanel);
        myLayout.putConstraint(SpringLayout.NORTH, myJTextArea, y, SpringLayout.NORTH, myJPanel);
        myLayout.putConstraint(SpringLayout.WEST, painInThe, x, SpringLayout.WEST, myJPanel);
        myLayout.putConstraint(SpringLayout.NORTH, painInThe, y, SpringLayout.NORTH, myJPanel);
        return myJTextArea;
    }
    
    
}