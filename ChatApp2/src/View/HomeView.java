package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import AppMain.NetworkUtils;
import AppMain.chatApp;

public class HomeView extends JFrame{

	//Panel Global => en borderLayout
	//Panel green Center
	//Text
	//Panel rouge EAST borderLayout
	//Panel titreListe north
	//Panel liste center
	//Liste de bouton
	private JMenu deco;
	private chatApp cApp;
	private JLabel welcomeTxt;
	private JPanel green;
	private Dimension screenDimension;
	private JPanel leftPanel;
	private JPanel listConteneur;
	
	public HomeView(chatApp c) {
		
		setLayout(new BorderLayout());
		
		screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenDimension);
		setExtendedState(MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		
		
		JPanel global = new JPanel(new BorderLayout());
		this.cApp = c;
		add(ListUser(),BorderLayout.WEST);
		JMenuBar bar = new JMenuBar();
		JMenu pseudo = new JMenu("Gestion pseudo");
		bar.add(pseudo);
		pseudo.addMouseListener(new MouseListener() {  
			@Override
			public void mouseReleased(MouseEvent e) {   }

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getSource() != null)
				{
					changeProcedure();
				}     
			}
		}); 

		this.deco = new JMenu("Déconnexion");
		this.deco.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {   }

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getSource() != null)
				{
					exitProcedure();
				}     
			}
		});
		bar.add(this.deco);


		this.add(bar,BorderLayout.NORTH);

		green = new JPanel();
		this.welcomeTxt = new JLabel("Bienvenue : "+ this.cApp.getCurrentUser().getLogin());
		green.add(welcomeTxt);
		global.add(green,BorderLayout.CENTER);

		this.add(global, BorderLayout.CENTER);
		this.setSize(800, 800);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				exitProcedure();
			}
		});
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Applichat");
		setVisible(true);		
	}

	public void ActualiseList() 
	{
		this.remove(leftPanel);
		add(ListUser(),BorderLayout.WEST);
		invalidate();
    	validate();
    	repaint();
	}

	private void exitProcedure()
	{
		this.cApp.disconnect();
	}
	
	private void changeProcedure()
	{
		String inputValue = JOptionPane.showInputDialog("Entrer le nouveau pseudo : ");
		if(!inputValue.isEmpty())
		{
			if(this.cApp.CheckUnicity(inputValue))
			{
				this.cApp.editPseudo(inputValue);
				this.welcomeTxt.setText("Bienvenu : "+ this.cApp.getCurrentUser().getLogin());
				try {
					NetworkUtils.sendMessage("change "+ inputValue);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				invalidate();
		    	validate();
		    	repaint();
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
	
	private JPanel ListUser()
	{
		leftPanel = new JPanel(new BorderLayout());
		leftPanel.add(new JLabel("Utilisateurs connectés : "),BorderLayout.NORTH);
		listConteneur = new JPanel();
		listConteneur.setLayout(new GridLayout(this.cApp.getListUsers().size(), 1));
		listConteneur.setBackground(new Color(175, 30, 0));
		for(int i=0;i < this.cApp.getListUsers().size();i++)
		{
			listConteneur.add(buttonPrefab(
					this.cApp.getListUsers().get(i).getIp(),
					this.cApp.getListUsers().get(i).getLogin(),
					listConteneur.getWidth()
					));
			
		}
		leftPanel.add(listConteneur,BorderLayout.CENTER);
		return leftPanel;
	}
	private JButton buttonPrefab(InetAddress ip, String login , int size)
	{
		JButton butt = new JButton();
		butt.setText("Nom : "+ login);
		butt.setMaximumSize(new Dimension(size,5));
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
				miseEnRelation(ip,login);
		    	invalidate();
		    	validate();
		    	repaint();
		    }  
	    });
		return butt;
	}
	
	private void miseEnRelation(InetAddress i, String log)
	{
		try {
			this.cApp.miseEnRelation(i, log);
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
