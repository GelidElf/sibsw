package slave1;

import core.aplication.Configuration;
import core.messages.Attribute;
import core.messages.Message;
import core.messages.OfflineCommunicationManager;
import core.messages.SingleInboxCommunicationManager;
import core.messages.enums.CommunicationIds;
import core.messages.enums.CommunicationMessageType;
import core.messages.enums.ConfigurationParameters;
import core.model.AutomataContainer;
import core.model.ModelListener;
import core.sections.AssembyStation.AssemblyStationAutomata;
import core.sections.AssembyStation.AssemblyStationManager;
import core.sections.ConveyorBelt.ConveyorBeltAutomata;
import core.sections.ConveyorBelt.ConveyorBeltManager;
import core.sections.ParallelPort.Utils.ParallelPortException;
import core.sections.robot1.Robot1Automata;
import core.sections.robot1.Robot1Manager;
import core.sections.robot1.Robot1Model;

public class Slave1Automata extends AutomataContainer<Slave1Input, Slave1State, Slave1Model> implements ModelListener {

	private ConveyorBeltAutomata gearBelt;
	private ConveyorBeltAutomata axisBelt;
	private AssemblyStationAutomata assemblyStation;
	private Robot1Automata robot;

	public Slave1State getCurrentState() {
		return getModel().getState();
	}

	public Slave1Automata(Configuration conf) {
		super(null, new Slave1Model(), new SingleInboxCommunicationManager(CommunicationIds.SLAVE1, conf));
		ConveyorBeltManager gearManager = new ConveyorBeltManager();
		gearManager.configure(10, 2);
		gearBelt = new ConveyorBeltAutomata(this, gearManager, Slave1Input.MOVE_GEAR);
		getModel().setGearBeltModel(gearBelt.getModel());
		gearBelt.enableAutoFeed();
		ConveyorBeltManager axisManager = new ConveyorBeltManager();
		axisManager.configure(10, 2);
		axisBelt = new ConveyorBeltAutomata(this, axisManager, Slave1Input.MOVE_AXIS);
		getModel().setAxisBeltModel(axisBelt.getModel());
		axisBelt.enableAutoFeed();
		AssemblyStationManager assemblyManager = new AssemblyStationManager();
		assemblyManager.configure(10);
		assemblyStation = new AssemblyStationAutomata(this, assemblyManager);
		getModel().setAssemblyStationModel(assemblyStation.getModel());
		robot = new Robot1Automata(this, new Robot1Model(), new OfflineCommunicationManager());
		robot.getModel().addListener(this);
		getModel().setRobot1Model(robot.getModel());
		getModel().setAutomata(this);
		getModel().addListener(this);
	}

	public void setInitialSettings(String settings) {

		//TODO: po hacerlo >.<
		//robot.setSpeed......
		//gearBelt.setLength,speed, capacity....
		//AS.setAssemblyDelay....

	}

	public ConveyorBeltAutomata getGearBelt() {
		return gearBelt;
	}

	public void setGearBelt(ConveyorBeltAutomata gearBelt) {
		this.gearBelt = gearBelt;
	}

	public ConveyorBeltAutomata getAxisBelt() {
		return axisBelt;
	}

	public void setAxisBelt(ConveyorBeltAutomata axisBelt) {
		this.axisBelt = axisBelt;
	}

	public AssemblyStationAutomata getAssemblyStation() {
		return assemblyStation;
	}

	public void setAssemblyStation(AssemblyStationAutomata assemblyStation) {
		this.assemblyStation = assemblyStation;
	}

