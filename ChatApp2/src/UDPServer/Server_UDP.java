package UDPServer;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import AppMain.chatApp;

public class Server_UDP implements Runnable {

	private int port;
	private DatagramSocket serverSocket;
	private chatApp cApp;
	
	public Server_UDP(chatApp c) {
		this.port = 9880;
		this.cApp = c;
	}
	@Override
	public void run() {
		 try {
			this.serverSocket = new DatagramSocket(this.port);
			byte[] receiveData = new byte[1024];
	        while(true)
            {
               DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
               serverSocket.receive(receivePacket);
               System.out.println("New udp message received");
               Thread t = new Thread(new ServerUdpThreads(receivePacket,this.serverSocket,this.cApp.getCurrentUser().getLogin(), this ));
               t.start();
            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @return the cApp
	 */
	public chatApp getcApp() {
		return cApp;
	}
	/**
	 * @param cApp the cApp to set
	 */
	public void setcApp(chatApp cApp) {
		this.cApp = cApp;
	}

}
