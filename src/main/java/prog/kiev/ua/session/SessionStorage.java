package prog.kiev.ua.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionStorage {

	private static final SessionStorage idList = new SessionStorage();

	private final Map<String, Integer> list = new ConcurrentHashMap<>();

	public static SessionStorage getInstance() {
		return idList;
	}

    private SessionStorage() {}
	
	public synchronized void add(String sessionId, Integer id) {
			list.put(sessionId, id);
	}

	public synchronized Integer getUserId(String sessionId) {
		if (!list.containsKey(sessionId)) {
			return null;
		}
		return list.get(sessionId);
	}

}
