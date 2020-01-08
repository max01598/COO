package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import AppMain.chatApp;

public class LoginView extends JFrame implements ActionListener {

	//Panel Global 
		//JLabel text "Enter login"
		//JTextFiel
		//JButton loginButt
	private JButton loginBuut;
	private JTextField loginTxt;
	public LoginView() {
		JPanel global = new JPanel();
		JLabel txt = new JLabel("Enter login");
		loginTxt = new JTextField(20);
		loginBuut = new JButton("Connection");
		loginBuut.addActionListener(this);
		global.add(txt);
		global.add(loginTxt);
		global.add(loginBuut);
		this.add(global);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(loginTxt.getText()!=null && loginTxt.getText() != " ")
		{
			if(chatApp.CheckUnicity(loginTxt.getText()))
			{
				try {
					chatApp.NewLogin(loginTxt.getText());
					this.dispose();
				} catch (IOException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else
			{
				JOptionPane.showMessageDialog(this, 
				         "Le login que vous venez de rentrer est déja pris" ,
				         " Erreur unicité du login ",
				         JOptionPane.WARNING_MESSAGE);
			}
		}
	}
}
