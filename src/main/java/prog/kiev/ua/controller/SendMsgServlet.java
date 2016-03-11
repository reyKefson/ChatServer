package prog.kiev.ua.controller;

import prog.kiev.ua.dao.*;
import prog.kiev.ua.dao.impl.*;
import prog.kiev.ua.entity.*;
import java.io.*;
import javax.servlet.http.*;

public class SendMsgServlet extends HttpServlet {

	private MessageDao listCommonMsg = MessageStorageDao.getInstance();
	private UserDao userStorage = UserStorageDao.getInstance();

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
		if (msg.getTo() != null) {
			User recipient = userStorage.getUserByLogin(msg.getTo());
			if (recipient == null || recipient.equals(user)) {
				resp.setStatus(400); // Bad request
				return;
			}
			recipient.addMessage(msg);
			return;
		}
		listCommonMsg.add(msg);
	}
}
