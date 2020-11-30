package Sockets;

public interface MessageListener {
	void message(String msg, MessageSender sender);
}
