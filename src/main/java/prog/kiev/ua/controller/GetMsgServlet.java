package prog.kiev.ua.controller;

import prog.kiev.ua.dao.MessageDao;
import prog.kiev.ua.dao.impl.MessageStorageDao;
import prog.kiev.ua.entity.User;
import prog.kiev.ua.util.Json;
import java.io.*;
import javax.servlet.http.*;

public class GetMsgServlet extends HttpServlet {
	public static final String PARAM_FROM = "from";
	public static final String PARAM_TYPE = "type";
	public static final String PARAM_PUBLIC = "public";
	public static final String PARAM_PERSONAL = "personal";

	private MessageDao msgList = MessageStorageDao.getInstance();
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws IOException 
	{
		String fromStr = req.getParameter(PARAM_FROM);
		if (fromStr != null) {
			try {
				int from = Integer.parseInt(fromStr);
				String type = req.getParameter(PARAM_TYPE);
				String json = null;

				if (type.equals(PARAM_PUBLIC)) {
					json = Json.messagesToJSON(msgList.getAllMessages(), from);
				} else if (type.equals(PARAM_PERSONAL)) {
					User user = (User) req.getAttribute("user");
					json = Json.messagesToJSON(user.getMessages(), from);
				}

				if (json != null) {
					OutputStream os = resp.getOutputStream();
					os.write(json.getBytes());
				}
				return;
			} catch (NumberFormatException e) {
				//NOP
			}
		}
		resp.setStatus(400); // Bad request

	}
}
