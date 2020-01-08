package View;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;

import javax.swing.JButton;

import AppMain.chatApp;

public class ButtList extends JButton implements ActionListener {

	private InetAddress ip;
	private String login;
	private Image img;
	
	public ButtList(InetAddress i, String log )
	{
		this.ip = i;
		this.login = log;
		this.setText(log + i.toString());
		this.addActionListener(this);
		this.setVisible(true);
		this.setSize(10, 5);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			System.out.println("Will enter on connection");
			chatApp.miseEnRelation(this.ip, this.login);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public InetAddress getIp()
	{
		return this.ip	;
	}
	
	
	
	
}
