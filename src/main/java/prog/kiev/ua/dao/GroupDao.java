package prog.kiev.ua.dao;


import java.util.List;


public interface GroupDao {
    List<String> getGroupByName(String name);
    void addGroup(String name, List<String> group);
    boolean containsUser(String groupName, String login);
}
