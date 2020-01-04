package TCPServer;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import AppMain.chatApp;

public class Server_TCP implements Runnable{
	private int port;
	private ServerSocket socketListen;
	public ArrayList<ServerTcpThreads> serverth;
	public Server_TCP() {
		this.port = 4440;
		this.serverth = new ArrayList<ServerTcpThreads>();
	}
	@Override
	public void run() {
		try {
			this.socketListen = new ServerSocket(port);
			System.out.println("Server tcp open");
			while(true)
			{
				Socket clientDestSocket = socketListen.accept();
				System.out.println("New connection");
				//Si connection etablie => on ouvre un thread 
				String login = chatApp.listUser.get(clientDestSocket.getInetAddress());
				new Thread(new ConnectThread(clientDestSocket, login)).start();
			}	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
