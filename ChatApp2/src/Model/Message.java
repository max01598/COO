package Model;

import java.util.Date;

public class Message {

	String loginEm;
	Date date;
	String loginDest;
	String contenu;
	
	public Message(String logE, Date d, String logD, String c)
	{
		this.loginEm = logE;
		this.date = d;
		this.loginDest = logD;
		this.contenu = c;
	}
	
	
}
