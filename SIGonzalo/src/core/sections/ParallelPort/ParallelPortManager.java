package core.sections.ParallelPort;

import java.util.ArrayList;
import java.util.Hashtable;

import core.sections.ParallelPort.Utils.ParallelPortException;


/**
 * Basic Manager for the parallel port, must be extended so that the constructor of the new class configures the names 
 * and groups according to the specific needs of each connection and implementation of the system.
 * Implementation uses 2 managers for each simulated connection, all interaction with state must be done through a manager.
 * 
 * Terminology:
 * 			System: Collection of objects that implement the simulation of a control automaton or of a section of the
 * 		assembly line that we wish to communicate.
 * 			Manager: An object of this class or any other class extended from this one.
 * 
 * How it works: 
 * 			A connection is maintained using 2 instances of the same manager. Refer to previous description for specifics.
 * 			Each instance must belong to one System and each System must register as observer of the other manager.
 * 			On update, the object feeds the new state to it's manager and performs the operations required.
 * 			The Manager doesn't store several modifications, so after each one the update method must be invoked.
 * 
 * 			It is recommended to perform one modification at a time, in case no specific communication is required for the system, 
 * 			setting up a group to force synchronization between systems would be interesting, and used as often as needed.
 * 
 * @author GelidElf
 *
 */
public class ParallelPortManager {

	/**
	 * Bits of the connection
	 */
	private ParallelPortState _state = null;
	/**
	 * Observers for the Managers
	 */
	private ArrayList<ParallelPortObserver> _observers = null;
	/**
	 * Contains the assigned groups of pin so that names can be used and connector pins can be assigned to groups
	 */
	private ParallelPortPinGroup[] _connectorOwners = null;
	/**
	 * Quick access to the pin groups acrdingto their names
	 */
	private Hashtable<String, ParallelPortPinGroup> _connectorNames = null;
	
	/**
	 * Protected constructor so that sons can access it
	 */
	protected ParallelPortManager(){}
	
	/**
	 * Accesses the current state instance
	 * @return
	 */
	public ParallelPortState getCurrentState(){
		return _state;
	}

	
	/**
	 * Setter for the state, used when received a modified state wich we want to treat.
	 * @param state
	 */
	public synchronized void setState(ParallelPortState state){
		_state = state;
	}

	/**
	 * clears the current value stored in state
	 */
	public synchronized void clearState(){
		_state = null;
	}
	
	/**
	 * Registers a new observer
	 * @param observer
	 * @return
	 */
	public synchronized boolean registerObserver (ParallelPortObserver observer){
		if (_observers == null)
			_observers = new ArrayList<ParallelPortObserver>();
		return _observers.add(observer);
	}
	
	/**
	 * Sets up a new group of bit positions
	 * @param name String that identifies the group
	 * @param start first pin of the group
	 * @param end last pin of the group
	 * @throws IndexOutOfBoundsException if the pinout is incorrect
	 */
	public synchronized void setBitGroup (String name, int start, int end) throws ParallelPortException {
		ParallelPortPinGroup pinGroup = new ParallelPortPinGroup(this,name,start,end);
		if (_connectorNames == null)
			_connectorNames = new Hashtable<String, ParallelPortPinGroup>();
		if (_connectorOwners == null)
			_connectorOwners = new ParallelPortPinGroup[16];
		if ((start < 0) || (end < 0) || (start > 16) || (end > 16))
			throw new IndexOutOfBoundsException("Index selected for pin group is out of bounds");
		if ((name == null) || name.equals(""))
			throw new ParallelPortException("Name for pin group is empty or null");
		_connectorNames.put(name,pinGroup);
		for (int i = start; i<=end; i++){
			if (_connectorOwners[i]!=null)
				throw new ParallelPortException("Position assigned to group already assigned");
			_connectorOwners[i]=pinGroup;
		}
	}
	
