package TCPServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import Model.Message;
import View.DialogBox;

public class ConnectThread implements Runnable {

	private DialogBox view;
	private Socket s;
	private InputStream in;
	private OutputStream out;
	private String loginDestinataire;
	private String loginEmetteur;
	private boolean inWork;
	private Connection con;
	
	
	public ConnectThread(Socket sock, String loginD,Connection c, String loginE,int type) throws IOException, SQLException {
		this.s = sock;
		this.in = this.s.getInputStream();
		this.out = this.s.getOutputStream();
		this.con = c ;
		PreparedStatement res = this.con.prepareStatement("SELECT * FROM Message where (login_Emmeteur =? and login_Destinataire =?) or (login_Emmeteur =? and login_Destinataire =?);");
		this.loginDestinataire = loginD;
		this.loginEmetteur = loginE;
		res.setString(1, this.loginEmetteur);
		res.setString(2, this.loginDestinataire);
		res.setString(3, this.loginDestinataire);
		res.setString(4, loginEmetteur);
		if(type == 0)//Demande
			this.view = new DialogBox(this, loginD,res);
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
					if(this.view == null)
					{
						PreparedStatement res = this.con.prepareStatement("SELECT * FROM Message where (login_Emmeteur =? and login_Destinataire =?) or (login_Emmeteur =? and login_Destinataire =?);");
						res.setString(1, this.loginEmetteur);
						res.setString(2, this.loginDestinataire);
						res.setString(3, this.loginDestinataire);
						res.setString(4, loginEmetteur);
						this.view = new DialogBox(this, this.loginDestinataire, res);
					}
					if("hasquit".equalsIgnoreCase(input))
					{
						this.view.AddTextToJTextArea(this.loginDestinataire + " left ... The convessation will close ...");
						Thread.sleep(3000);
						this.inWork = false;
						this.view.dispose();
					}
					else
					{
						this.view.AddTextToJTextArea(this.loginDestinataire + " > " + input);
						Date date = new Date();  
						PreparedStatement statement = this.con.prepareStatement("INSERT INTO Message(id,login_Emmeteur,date,login_Destinataire,contenu) VALUES (null,?,?,?,?);");
						java.sql.Date date_sql = new java.sql.Date(date.getTime());
						Message m = new Message(this.loginEmetteur, date, this.loginDestinataire, input);
						statement.setString(1, this.loginEmetteur);
						statement.setDate(2, date_sql);
						statement.setString(3, this.loginDestinataire);
						statement.setString(4, input);
						System.out.println(statement.execute());
					}
				}
			}
			if(this.out != null)
			{
				send("hasquit");
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
		if(!"hasquit".equalsIgnoreCase(txt))
			this.view.AddTextToJTextArea("You > " + txt);
	}
	
	public void dispose()
	{
		this.inWork = false;
	}

}
