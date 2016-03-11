package prog.kiev.ua.dao.impl;

import prog.kiev.ua.dao.MessageDao;
import prog.kiev.ua.entity.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageStorageDao implements MessageDao {

	private static final MessageStorageDao msgList = new MessageStorageDao();

	private final List<Message> list = new ArrayList<Message>();

	public static MessageStorageDao getInstance() {
		return msgList;
	}

	private MessageStorageDao() {}

	@Override
	public List<Message> getAllMessages() {
		return new ArrayList<>(list);
	}

	@Override
	public synchronized void add(Message m) {
		list.add(m);
	}
}
