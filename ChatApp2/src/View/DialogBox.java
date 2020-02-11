package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	private JTextArea conversation;
	private ConnectThread thread;
	private JTextField messageToSend;
	private JButton sendButt;
	private PreparedStatement r;
	private String log;
	public DialogBox(ConnectThread t, String login,PreparedStatement res)
	{
		this.thread = t;
		this.r = res;
		this.log = login;
		
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
		try {
			GenerateHistorique();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent event) {
	            exitProcedure();
	        }
	    });
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
	
	private void GenerateHistorique() throws SQLException
	{
		//System.out.println(this.r.next());
		ResultSet a = this.r.executeQuery();
		while(a.next())
		{
			String em = a.getString(2);
			System.out.println(em);
			if(em.equalsIgnoreCase(this.log))
			{
				AddTextToJTextArea("You > " + a.getString(5));
			}
			else
			{
				AddTextToJTextArea(this.log + " > " + a.getString(5));
			}
		}
	}
	
	private void exitProcedure()
	{
		this.thread.dispose();
		this.dispose();
	}
}
