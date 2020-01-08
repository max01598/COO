package View;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import AppMain.chatApp;

public class ListUserConteneur extends JPanel {
	
	ArrayList<ButtList> buttonslist;
	public ListUserConteneur()
	{
		buttonslist = new ArrayList<ButtList>();
		this.setVisible(true);
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
	}
	
	public void GenerateList()
	{
		System.out.println(chatApp.listUser.size());
		ClearList();
		if(!chatApp.listUser.isEmpty())
		{
			for(Map.Entry<InetAddress, String> u : chatApp.listUser.entrySet())
			{
				if(!ContainElement(u.getKey()))
				{
					this.add(new ButtList(u.getKey(), u.getValue()));	
				}
			}
		}
	}
	
	private boolean ContainElement(InetAddress i)
	{
		boolean find = false;
		for (ButtList buttList : buttonslist) {
			if(buttList.getIp().equals(i))
			{
				find = true;
			}
		}
		return find;
	}
	
	private void ClearList()
	{
		for (ButtList buttList : buttonslist) {
			remove(buttList);
		}	
	}
}
