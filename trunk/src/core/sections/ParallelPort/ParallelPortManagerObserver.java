package core.sections.ParallelPort;

/**
 * Observer interface for the Parallel Port Manager.
 * 
 * @author GelidElf
 * 
 */
public interface ParallelPortManagerObserver {

	/**
	 * Method to be called of all observers once the observed item has changed
	 * 
	 * @param state
	 */
	public abstract void update(ParallelPortManager manager);

}
