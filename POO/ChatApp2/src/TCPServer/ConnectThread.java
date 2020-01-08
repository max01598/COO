package TCPServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Timer;

import View.DialogBox;

public class ConnectThread implements Runnable {

	DialogBox view;
	Socket s;
	InputStream in;
	OutputStream out;
	BufferedReader reader;
	String loginDestinataire;
	boolean inWork;
	public ConnectThread(Socket sock, String login) throws IOException {
		this.s = sock;
		this.in = this.s.getInputStream();
		this.out = this.s.getOutputStream();
		this.view = new DialogBox(this, login);
		this.loginDestinataire = login;
		this.inWork = true;
	}
	@Override
	public void run() {
		try {
			String input;
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			while(this.inWork)
			{
				if(reader.ready())//rcv
				{
					input = reader.readLine();
					this.view.AddTextToJTextArea(this.loginDestinataire + " > " + input);
				}
			}
			reader.close();
			this.out.close();
			this.s.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void send(String txt) throws IOException
	{
		out.write((txt+"\n").getBytes());
		this.view.AddTextToJTextArea("You > " + txt);
	}
	
	public void dispose()
	{
		this.inWork = false;
	}

}
