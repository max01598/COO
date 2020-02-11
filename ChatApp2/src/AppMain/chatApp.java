package AppMain;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;


import Model.User;
import TCPServer.*;
import UDPServer.*;
import View.HomeView;
import View.LoginView;


public class chatApp {
	final static String  className = "com.mysql.cj.jdbc.Driver";
	final static String nomDeDomaine = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/tpservlet_10";
	final static String login = "tpservlet_10";
	final static String mdp = "Phee7aed";
	
	private Server_TCP server_tcp;
	private Server_UDP server_udp;
	private User currentUser;
	private ArrayList<User> listUsers;
	private States state;
	private HomeView homeView;
	private Connection con;
	
	public chatApp(String classN, String c, String l, String mdp) throws IOException, ClassNotFoundException, SQLException, ParseException
	{
		this.listUsers = new ArrayList<User>();
		ActiveTCPServer();
		ActiveUDPServer();
		//connectDB(classN,c,l,mdp);
		currentUser = new User(NetworkUtils.GetCurrentIP(),"none");
		state = States.IDLE;
		NetworkUtils.sendMessage("who");
	}
	

	private void ActiveTCPServer()
	{
		this.server_tcp = new Server_TCP(this);
		Thread c = new Thread(server_tcp);
		c.start();
	}
	
	/**
	 * @return the currentUser
	 */
	public User getCurrentUser() {
		return currentUser;
	}


	/**
	 * @param currentUser the currentUser to set
	 */
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}


	/**
	 * @return the state
	 */
	public States getState() {
		return state;
	}


	/**
	 * @param state the state to set
	 */
	public void setState(States state) {
		this.state = state;
	}


	/**
	 * @return the listUsers
	 */
	public ArrayList<User> getListUsers() {
		return listUsers;
	}


	/**
	 * @param listUsers the listUsers to set
	 */
	public void setListUsers(ArrayList<User> listUsers) {
		this.listUsers = listUsers;
	}

	/**
	 * @return the con
	 */
	public Connection getCon() {
		return con;
	}


	/**
	 * @param con the con to set
	 */
	public void setCon(Connection con) {
		this.con = con;
	}

	private void ActiveUDPServer()
	{
		this.server_udp = new Server_UDP(this);
		Thread u = new Thread(this.server_udp);
		u.start();
	}
	
	public void addUser(InetAddress i, String log)
	{
		System.out.println(this.listUsers);
		boolean valide = true;
		System.out.println(log);
		if(i.equals(this.currentUser.getIp()) || log.equals("null"))
		{
			valide = false;
		}
		for (User user : listUsers) {
			if(user.getIp() == i)
				valide = false;
		}
		
		if(valide)
		{
			this.listUsers.add(new User(i,log));
			if(this.state == States.CONNECTED)
				this.homeView.ActualiseList();
		}
	}

	public void NewLogin(String log) throws IOException, InterruptedException
	{
		this.currentUser.setLogin(log);
		String mess = "login "+ log;
		NetworkUtils.sendMessage(mess);
		Thread.sleep(5000);
        this.homeView = new HomeView(this);
        this.state = States.CONNECTED;
	}
	
	public boolean CheckUnicity(String log)
	{
		for (User user : listUsers) {
			if(log.trim().equalsIgnoreCase(user.getLogin()))
			{
				return false;
			}
		}
		return true;
	}

	public void removeUser(InetAddress address) {
		for (User user : listUsers) {
			if(user.getIp().equals(address))
			{
				this.listUsers.remove(user);
				break;
			}
		}
		if(this.state == States.CONNECTED)
			this.homeView.ActualiseList();
	}
	
	public void miseEnRelation(InetAddress ip, String login1) throws UnknownHostException, IOException, SQLException {
		Socket s = new Socket(ip,4440);
		new Thread(new ConnectThread(s, login1,this.con,this.getCurrentUser().getLogin(), 0)).start();
	}
	

	private void connectDB(String cn, String name,String log, String mdp) throws ClassNotFoundException, SQLException, ParseException
	{
		this.con = null;
		Class.forName(cn);
		this.con = DriverManager.getConnection(name,log,mdp);
	}
	
	public void disconnect()
	{
		try {
			NetworkUtils.sendMessage("disconnect "+ this.currentUser.getLogin());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.homeView.dispose();
		state = States.DISCONNECTED;
		System.exit(0);;
	}
	
	public void editPseudo(String inputValue) {
		currentUser.setLogin(inputValue);
	}
	
	public void changementPseudo(InetAddress ip, String newlog)
	{
		for (User user : listUsers) {
			if(user.getIp().equals(ip))
			{
				user.setLogin(newlog);
			}
		}
		this.homeView.ActualiseList();
	}
	
	public static void main(String[] args) {
		chatApp c = null;
		System.setProperty("file.encoding", "UTF-8");
		try {
			c = new chatApp(chatApp.className,chatApp.nomDeDomaine,chatApp.login,chatApp.mdp);
		} catch (IOException | ClassNotFoundException | SQLException | ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		if(c!=null)
			new LoginView(c);
		
		/*try {
			Thread.sleep(10000);
			NetworkUtils.sendMessage("login lala");
			Thread.sleep(10000);
			NetworkUtils.sendMessage("disconnect lala");
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}