	/**
	 * Informs all observers that there has been a change in the state and returns a cloned instance of the state.
	 * As we souldn't have more than 1 observer per manager, we don't send a new instance to each
	 * TODO if neccesary send a new instance to each.
	 */
	public synchronized void update(){
		ParallelPortState pps = this.getCurrentState();
		for (ParallelPortObserver obs: _observers)
			obs.update(pps);
	}
	
	public synchronized void setBit(int position){
		_state.setValue(position, true);
	}
	
	public synchronized void resetBit(int position){
		_state.setValue(position, false);
	}
	
	public synchronized void setBitTo(int position, boolean value){
		_state.setValue(position, value);
	}
	
	public synchronized void setBitTo(int position, int value){
		_state.setValue(position, (value==1));
	}
	
	/**
	 * Returns 1 if the the array holds in the position true, 0 otherwise
	 * @param position the position which value we wish to return in integer format
	 * @return 1 if true, 0 otherwise
	 */
	public synchronized int getIntValue(int position){
		if (_state.getValue(position))
			return 1;
		else
			return 0;
	}
	
	public synchronized void setBitGroupValue(String name, int value) throws ParallelPortException{
		_connectorNames.get(name).setValue(value);
	}
	
	public synchronized int getBitGroupValue(ParallelPortState state,String name){
		return _connectorNames.get(name).intValue();
	}
	
	public synchronized String getModifiedPositionBitGroupName(){
		if (_connectorOwners[_state.getModifiedBit()]!=null)
			return _connectorOwners[_state.getModifiedBit()].getName();
		else
			return null;
	}
	
	/**
	 * Returns the position that was modified, if a group was modified then the position returned is the first position of the group
	 * @return te position that was modified
	 */
	public synchronized int getModifiedBitPosition(){
		return _state.getModifiedBit();
	}
	
	/**
	 * Sets to 0 the value of the group groupName
	 * @param groupName the name of the group
	 * @throws ParallelPortException if groupName is null or empty
	 */
	public synchronized void resetBitGroupValue(String groupName) throws ParallelPortException{
		if ((groupName == null) || groupName.equals(""))
			throw new ParallelPortException("Name for pin group is empty or null");
		try {
			_connectorNames.get(groupName).setValue(0);
		} catch (ParallelPortException e) {
			// TODO Do nothing, 0 is never going to be too big
		}
	}
	
	/**
	 * Sets the value of the group named groupName to value
	 * @param groupName the name of the group
	 * @param value the value stored in the pins assigned to that group
	 * @throws ParallelPortException if groupName is null or empty of if the value is to big for the group
	 */
	public synchronized void setValueByName(String groupName, int value) throws ParallelPortException{
		if ((groupName == null) || groupName.equals(""))
			throw new ParallelPortException("Name for pin group is empty or null");
		_connectorNames.get(groupName).setValue(value);
	}
	
	/**
	 * Returns the value of the assigned pins of the group named groupName
	 * @param groupName the name of the group
	 * @return the value
	 * @throws ParallelPortException if groupName is null or empty or if there is no group with that name
	 */
	public synchronized int getValueByName(String groupName) throws ParallelPortException{
		if ((groupName == null) || groupName.equals(""))
			throw new ParallelPortException("Name for pin group is empty or null");
		ParallelPortPinGroup pinGroup = _connectorNames.get(groupName);
		if (pinGroup == null)
			throw new ParallelPortException("There is no group with that name stored");
		else
			return pinGroup.intValue();
	}
	
	public synchronized String getModifiedGroupName(){
		if ((_state.getModifiedBit() < 0) &&(_state.getModifiedBit() >= 16))
			return null;
		else
			return _connectorOwners[_state.getModifiedBit()].getName();
	}
	
	public synchronized int getModifedValue(){
		if (_connectorOwners[_state.getModifiedBit()]==null)
			return getIntValue(_state.getModifiedBit());
		else
			return _connectorOwners[_state.getModifiedBit()].intValue();
	}
	
	
}
