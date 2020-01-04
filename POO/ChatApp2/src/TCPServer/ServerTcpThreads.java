package TCPServer;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import View.DialogBox;

public class ServerTcpThreads implements Runnable {

	private final Socket clSocket;
	private OutputStream out;
	private Server_TCP server;
	public ServerTcpThreads(Server_TCP s,Socket so) {
		this.clSocket = so;
		this.server = s;
	}
	@Override
	public void run() {
		try {
			handleClientConnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void handleClientConnect() throws IOException
	{
		InputStream in = this.clSocket.getInputStream();
		this.out = this.clSocket.getOutputStream();
		String input;
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		BufferedReader reader2 = new BufferedReader(new InputStreamReader(System.in));
		while(true)
		{
			if(reader.ready()) // affichage 
			{
				input = reader.readLine();
				System.out.println("userDestination > " + input );
				//this.sendMessage(("You > " + input +"\n").getBytes());
			}
			if(reader2.ready())// send
			{
				input = reader2.readLine();
				this.sendMessage((input + "\n").getBytes());
				System.out.println("You > " + input);
			}
		}
		/*Socket s = new Socket("192.168.1.34",4440);
		new Thread(new ConnectThread(s)).start();
		DialogBox d = new DialogBox();
		InputStream in = s.getInputStream();
		OutputStream out = s.getOutputStream();
		String input;
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		BufferedReader reader2 = new BufferedReader(new InputStreamReader(System.in));
		while(true)
		{
			if(reader2.ready())//send
			{
				input = reader2.readLine();
				out.write((input+"\n").getBytes());
				//System.out.println("You > " + input);
				d.AddTextToJTextArea("You > " + input);
			}
			if(reader.ready())//rcv
			{
				input = reader.readLine();
				//out.write((input + "\n").getBytes());
				//System.out.println("Destinataire > " + input);
				d.AddTextToJTextArea("Destinataire > " + input);
			}
		}*/
		//clSocket.close();
	}
	//Pour fermer le socket on crée une methode
	
	
	private void sendMessage(byte[] b) throws IOException 
	{
		this.out.write(b,0,b.length);
	}
}
