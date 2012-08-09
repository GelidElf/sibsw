package slave1;


import core.aplication.Configuration;
import core.messages.Attribute;
import core.messages.Message;
import core.messages.SingleInboxCommunicationManager;
import core.messages.enums.CommunicationIds;
import core.model.AutomataContainer;
import core.sections.AssembyStation.ATAssemblyStation;
import core.sections.AssembyStation.AssemblyStationManager;
import core.sections.ConveyorBelt.ATConveyorBelt;
import core.sections.ConveyorBelt.ConveyorBeltManager;
import core.sections.robot1.Robot;

public class ATSlave1 extends AutomataContainer<Slave1Input>{

	private ATConveyorBelt gearBelt;
	private ATConveyorBelt axisBelt;
	private Slave1State currentState;
	private ATAssemblyStation assemblyStation;
	private Robot robot;

	public Slave1State getCurrentState() {
		return currentState;
	}


	public ATSlave1(Configuration conf){
		super(null,new SingleInboxCommunicationManager(CommunicationIds.SLAVE1,conf,new Slave1Model()));
		ConveyorBeltManager gearManager = new ConveyorBeltManager();
		gearManager.configure(10,2);
		gearBelt = new ATConveyorBelt(this, gearManager);
		ConveyorBeltManager axisManager = new ConveyorBeltManager();
		axisManager.configure(10,2);
		axisBelt = new ATConveyorBelt(this, axisManager);
		AssemblyStationManager assemblyManager = new AssemblyStationManager();
		assemblyManager.configure(10);
		assemblyStation = new ATAssemblyStation(this, assemblyManager);
	}

	//	public ATSlave1(){
	//		System.out.println("lanzo simulación en robot");
	//		/* Iniciamos robot*/
	//		robot = new Robot();
	//
	//		assemblyStation = new AssemblyStation();
	//		assemblyStation.setEmpty(true);
	//		assemblyStation.setGearNeeded(true);
	//		assemblyStation.setAxisNeeded(true);
	//		assemblyStation.start();
	//
	//		ParallelPortState state = new ParallelPortState();
	//		gearBelt = new ATConveyorBelt(this,new ConveyorBeltManager());
	//		gearBelt.getManager().setState(state);
	//
	//
	//		ParallelPortState state2 = new ParallelPortState();
	//		axisBelt = new ATConveyorBelt(this,new ConveyorBeltManager());
	//		axisBelt.getManager().setState(state2);
	//		axisBelt.start();
	//
	//	}


	/*
	 * Recibir config del buzón,
	 */
	/*@Override
	public void run(){
		setInitialSettings("Mensaje(s) leido del buzón con los parámetros");
		gearBelt.start();
		robot.start();
		setCurrentState(new Idle());
		while(true){
			currentState.execute(this);
			try {
				sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}*/

	public void setInitialSettings(String settings){

		//TODO: po hacerlo >.<
		//robot.setSpeed......
		//gearBelt.setLength,speed, capacity....
		//AS.setAssemblyDelay....

	}

	public ATConveyorBelt getGearBelt() {
		return gearBelt;
	}


	public void setGearBelt(ATConveyorBelt gearBelt) {
		this.gearBelt = gearBelt;
	}


	public ATConveyorBelt getAxisBelt() {
		return axisBelt;
	}


	public void setAxisBelt(ATConveyorBelt axisBelt) {
		this.axisBelt = axisBelt;
	}


	public ATAssemblyStation getAssemblyStation() {
		return assemblyStation;
	}


	public void setAssemblyStation(ATAssemblyStation assemblyStation) {
		this.assemblyStation = assemblyStation;
	}


	public Robot getRobot() {
		return robot;
	}


	public void setRobot(Robot robot) {
		this.robot = robot;
	}


	@Override
	protected void consume(Message message) {
		switch ((Slave1Input)message.getInputType()) {
		case START:
			currentState.execute((Slave1Input)message.getInputType());
			break;

		case EMPTY_TRANSFER_CB:
			
			break;
			
		default:
			break;
		}
		
	}


	@Override
	protected void startCommand() {
		currentState = new Slave1State(this);
		getCommunicationManager().initialize();
		start();
	}


	@Override
	protected void changeConfigurationParameter(Attribute attribute) {
		// TODO Auto-generated method stub
		
	}

	//	public static void main (String args[]){
	//		ATSlave1 slave = new ATSlave1();
	//		System.out.println("ARRRRRRRANCO!");
	//		slave.start();
	//	}
}
