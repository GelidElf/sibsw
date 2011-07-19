package Slave.States;

public interface AutomataStateSlave{
	public abstract AutomataStateSlave asEmpty();

	public abstract AutomataStateSlave asComplete();

	public abstract AutomataStateSlave transferCBNotFull();

	public abstract AutomataStateSlave asAxisNeeded();

	public abstract AutomataStateSlave axisCBUnloadReady();

	public abstract AutomataStateSlave asGearNeeded();

	public abstract AutomataStateSlave gearCBUnloadReady();

	public abstract AutomataStateSlave robot1Idle();

	public abstract AutomataStateSlave robot1Loaded();
}
