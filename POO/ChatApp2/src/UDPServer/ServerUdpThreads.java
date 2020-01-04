package UDPServer;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import AppMain.*;

public class ServerUdpThreads implements Runnable {
	
	private DatagramPacket dt;
	private DatagramSocket server;
	private String login;
	public ServerUdpThreads(DatagramPacket d, DatagramSocket s, String l) {
		this.dt = d;
		this.server = s;
		this.login = l;
	}
	
	@Override
	public void run() {
		try {
			String sentence = new String( this.dt.getData(), 0, this.dt.getLength());
			System.out.println(sentence);
			if("who".equalsIgnoreCase(sentence))
			{
					handleWhoMessage();
			}else {
				String tokens[] = sentence.split(" ");
				if("connected".equalsIgnoreCase(tokens[0]))
				{
					handleConnectedMessage(tokens);
				}
				else if("login".equalsIgnoreCase(tokens[0]))
				{
					handleConnectedMessage(tokens);
				}
				else if("disconnected".equalsIgnoreCase(tokens[0]))
				{
					
				}
					
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void handleConnectedMessage(String tokens[]) throws IOException {
		//aucun envoi juste ajout dans une liste de users
		chatApp.addUser(this.dt.getAddress(), tokens[1]);
	}

	private void handleWhoMessage() throws IOException
	{
		if(chatApp.connected)
		{
			byte[] sendData = new byte[1024];
			InetAddress IPAddress = this.dt.getAddress();
		    int port = 9880;
		    String returnMessage = "connected "+ this.login;
		    sendData = returnMessage.getBytes();
		    DatagramPacket sendPacket =
		    new DatagramPacket(sendData, sendData.length, IPAddress, port);
		    this.server.send(sendPacket);
		}
	}
	
	private void handleDisconnectedMessage(String tokens[])
	{
		chatApp.removeUser(this.dt.getAddress());
	}
    

}
