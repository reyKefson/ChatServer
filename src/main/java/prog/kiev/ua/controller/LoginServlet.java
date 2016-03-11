package prog.kiev.ua.controller;


import prog.kiev.ua.dao.UserDao;
import prog.kiev.ua.dao.impl.UserStorageDao;
import prog.kiev.ua.session.SessionStorage;
import prog.kiev.ua.entity.User;
import prog.kiev.ua.util.Json;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;


public class LoginServlet extends HttpServlet {
    private UserDao userStorage = UserStorageDao.getInstance();
    private SessionStorage sessionList = SessionStorage.getInstance();

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
            sessionList.add(session.getId(), newUser.getId());
            return;
        }

        boolean passwordCorrect = user.getPassword().equals(userFromReq.getPassword());

        if (!passwordCorrect) {
            resp.setStatus(401); //Unauthorized
            return;
        }
        sessionList.add(session.getId(), user.getId());
    }

    private static boolean userCorrect(User user) {
        if (user.getLogin().isEmpty() || user.getPassword().isEmpty())
            return false;
        return true;
    }

}
