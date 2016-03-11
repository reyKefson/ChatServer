package prog.kiev.ua.entity;

import java.util.ArrayList;
import java.util.List;

public class User {
    private static int idCount;
    private final int id;
    private String login;
    private String password;
    private long lastRequest;
    private final List<Message> messageList = new ArrayList<>();

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.id = ++idCount;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public void addMessage(Message m) {
        messageList.add(m);
    }

    public long getLastRequest() {
        return lastRequest;
    }

    public void setLastRequest(long lastRequest) {
        this.lastRequest = lastRequest;
    }

    public List<Message> getMessages() {
        return new ArrayList<>(messageList);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id == user.id;

    }
}
