package core.messages.criteria;

public abstract class resendCriteria {

	private String destination;
	
	public resendCriteria(String destination){
		this.destination = destination;
	}
	
	public abstract String evaluate();
}
