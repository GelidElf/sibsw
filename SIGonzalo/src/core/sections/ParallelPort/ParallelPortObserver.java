package core.sections.ParallelPort;

/**
 * Observer interface for the Parallel Port Manager.
 * @author GelidElf
 *
 */
public interface ParallelPortObserver {

	public abstract void update(ParallelPortState state);
	
	public abstract void setParallelPortState(ParallelPortState state);
}
