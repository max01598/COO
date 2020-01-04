package AppMain;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Scanner;

import TCPServer.*;
import UDPServer.*;
import View.HomeView;
import View.LoginView;

public class chatApp {

	public static HashMap<InetAddress, String> listUser;
	public static String login;
	public static InetAddress ip;
	public static HomeView v;
	public static boolean connected;
	public static void main(String[] args) {
		connected = false;
		//Server TCP peut etre activer que si fin de connection
		ActiveTCPServer();
		//Server UDP
		ActiveUDPServer();
		//Initialisation de la liste des users
		listUser =  new HashMap<InetAddress, String>();
		//R�cuperation adresseIP
		try {
			GetCurrentIP();
		} catch (UnknownHostException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (SocketException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		//Envoi du message login/disconnected/... se fait en datagramme (broadcast)
		System.out.println("Envoi du message who");
		try {
			DatagramSocket socket = new DatagramSocket();
	        socket.setBroadcast(true);
	        String mess = "who";
	        byte[] buffer = mess.getBytes();
	        DatagramPacket packet;
			packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("255.255.255.255"), 9880);
			socket.send(packet);
	        socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		new LoginView();
	}
	
	public static void miseEnRelation(InetAddress ip, String login) throws UnknownHostException, IOException {
		Socket s = new Socket(ip,4440);
		new Thread(new ConnectThread(s, login)).start();
	}

	private static void GetCurrentIP() throws UnknownHostException, SocketException
	{
		try(final DatagramSocket socket = new DatagramSocket()){
		  socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
		  ip = socket.getLocalAddress();
		}
	}
	private static void ActiveTCPServer()
	{
		Server_TCP server = new Server_TCP();
		Thread c = new Thread(server);
		c.start();
	}
	
	private static void ActiveUDPServer()
	{
		Server_UDP server_udp = new Server_UDP();
		Thread u = new Thread(server_udp);
		u.start();
	}

	public static void addUser(InetAddress i, String log)
	{
		if(!listUser.containsKey(ip) && !i.equals(ip) && !log.equals("null"))
		{
			listUser.put(i, log);
			v.ActualiseList();
		}
	}

	public static void NewLogin(String log) throws IOException, InterruptedException
	{
		login = log;
		
		DatagramSocket socket = new DatagramSocket();
        socket.setBroadcast(true);
        String mess = "login "+ login;
        byte[] buffer = mess.getBytes();
        DatagramPacket packet;
		packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("255.255.255.255"), 9880);
		socket.send(packet);
        socket.close();
        
        Thread.sleep(5000);
        v = new HomeView();
        connected = true;
        
	}
	
	public static boolean CheckUnicity(String log)
	{
		return true;
	}

	public static void removeUser(InetAddress address) {
		listUser.remove(address);
	}
}