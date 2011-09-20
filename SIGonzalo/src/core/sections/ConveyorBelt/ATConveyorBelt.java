package core.sections.ConveyorBelt;
import java.util.Random;

import core.messages.CommunicationManager;
import core.messages.Message;
import core.messages.MessageFactory.SlaveAutomaton1MessageFactory;
import core.sections.ConveyorBelt.States.AutomataStateCB;
import core.sections.ConveyorBelt.States.Idle;
import core.sections.ParallelPort.*;
import core.sections.ParallelPort.Utils.ParallelPortException;


public class ATConveyorBelt extends Thread implements ParallelPortObserver{

	private ConveyorBeltManager _manager = null;
	private AutomataStateCB currentState = null;
	private Random rand = new Random(System.currentTimeMillis());
	private CommunicationManager commManager= null;
	private ConveyorBeltSimulator cbs;


	public ATConveyorBelt(){
		_manager = new ConveyorBeltManager();
		//TODO: hay que quitar este commManager
		//	commManager = new CommunicationManager(false,"ATCB");
	}

	@Override
	public void update(ParallelPortState state) {
		_manager.setState(state);		
	}

	public void run(){

		//Message mes = commManager.getInboxByName("unknown").getMessage();
		String speed = "5";//mes.getAttributeValue(SlaveAutomaton1MessageFactory.SPEED);
		String capacity = "5";//mes.getAttributeValue(SlaveAutomaton1MessageFactory.CAPACITY);
		try {
			System.out.println("Pongo valores...");
			_manager.setValueByName(ConveyorBeltManager.VELOCITY, Integer.parseInt(speed));
			_manager.setValueByName(ConveyorBeltManager.CAPACITY, Integer.parseInt(capacity));
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParallelPortException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		cbs = new ConveyorBeltSimulator(_manager);
		cbs.setCapacity(5);
		cbs.start();
		//cbs.run();
		System.out.println("despues de run");
		while(true){
			if(currentState == null){
				currentState = Idle.getInstance();
				try {
					_manager.setBitGroupValue(ConveyorBeltManager.RUNNING,1);
				} catch (ParallelPortException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				if(_manager.getValueByName(ConveyorBeltManager.SENSOR_INITIAL) == 0){
					if (rand.nextBoolean()){
						_manager.setValueByName(ConveyorBeltManager.SENSOR_INITIAL, 1);
						//	System.out.println("sensor = 1");
					}else{
						try {
							/* NOS TENEMOS QUE DORMIR EL MISMO TIEMPO QUE EL SIMULADOR, si no, en cuanto el simulador se duerma,
							 * el random hace 1000000 intentos y acaba por salir true. Esto nos lleva a que SIEMPRE soltamos un
							 * gear/axis, o lo que es lo mismo, la cinta siempre está llena
							 */
							sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				if(isReady()){
					//quitar esto
					//System.out.println("Estoy listo!");
					/*try {
						//sleep(2000);						
						//_manager.setBitGroupValue(ConveyorBeltManager.SENSOR_FINISH, 0);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/

				}
			} catch (ParallelPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			currentState = currentState.estop();
		}
	}

	public void setupParametersFromMessage(Message mes) {
		String speed = mes.getAttributeValue(SlaveAutomaton1MessageFactory.SPEED);
		String capacity = mes.getAttributeValue(SlaveAutomaton1MessageFactory.CAPACITY);
		try {
			_manager.setValueByName(ConveyorBeltManager.VELOCITY, Integer.parseInt(speed));
			_manager.setValueByName(ConveyorBeltManager.CAPACITY, Integer.parseInt(capacity));
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParallelPortException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void piecePicked(){
		try {
			_manager.setBitGroupValue(ConveyorBeltManager.SENSOR_FINISH, 0);
		} catch (ParallelPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isReady(){
		boolean op = false;
		try {
			if(_manager.getValueByName(ConveyorBeltManager.SENSOR_FINISH)==1){
				op = true;
			}
		} catch (ParallelPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return op;
	}

	public ConveyorBeltManager getManager(){
		return _manager;
	}

	public static void initializeAndConfigure(){

	}

	public static void main (String[] args){
		ParallelPortState state = new ParallelPortState();
		ATConveyorBelt atcb = new ATConveyorBelt();
		atcb._manager.setState(state);
		ConveyorBeltSimulator cbs = new ConveyorBeltSimulator(atcb.getManager());
		System.out.println("hilos lanzados1");
		cbs.getManager().setState(state);
		System.out.println("hilos lanzados2");
		atcb.start();
		System.out.println("hilos lanzados3");
		cbs.start();
	}


	@Override
	public void setParallelPortState(ParallelPortState state) {
		// TODO Auto-generated method stub

	}
}