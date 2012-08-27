package slave1;

import core.aplication.Configuration;
import core.messages.Attribute;
import core.messages.Message;
import core.messages.OfflineCommunicationManager;
import core.messages.SingleInboxCommunicationManager;
import core.messages.enums.CommunicationIds;
import core.messages.enums.CommunicationMessageType;
import core.model.AutomataContainer;
import core.model.ModelListener;
import core.sections.AssembyStation.AssemblyStationAutomata;
import core.sections.AssembyStation.AssemblyStationManager;
import core.sections.ConveyorBelt.ConveyorBeltAutomata;
import core.sections.ConveyorBelt.ConveyorBeltManager;
import core.sections.robot1.Robot1Automata;
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
		gearBelt = new ConveyorBeltAutomata(this, gearManager);
		getModel().setGearBeltModel(gearBelt.getModel());
		ConveyorBeltManager axisManager = new ConveyorBeltManager();
		axisManager.configure(10, 2);
		axisBelt = new ConveyorBeltAutomata(this, axisManager);
		getModel().setAxisBeltModel(axisBelt.getModel());
		AssemblyStationManager assemblyManager = new AssemblyStationManager();
		assemblyManager.configure(10);
		assemblyStation = new AssemblyStationAutomata(this, assemblyManager);
		getModel().setAssemblyStationModel(assemblyStation.getModel());
		robot = new Robot1Automata(this, new Robot1Model(), new OfflineCommunicationManager());
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
			getModel().getState().execute((Slave1Input) message.getInputType());
		}

	}

	private void reaccionaPorTipoDeMensaje(Message message) {
		switch (message.getType()) {
		case START:
			feedInput(Slave1Input.START, message.isUrgent());
			break;
		case NSTOP:
			feedInput(Slave1Input.NSTOP, message.isUrgent());
			break;
		case ESTOP:
			feedInput(Slave1Input.ESTOP, message.isUrgent());
			break;
		case RESTART:
			feedInput(Slave1Input.RESTART, message.isUrgent());
			break;
		case CONFIGURATION:
			for (Attribute attribute : message.getAttributes()) {
				changeConfigurationParameter(attribute);
			}
			break;
		}

	}

	private boolean debeReaccionaPorTipoEntrada(Message message) {
		return message.getType() == CommunicationMessageType.COMMAND;
	}

	@Override
	public void startCommand() {
		getCommunicationManager().initialize();
		start();
	}

	@Override
	protected void changeConfigurationParameter(Attribute attribute) {
		// TODO Auto-generated method stub

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
