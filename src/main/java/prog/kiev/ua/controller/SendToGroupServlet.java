package prog.kiev.ua.controller;

import prog.kiev.ua.dao.*;
import prog.kiev.ua.dao.impl.*;
import prog.kiev.ua.entity.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.List;

public class SendToGroupServlet extends HttpServlet {

	private UserDao userStorage = UserStorageDao.getInstance();
	private GroupDao groupStorage = GroupStorageDao.getInstance();

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException
	{
		InputStream is = req.getInputStream();
		byte[] buf = new byte[req.getContentLength()];
		is.read(buf);

		Message msg = Message.fromJSON(new String(buf));
		if (msg == null) {
			resp.setStatus(400); // Bad request
			return;
		}

		User user = (User) req.getAttribute("user");
		msg.setFrom(user.getLogin());
		String groupName = msg.getTo();

		if (groupName != null) {
			if (!groupStorage.containsUser(groupName, user.getLogin())) {
				resp.setStatus(400); // Bad request
				return;
			}

			List<String> group = groupStorage.getGroupByName(groupName);
			if (group == null) {
				resp.setStatus(400); // Bad request
				return;
			}
			for (String login : group) {
				User participant = userStorage.getUserByLogin(login);
				participant.addMessage(msg);
			}
			return;
		}
		resp.setStatus(400); // Bad request
	}
}
