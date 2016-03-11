package prog.kiev.ua.controller;

import prog.kiev.ua.dao.UserDao;
import prog.kiev.ua.dao.impl.UserStorageDao;
import prog.kiev.ua.util.Json;
import javax.servlet.http.*;
import java.io.*;

public class GetUsersServlet extends HttpServlet {

	private UserDao userStorage = UserStorageDao.getInstance();
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws IOException 
	{
		String json = Json.listToJSON(userStorage.getAllLogins());

		if (json != null) {
			OutputStream os = resp.getOutputStream();
			os.write(json.getBytes());
			os.flush();
		}
	}
}
