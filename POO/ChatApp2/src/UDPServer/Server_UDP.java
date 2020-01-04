package UDPServer;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import AppMain.chatApp;

public class Server_UDP implements Runnable {

	private int port;
	private DatagramSocket serverSocket;
	public Server_UDP() {
		this.port = 9880;
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
               System.out.println("New mess");
               Thread t = new Thread(new ServerUdpThreads(receivePacket,this.serverSocket, chatApp.login ));
               t.start();
            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
