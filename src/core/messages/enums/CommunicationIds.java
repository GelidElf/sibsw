package core.messages.enums;

import java.awt.Color;

public enum CommunicationIds {
	MASTER(Color.BLACK),
	SLAVE1(Color.GREEN),
	SLAVE2(Color.ORANGE),
	SLAVE3(Color.PINK),
	SCADA(null),
	BROADCAST(null);

	private Color consoleTextColor;

	public Color getConsoleTextcolor (){
		return consoleTextColor;
	}

	CommunicationIds (Color consoleTextColor){
		this.consoleTextColor = consoleTextColor;
	}
}
