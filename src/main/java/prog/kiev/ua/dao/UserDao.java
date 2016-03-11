package prog.kiev.ua.dao;

import prog.kiev.ua.entity.User;

import java.util.List;


public interface UserDao {
    User getUserById(Integer id);
    User getUserByLogin(String login);
    List<String> getLoginOnlineUsers();
    List<String> getAllLogins();
    void addUser(Integer id, User user);
}
