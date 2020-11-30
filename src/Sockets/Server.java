package Sockets;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server implements MessageSender{
	
	public static void main(String[] args)
	{
		Server server = new Server(20000, new MessageListener() {
			@Override
			public void message(String msg, MessageSender sender) {
				sender.sendMessage(msg);
			}
		});
	}
	ServerSocket connectionListener;
	MessageListener messageListener;
	ArrayList<ServerClient> clients;

	public Server(int port, MessageListener messageListener)
	{
		this.messageListener = messageListener;
		MessageSender sender = this;
		//this.port = port;
		clients = new ArrayList<>();
		// clients - each conect seperatly, ets added to list above
		(new Thread(new Runnable() {	
            @Override
            public void run() {
                try {
                    connectionListener = new ServerSocket(port);
                    System.out.println("LISTENING>>>");

                    boolean running = true;
                    while (running) {
                        ServerClient client = new ServerClient(connectionListener.accept());
                        clients.add(client);
                        System.out.println("Client Connected " + client.connection.getInetAddress().getHostAddress());

                        (new Thread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("Starting client message listener.... ");
                                while (client.connection.isConnected()) {
                                    try {
                                        messageListener.message(client.inputStream.readLine(), sender);
                                        //System.out.println("MESSAGE RECEIVED IN SERVER CLASS: " + client.inputStream.readLine());
                                    } catch (Exception e) {
                                        System.err.println("Couldn't to read line: " + e);
                                        //break;
                                    }
                                }
                            }
                        })).start();
                    }
                } catch (Exception e) {
                    System.err.println("FAILED TO LISTEN FORA CONNECTION?" + e);
                }
            }
        })).start();
		
	}
	
	@Override 
	public void sendMessage(String msg)
	{
		for(ServerClient client : clients)
		{
			if(client.connection.isClosed())
			{
				clients.remove(client);
			} else {
				client.outputStream.println(msg);
			}
		}
	}
	
	class ServerClient {
		Socket connection;
		PrintWriter outputStream;
		BufferedReader inputStream;
		
		ServerClient(Socket connection)
		{
			this.connection = connection;
			try {
				outputStream = new PrintWriter(connection.getOutputStream(), true);
				inputStream = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			} catch(Exception e)
			{
				System.err.println("Failed to create client: " + e);
			}
		}
	}
}