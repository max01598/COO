package AppMain;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class NetworkUtils {


	public static InetAddress GetCurrentIP() throws UnknownHostException, SocketException
	{
		try(final DatagramSocket socket = new DatagramSocket()){
		  socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
		  return socket.getLocalAddress();
		}
	}
	
	public static void sendMessage(String mess) throws IOException
	{
		DatagramSocket socket = new DatagramSocket();
        socket.setBroadcast(true);
        byte[] buffer = mess.getBytes();
        DatagramPacket packet;
		packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("255.255.255.255"), 9880);
		socket.send(packet);
        socket.close();
	}
}
