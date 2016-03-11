package prog.kiev.ua.dao.impl;

import prog.kiev.ua.dao.UserDao;
import prog.kiev.ua.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;


public class UserStorageDao implements UserDao {
    private static final UserStorageDao users = new UserStorageDao();
    private final Map<Integer, User> memory = new ConcurrentHashMap<>();

    public static final UserStorageDao getInstance() {
        return users;
    }

    private UserStorageDao() {
    }

    @Override
    public User getUserById(Integer id) {
        return id == null ? null : memory.get(id);
    }

    @Override
    public User getUserByLogin(String login) {
        for (Map.Entry<Integer, User> node : memory.entrySet()) {
            User user = node.getValue();
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<String> getLoginOnlineUsers() {
        long currentTimeMillis = System.currentTimeMillis();
        long dT = TimeUnit.MINUTES.toMillis(1);
        List<String> logins = new ArrayList<>();

        for (Map.Entry<Integer, User> node : memory.entrySet()) {
            User user = node.getValue();
            if ((currentTimeMillis - user.getLastRequest()) < dT)
                logins.add(user.getLogin());
        }
        return logins;
    }

    @Override
    public List<String> getAllLogins() {
        List<String> logins = new ArrayList<>();
        for (Map.Entry<Integer, User> node : memory.entrySet())
            logins.add(node.getValue().getLogin());
        return logins;
    }

    @Override
    public synchronized void addUser(Integer id, User user) {
        memory.put(id, user);
    }
}
