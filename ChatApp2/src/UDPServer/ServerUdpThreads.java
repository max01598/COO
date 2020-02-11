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
	private Server_UDP udpServ;
	public ServerUdpThreads(DatagramPacket d, DatagramSocket s, String l, Server_UDP udpS) {
		this.dt = d;
		this.server = s;
		this.login = l;
		this.udpServ = udpS;
	}
	
	@Override
	public void run() {
		try {
			String sentence = new String( this.dt.getData(), 0, this.dt.getLength());
			System.out.println(sentence);
			sentence.trim();
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
				else if("disconnect".equalsIgnoreCase(tokens[0]))
				{
					handleDisconnectedMessage(tokens);
				}
				else if("change".equalsIgnoreCase(tokens[0]))
				{
					handleEditPseudo(tokens);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void handleConnectedMessage(String tokens[]) throws IOException {
		//aucun envoi juste ajout dans une liste de users
		//chatApp.addUser(this.dt.getAddress(), tokens[1]);
		this.udpServ.getcApp().addUser(this.dt.getAddress(), tokens[1].trim());
	}

	private void handleWhoMessage() throws IOException
	{
		if(this.udpServ.getcApp().getState() == States.CONNECTED)
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
		this.udpServ.getcApp().removeUser(this.dt.getAddress());
	}
	
	private void handleEditPseudo(String tokens[])
	{
		if(tokens[1]!=null)
			this.udpServ.getcApp().changementPseudo(this.dt.getAddress(), tokens[1].trim());
	}

}
