package prog.kiev.ua.controller;

import prog.kiev.ua.dao.*;
import prog.kiev.ua.dao.impl.*;
import prog.kiev.ua.entity.User;
import prog.kiev.ua.util.Json;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class CreateGroupServlet extends HttpServlet {
	private GroupDao groupStorage = GroupStorageDao.getInstance();;
	private UserDao userStorage = UserStorageDao.getInstance();

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		InputStream is = req.getInputStream();
		byte[] buf = new byte[req.getContentLength()];
		is.read(buf);

		String[] logins = Json.fromJSON(new String(buf));
		if (logins == null || logins.length < 4) {
			resp.setStatus(400); // Bad request
			return;
		}
		String groupName = logins[0];

		List<String> group = new ArrayList<>();
		for (int i = 1; i < logins.length; i++) {
			User user = userStorage.getUserByLogin(logins[i]);
			if (user == null) {
				resp.setStatus(400); // Bad request
				return;
			}
			group.add(user.getLogin());
		}
		groupStorage.addGroup(groupName ,group);
	}
}
