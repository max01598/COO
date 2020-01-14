package TCPServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Timer;

import AppMain.Message;
import AppMain.chatApp;
import View.DialogBox;

public class ConnectThread implements Runnable {

	DialogBox view;
	Socket s;
	InputStream in;
	OutputStream out;
	BufferedReader reader;
	String loginDestinataire;
	boolean inWork;
	public ConnectThread(Socket sock, String login) throws IOException, SQLException {
		this.s = sock;
		this.in = this.s.getInputStream();
		this.out = this.s.getOutputStream();
		Statement statement = chatApp.con.createStatement();
		ResultSet res = statement.executeQuery("SELECT * FROM Message;");
		this.view = new DialogBox(this, login,res);
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
					if("hasquit".equalsIgnoreCase(input))
					{
						this.view.AddTextToJTextArea(this.loginDestinataire + " left ... The convessation will close ...");
					}
					else
					{
						this.view.AddTextToJTextArea(this.loginDestinataire + " > " + input);
						Date date = new Date();  
						PreparedStatement statement = chatApp.con.prepareStatement("INSERT INTO Message(id,login_Emmeteur,date,login_Destinataire,contenu) VALUES (null,?,?,?,?);");
						java.sql.Date date_sql = new java.sql.Date(date.getTime());
						Message m = new Message(chatApp.login, date, this.loginDestinataire, input);
						statement.setString(1, chatApp.login);
						statement.setDate(2, date_sql);
						statement.setString(3, this.loginDestinataire);
						statement.setString(4, input);
						System.out.println(statement.execute());
					}
				}
			}
			send("hasquit");
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
		if(!"hasquit".equalsIgnoreCase(txt))
			this.view.AddTextToJTextArea("You > " + txt);
	}
	
	public void dispose()
	{
		this.inWork = false;
	}

}
