package ParallelPort;

/**
 * Observer interface for the Parallel Port Manager.
 * @author GelidElf
 *
 */
public interface ParallelPortObserver {

	public abstract void update(ParallelPortState state);
	
}
