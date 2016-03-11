package prog.kiev.ua.dao.impl;


import prog.kiev.ua.dao.GroupDao;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class GroupStorageDao implements GroupDao {
    private static final GroupStorageDao groupDao = new GroupStorageDao();
    private final Map<String, List<String>> groups = new ConcurrentHashMap<>();

    public static GroupStorageDao getInstance() {
        return groupDao;
    }

    private GroupStorageDao() {}

    @Override
    public List<String> getGroupByName(String name) {
        return groups.get(name);
    }

    @Override
    public synchronized void addGroup(String name, List<String> group) {
        groups.put(name, group);
    }

    @Override
    public boolean containsUser(String groupName, String login) {
        for (String key : groups.keySet()) {
            if (key.equals(groupName)) {
                for (String userLogin : groups.get(key)) {
                    if (userLogin.equals(login)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
