package prog.kiev.ua.filter;


import prog.kiev.ua.dao.UserDao;
import prog.kiev.ua.dao.impl.UserStorageDao;
import prog.kiev.ua.entity.User;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class AuthFilter extends BaseFilter {
    public static final String PAGE_LOGIN = "/login";
    public static final String ATTRIBUTE_MODEL = "user";
    private UserDao userStorage = UserStorageDao.getInstance();

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {

        HttpSession session = req.getSession();
        Integer userId = (Integer) session.getAttribute("id");
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
