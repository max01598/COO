package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;

public class InterfaceV2 extends JFrame {

	//JFrame
		//Panel global en FlowLayout
			//Panel de gauche avec taille spécifique gris
			//Panel de droite qui est une dialogueBox
	Dimension screenDimension;
	public InterfaceV2()
	{
		setLayout(new BorderLayout());
		add(ListUser(),BorderLayout.WEST);
		screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		setExtendedState(MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new InterfaceV2();
	}
	
	private JPanel ListUser()
	{
		JPanel leftPanel = new JPanel(new BorderLayout());
		leftPanel.add(new JLabel("Utilisateurs connectés : "),BorderLayout.NORTH);
		JPanel listConteneur = new JPanel();
		listConteneur.setLayout(new GridLayout(10, 1));
		listConteneur.setBackground(new Color(175, 30, 0));
		for(int i=0;i < 10;i++)
		{
			listConteneur.add(buttonPrefab(i, listConteneur.getWidth()));
			
		}
		leftPanel.add(listConteneur,BorderLayout.CENTER);
		return leftPanel;
	}
	
	private JButton buttonPrefab(int i, int size)
	{
		JButton butt = new JButton();
		butt.setText("User "+i);
		//butt.setPreferredSize(new Dimension(size, butt.getHeight()));
		butt.setOpaque(false);
		butt.setContentAreaFilled(false);
		butt.setBorderPainted(false);
		butt.setVisible(true);
		butt.setForeground(Color.white);
		butt.addMouseListener( new MouseAdapter() {
		    public void mouseEntered( MouseEvent e ) {
		    	butt.setOpaque(true);
				butt.setContentAreaFilled(true);
				butt.setBorderPainted(true);
		        butt.setBackground(Color.black);
		    }
		    
		    public void mouseExited( MouseEvent e ) {
		    	butt.setOpaque(false);
				butt.setContentAreaFilled(false);
				butt.setBorderPainted(false);
		    }
		} );
		butt.addActionListener(new ActionListener(){  
		    public void actionPerformed(ActionEvent e){
	           //open the dialogBox 
		    	add(Dialogox(),BorderLayout.CENTER);
		    	setTitle("Conversation with : User "+i);
		    	invalidate();
		    	validate();
		    	repaint();
		    }  
	    });  
		return butt;
	}
	
	private JPanel Dialogox()
	{
		JPanel dialogPanel = new JPanel(new BorderLayout());
		JPanel redPanel = new JPanel(new BorderLayout());
		JTextArea conversation = new JTextArea(5,8);
		conversation.setEditable(false);
		redPanel.add(conversation, BorderLayout.CENTER);
		
		JPanel yellowPanel = new JPanel(new FlowLayout());
		JTextField messageToSend = new JTextField(80);
		JButton sendButt = new JButton("Send");
		//sendButt.addActionListener(this);
		yellowPanel.add(messageToSend);
		yellowPanel.add(sendButt);
		
		dialogPanel.add(redPanel,BorderLayout.CENTER);
		dialogPanel.add(yellowPanel,BorderLayout.SOUTH);
		
		return dialogPanel;
	}

}