	@Override
	protected void consume(Message message) {
		reaccionaPorTipoDeMensaje(message);
		if (debeReaccionaPorTipoEntrada(message)) {
			Slave1Input input = (Slave1Input) message.getInputType();
			//If input is a shortcut, try it here
			switch (input) {
			case AUTO_FEED_AXIS_OFF:
				axisBelt.disableAutoFeed();
				message.consumeMessage();
				break;
			case AUTO_FEED_AXIS_ON:
				axisBelt.enableAutoFeed();
				message.consumeMessage();
				break;
			case AUTO_FEED_GEAR_OFF:
				gearBelt.disableAutoFeed();
				message.consumeMessage();
				break;
			case AUTO_FEED_GEAR_ON:
				gearBelt.enableAutoFeed();
				message.consumeMessage();
				break;
			default:
				// Input is a normal state command, use the state.
				message.setConsumed(getModel().getState().execute(input));
				break;
			}
		}

	}

	private void reaccionaPorTipoDeMensaje(Message message) {
		message.consumeMessage();
		switch (message.getType()) {
		case START:
			message.setConsumed(getModel().getState().execute(Slave1Input.START));
			break;
		case NSTOP:
			message.setConsumed(getModel().getState().execute(Slave1Input.NSTOP));
			break;
		case ESTOP:
			message.setConsumed(getModel().getState().execute(Slave1Input.ESTOP));
			break;
		case RESTART:
			message.setConsumed(getModel().getState().execute(Slave1Input.RESTART));
			break;
		case CONFIGURATION:
			for (Attribute attribute : message.getAttributes()) {
				changeConfigurationParameter(attribute);
			}
			break;
		default:
			message.didNotConsumeMessage();
			break;
		}

	}

	private boolean debeReaccionaPorTipoEntrada(Message message) {
		return message.getType() == CommunicationMessageType.COMMAND;
	}

	@Override
	public void startCommand() {
		getCommunicationManager().initialize();
		getGearBelt().startCommand();
		getAxisBelt().startCommand();
		getAssemblyStation().startCommand();
		getRobot().startCommand();
		this.start();
	}

	@Override
	protected void changeConfigurationParameter(Attribute attribute) {
		
		/* Falta CB_AXIS_LENGTH,
	CB_GEARS_LENGTH !!!!!!!!!!!!!!!!1*/
		
		ConfigurationParameters parameter = ConfigurationParameters.getEnum(attribute.getName());
		if (parameter != null) {
			try {
				switch (parameter) {
				case CB_AXIS_CAPACITY:
					axisBelt.getManager().setBitGroupValue(ConveyorBeltManager.CAPACITY, (Integer) attribute.getValue());
					break;
				case CB_AXIS_SPEED:
					axisBelt.getManager().setBitGroupValue(ConveyorBeltManager.SPEED, (Integer) attribute.getValue());
					break;
				case CB_GEARS_CAPACITY:
					gearBelt.getManager().setBitGroupValue(ConveyorBeltManager.CAPACITY, (Integer) attribute.getValue());
					break;
				case CB_GEARS_SPEED:
					gearBelt.getManager().setBitGroupValue(ConveyorBeltManager.SPEED, (Integer) attribute.getValue());
					break;
				case ACTIVATION_TIME_AS:
					assemblyStation.getManager().setBitGroupValue(AssemblyStationManager.ASSEMBLING_TIME, (Integer) attribute.getValue());
					break;
				case PICK_TIME_AXIS_GEAR:
					robot.getManager().setBitGroupValue(Robot1Manager.TIME_TO_AXIS_GEAR, (Integer) attribute.getValue());
					break;
				case TRANSPORT_PLACE_TIME_AXIS_GEAR:
					robot.getManager().setBitGroupValue(Robot1Manager.TIME_TO_ASSEMBLED, (Integer) attribute.getValue());
					break;
				case TRANSPORT_PLACE_TIME_ASSEMBLED:
					robot.getManager().setBitGroupValue(Robot1Manager.TIME_TO_TRANSPORT, (Integer) attribute.getValue());
					break;
				default:
					break;
				}
			} catch (ParallelPortException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void updateOnModelChange() {
		sendMessage(new Message("MODEL_UPDATE", CommunicationIds.MASTER, false, CommunicationMessageType.STATUS_UPDATE, null));
	}

	public void setRobot(Robot1Automata robot) {
		this.robot = robot;
	}

	public Robot1Automata getRobot() {
		return robot;
	}
}
