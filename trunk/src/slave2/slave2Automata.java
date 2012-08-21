package slave2;


import slave2.States.AutomataStateSlave;
import slave2.States.Idle;
import core.aplication.Configuration;
import core.messages.Attribute;
import core.messages.Message;
import core.messages.SingleInboxCommunicationManager;
import core.messages.enums.CommunicationIds;
import core.model.AutomataContainer;
import core.sections.AssembyStation.ATAssemblyStation;
import core.sections.ConveyorBelt.ATConveyorBelt;
import core.sections.robot1.Robot;

public class slave2Automata extends AutomataContainer<Slave2Input,Slave2Model>{

	private ATConveyorBelt gearBelt;
	private ATConveyorBelt axisBelt;
	private AutomataStateSlave currentState;
	private ATAssemblyStation assemblyStation;
	private core.sections.robot1.Robot robot;

	public AutomataStateSlave getCurrentState() {
		return currentState;
	}


	public slave2Automata(Configuration conf){
		super(null,new Slave2Model(),new SingleInboxCommunicationManager(CommunicationIds.SLAVE2,conf));
		//currentState = (Slave3State) Slave3State.createState("Idle", currentState);
	}

	//	public ATSlave1(){
	//		Logger.println("lanzo simulación en robot");
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
	public void setCurrentState(AutomataStateSlave state){
		if((currentState==null) || (state == null)){
			currentState = new Idle();
		}else{
			currentState = state;
		}
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
	public void startCommand() {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void changeConfigurationParameter(Attribute attribute) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void consume(Message currentMessage) {
		// TODO Auto-generated method stub
		
	}

	//	public static void main (String args[]){
	//		ATSlave1 slave = new ATSlave1();
	//		Logger.println("ARRRRRRRANCO!");
	//		slave.start();
	//	}
}
