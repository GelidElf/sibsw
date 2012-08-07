package core.messages;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Inbox {

	private final LinkedList<Message> inbox = new LinkedList<Message>();
	private final LinkedList<Message> priorityInbox = new LinkedList<Message>();
	private Lock lock = new ReentrantLock();
	private Condition notEmpty = lock.newCondition();

	public Inbox() {
	}

	public Inbox(boolean isMaster) {

	}

	public void add(Message message) {
		lock.lock();
		try {
			if (message.isUrgent())
				priorityInbox.addLast(message);
			else
				inbox.addLast(message);
			notEmpty.signalAll();
		} finally {
			lock.unlock();
		}
	}

	public Message getMessage() throws InterruptedException {
		lock.lock();
		try {
			Message message = null;
			while (0 == messagesCount() ){
				notEmpty.awaitNanos(100L);
			}
			if (priorityInbox.size() > 0) {
				message = priorityInbox.removeFirst();
			} else if (inbox.size() > 0) {
				message = inbox.removeFirst();
			}
			return message;
		} finally {
			lock.unlock();
		}
	}

	public synchronized int messagesCount() {
		return inbox.size() + priorityInbox.size();
	}
}
