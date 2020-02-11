package TCPServer;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import AppMain.chatApp;
import Model.User;

public class Server_TCP implements Runnable{
	private int port;
	private ServerSocket socketListen;
	private chatApp cApp;
	public Server_TCP(chatApp c) {
		this.port = 4440;
		this.cApp = c;
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
				String login = findLogin(clientDestSocket.getInetAddress());
				if(login != null)
					new Thread(new ConnectThread(clientDestSocket, login,this.cApp.getCon(),this.cApp.getCurrentUser().getLogin(),1)).start();
			}	
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String findLogin(InetAddress ip)
	{
		for (User u : this.cApp.getListUsers()) {
			if(u.getIp().equals(ip))
				return u.getLogin();
		}
		return null;
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
