package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import TCPServer.ConnectThread;

//slide 30 pour le listener
public class DialogBox extends JFrame implements ActionListener {
	//JFrame
		//JPanel global gridLayout
			//Rectangle rouge : JPanel 
				//JTextArea /!\ non editable  
			//Rectangle jaune : JPanel en FlowLayout
				//JTextFiel 
				//JButton
	JTextArea conversation;
	ConnectThread thread;
	JTextField messageToSend;
	JButton sendButt;
	
	public DialogBox(ConnectThread t, String login)
	{
		this.thread = t;
		
		JPanel globalPanel = new JPanel(new BorderLayout());
		
		JPanel redPanel = new JPanel(new BorderLayout());
		conversation = new JTextArea(5,8);
		conversation.setEditable(false);
		redPanel.add(conversation, BorderLayout.CENTER);
		
		JPanel yellowPanel = new JPanel(new FlowLayout());
		messageToSend = new JTextField(80);
		sendButt = new JButton("Send");
		sendButt.addActionListener(this);
		yellowPanel.add(messageToSend);
		yellowPanel.add(sendButt);
		
		globalPanel.add(redPanel,BorderLayout.CENTER);
		globalPanel.add(yellowPanel,BorderLayout.SOUTH);
		
		this.add(globalPanel);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Conversation with "+ login);
		pack();
		setVisible(true);
	}
	
	public void AddTextToJTextArea(String txt)
	{
		String act = conversation.getText();
		String newTxt = act + "\n" + txt;
		conversation.setText(newTxt);
	}
	
	
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == sendButt)
		{
			try {
				this.thread.send(messageToSend.getText());
				messageToSend.setText("");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
