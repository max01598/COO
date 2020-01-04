package View;

import java.awt.GridLayout;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JPanel;

import AppMain.chatApp;

public class ListUserConteneur extends JPanel {
	
	ArrayList<ButtList> buttonslist;
	public ListUserConteneur()
	{
		buttonslist = new ArrayList<ButtList>();
		this.setVisible(true);
		this.setLayout(new GridLayout(10,1));
	}
	
	public void GenerateList()
	{
		if(!chatApp.listUser.isEmpty())
		{
			for(Map.Entry<InetAddress, String> u : chatApp.listUser.entrySet())
			{
				if(!buttonslist.contains(new ButtList(u.getKey(), u.getValue())))
				{
					this.add(new ButtList(u.getKey(), u.getValue()));	
				}
			}
		}
	}
}
