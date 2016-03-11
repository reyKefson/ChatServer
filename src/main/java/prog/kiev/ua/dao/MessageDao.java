package prog.kiev.ua.dao;

import prog.kiev.ua.entity.Message;

import java.util.List;


public interface MessageDao {
    List<Message> getAllMessages();
    void add(Message m);
}
