package core.model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import core.aplication.Configuration;
import core.messages.Attribute;
import core.messages.CommunicationManager;
import core.messages.Message;

public abstract class AutomataContainer<T extends Enum<T>> extends Thread{

	protected Configuration conf;
	private CommunicationManager commManager;
	protected AutomataContainer<?> father;
	protected BlockingQueue<T> inputStorage = new LinkedBlockingQueue<T>();
	protected BlockingQueue<T> priorityInputStorage = new LinkedBlockingQueue<T>();
	
	public AutomataContainer(AutomataContainer<?> father){
		this.father = father;
	}

	public AutomataContainer(AutomataContainer<?> father, CommunicationManager commManager){
		this.father = father;
		this.commManager = commManager;
	}
	
	public CommunicationManager getCommunicationManager(){
		return commManager;
	}
	
	public void injectMessage(Message message){
		switch (message.getType()) {
		case START:
			begin();
			break;
		case COMMAND:
			if (message.isUrgent()){
				priorityInputStorage.add((T)message.getInputType());
			}else{
				inputStorage.add((T)message.getInputType());
			}
			break;
		case CONFIGURATION:
			for (Attribute attribute: message.getAttributes())
			changeConfigurationParameter(attribute);
		break;
		default:
			break;
		}
	}

	@Override
	public void run() {
		while(true){
			super.run();
			try {
				if (priorityInputStorage.size() == 0){
					consume(inputStorage.take());
				}else{
					consume(priorityInputStorage.poll());
				}
			} catch (InterruptedException e) {
				System.out.println(this.getClass().getCanonicalName() + " Interrupted");
			}
		}
	}

	public void feedInput(T input){
		inputStorage.add(input);
	}
	
	public void feedPriorityInput(T input){
		priorityInputStorage.add(input);
	}
	
	protected abstract void consume(T currentInput);
	
	protected abstract void begin();
	
	protected abstract void changeConfigurationParameter(Attribute attribute);
	
	public void sendMessage(Message message){
		commManager.sendMessage(message);
	}
}
