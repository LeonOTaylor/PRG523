package Sockets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ChatClientUI {
	
	JFrame window;
	Client client;
	JList chatHistory;
	ArrayList<String> chatHistoryData;
	
	public ChatClientUI()
	{
		window = new JFrame("Chat Client");
		window.setMinimumSize(new Dimension(400, 400));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		Container content = window.getContentPane();
		content.setLayout(new GridBagLayout());
		
		chatHistoryData = new ArrayList<>();
		
		createUI();
		window.pack();
		window.setVisible(true);
	}
	
	private void createUI()
	{
		JTextField serverAddress = new JTextField();
		addComponent(serverAddress, 0, 0, 2, 1, 0f, 0f);
		
		JButton connect = new JButton("Connect");
		addComponent(connect, 0, 1, 2, 1, 0f, 0f);
		connect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Open connection with: !" + serverAddress.getText());
				client = new Client(serverAddress.getText(), new MessageListener() {
					@Override
					public void message(String msg, MessageSender sender) {
						chatHistoryData.add(msg);
						chatHistory.setListData(chatHistoryData.toArray());
					}
				});
			}
		});
		
		JList chatHistory = new JList();
		addComponent(chatHistory, 0, 2, 2, 1, 0f, 0f);
		
		JTextField chatBox = new JTextField();
		addComponent(chatBox, 0, 3, 1, 1, 1f, 0f);
		
		JButton send = new JButton("Send");
		addComponent(send, 1, 3, 1, 1, 0f, 0f);
		send.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Send pressed with: " + chatBox.getText());
				if(client != null) {
					client.sendMessage(chatBox.getText());
				}
				chatBox.setText("");
			}
		});
	}
	
	
	
	private <C extends Component> C addComponent(C Component, int gridX, int gridY, int gridwidth, int gridheight,
			float weightX, float weightY) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = gridX;
		constraints.gridy = gridY;
		constraints.gridwidth = gridwidth;
		constraints.gridheight = gridheight;
		constraints.weightx = weightX;
		constraints.weighty = weightY;
		
		window.getContentPane().add(Component, constraints);
		
		return Component;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run()
			{
				ChatClientUI client = new ChatClientUI();
				client.createUI();
			}
		});
		
	}

}
