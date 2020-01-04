package View;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import AppMain.chatApp;

public class HomeView extends JFrame implements ActionListener {

	//Panel Global => en borderLayout
		//Panel green Center
			//Text
		//Panel rouge EAST borderLayout
			//Panel titreListe north
			//Panel liste center
				//Liste de bouton
	ListUserConteneur list;
	public HomeView() {
		JPanel global = new JPanel(new BorderLayout());
		
		JPanel green = new JPanel();
		JLabel welcomeTxt = new JLabel("Welcome on board "+ chatApp.login);
		green.add(welcomeTxt);
		global.add(green,BorderLayout.CENTER);
		
		JPanel red = new JPanel(new BorderLayout());
		red.add(new JLabel("User Connected"), BorderLayout.NORTH);
		list = new ListUserConteneur();
		list.GenerateList();
		System.out.println(chatApp.listUser.size());
		
		red.add(list, BorderLayout.CENTER);
		global.add(red,BorderLayout.EAST);
		
		this.add(global);
		this.setSize(800, 800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Home sweet Home");
		setVisible(true);		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	public void ActualiseList() {
		list.GenerateList();
		SwingUtilities.updateComponentTreeUI(this);
	}

}
