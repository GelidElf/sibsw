package core.sections.ParallelPort;

/**
 * Class that contains the methods to create instances of the different managers needed
 * TODO Once all managers are created as individual classes move the constructoin methods here.
 * @author GelidElf
 *
 */
@Deprecated
public class ParallelPortConstructor {

	/*
	 * Example method for creating specific managers
	 */
	public static ParallelPortManager createConveyorBeltManager() {
		ParallelPortManager manager = new ParallelPortManager();

		return manager;
	}
}
