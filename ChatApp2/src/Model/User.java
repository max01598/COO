package Model;

import java.net.InetAddress;

public class User extends Object {
	private InetAddress ip;
	private String login;
	
	public User(InetAddress i, String l)
	{
		this.ip = i;
		this.login=l;
	}

	/**
	 * @return the ip
	 */
	public InetAddress getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(InetAddress ip) {
		this.ip = ip;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	
	
}
