package AppMain;

import java.util.ArrayList;

import Model.User;

public class GeneralUtils {

	public boolean CheckUnicity(ArrayList<User> array, String log)
	{
		for (User user : array) {
			if(log.equalsIgnoreCase(user.getLogin()))
			{
				return false;
			}
		}
		return true;
	}
}
