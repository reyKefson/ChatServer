package prog.kiev.ua.controller;


import prog.kiev.ua.dao.UserDao;
import prog.kiev.ua.dao.impl.UserStorageDao;
import prog.kiev.ua.entity.User;
import prog.kiev.ua.util.Json;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;


public class LoginServlet extends HttpServlet {
    public static final String ATTRIBUTE_USER_ID = "id";
    private UserDao userStorage = UserStorageDao.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        InputStream is = req.getInputStream();
        byte[] buf = new byte[req.getContentLength()];
        is.read(buf);

        User userFromReq = Json.userFromJSON(new String(buf));
        if (userFromReq == null || !userCorrect(userFromReq)) {
            resp.setStatus(400); // Bad request
            return;
        }

        User user = userStorage.getUserByLogin(userFromReq.getLogin());
        if (user == null) {
            User newUser = new User(userFromReq.getLogin(), userFromReq.getPassword());
            userStorage.addUser(newUser.getId(), newUser);
            session.setAttribute(ATTRIBUTE_USER_ID, newUser.getId());
            return;
        }

        boolean passwordCorrect = user.getPassword().equals(userFromReq.getPassword());

        if (!passwordCorrect) {
            resp.setStatus(401); //Unauthorized
            return;
        }
        session.setAttribute(ATTRIBUTE_USER_ID, user.getId());

    }

    private static boolean userCorrect(User user) {
        if (user.getLogin().isEmpty() || user.getPassword().isEmpty())
            return false;
        return true;
    }

}
