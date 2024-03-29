package core.messages;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Message storage
 * 
 * Contains a proproty queue and a normal queue, priority messages ar added to
 * the priority queue, and are read before the normal messages
 * 
 * Uses a lock and condition so that the read is blocked until there is
 * something to read
 * 
 * 
 * @author GelidElf
 * 
 */
public class Inbox {

	private final LinkedList<Message> inbox = new LinkedList<Message>();
	private final LinkedList<Message> priorityInbox = new LinkedList<Message>();
	private final LinkedList<Message> remainders = new LinkedList<Message>();
	private Lock lock = new ReentrantLock();
	private Condition notEmpty = lock.newCondition();
	private String owner;

	public Inbox(String owner) {
		this.owner = owner;
	}

	/**
	 * Adds a message to the queues, if is urgent, adds it to the priority queue
	 * Notifies all blocked threads that there is a message to read
	 * 
	 * param message the message to add
	 */
	public void add(Message message) {
		//Logger.println(owner + "->" + message.toString());
		lock.lock();
		try {
			if (message.isUrgent()) {
				priorityInbox.addLast(message);
			} else {
				inbox.addLast(message);
			}
			notEmpty.signalAll();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Reads a message from the queues, from the priority one until there are no
	 * more, then from the normal one. Reads that find the inbox empty are
	 * blocked until they can return a message
	 * 
	 * @return the message read
	 * @throws InterruptedException
	 *             if the blocking failed
	 */
	public Message getMessage() throws InterruptedException {
		lock.lock();
		try {
			Message message = null;
			while (0 == messagesCount()) {
				notEmpty.awaitNanos(100L);
			}
			if (priorityInbox.size() > 0) {
				message = priorityInbox.removeFirst();
				remainders.add(message);
			} else if (inbox.size() > 0) {
				message = inbox.removeFirst();
				remainders.add(message);
			} else {
				if (remainders.size() > 0) {
					message = remainders.removeFirst();
					remainders.addLast(message);
				}
			}
			return message;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Returns the number of messages stored in the Inbox
	 * 
	 * @return the sum total of the messages stored in the queues
	 */
	public synchronized int messagesCount() {
		return inbox.size() + priorityInbox.size() + remainders.size();
	}

	/**
	 * Removes the message from this inbox
	 * 
	 * @param message
	 *            the message to be removed.
	 */
	public void remove(Message message) {
		if (!message.getConsumed()) {
			return;
		}
		lock.lock();
		if (remainders.contains(message)) {
			remainders.remove(message);
		}
		lock.unlock();
	}
}
