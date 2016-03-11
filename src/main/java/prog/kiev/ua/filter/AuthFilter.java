package prog.kiev.ua.filter;


import prog.kiev.ua.dao.UserDao;
import prog.kiev.ua.dao.impl.UserStorageDao;
import prog.kiev.ua.session.SessionStorage;
import prog.kiev.ua.entity.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter extends BaseFilter {
    public static final String PAGE_LOGIN = "/login";
    public static final String ATTRIBUTE_MODEL = "user";
    private UserDao userStorage = UserStorageDao.getInstance();
    private SessionStorage sessionList = SessionStorage.getInstance();

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {

        HttpSession session = req.getSession();

        Integer userId = sessionList.getUserId(session.getId());
        User user = userStorage.getUserById(userId);

        if (user != null) {
            user.setLastRequest(System.currentTimeMillis());
            req.setAttribute(ATTRIBUTE_MODEL, user);
            chain.doFilter(req, resp);
            return;
        }
        req.getRequestDispatcher(PAGE_LOGIN).forward(req, resp);
    }
}
