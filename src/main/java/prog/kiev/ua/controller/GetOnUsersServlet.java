package prog.kiev.ua.controller;

import prog.kiev.ua.dao.UserDao;
import prog.kiev.ua.dao.impl.UserStorageDao;
import prog.kiev.ua.util.Json;
import javax.servlet.http.*;
import java.io.*;
import java.util.List;

public class GetOnUsersServlet extends HttpServlet {

	private UserDao userStorage = UserStorageDao.getInstance();
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws IOException 
	{
		List<String> loginOnlineUsers = userStorage.getLoginOnlineUsers();
		String json = Json.listToJSON(loginOnlineUsers);

		if (json != null) {
			OutputStream os = resp.getOutputStream();
			os.write(json.getBytes());
			os.flush();
		}
	}
}
