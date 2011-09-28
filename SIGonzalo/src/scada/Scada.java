package scada;

import java.lang.reflect.Field;

import core.aplication.ApplicationConfiguration;
import core.aplication.Configuration;
import core.file.ApplicationConfigurationFileReader;
import core.messages.CommunicationManager;
import core.messages.Message;
import core.messages.SingleInboxCommunicationManager;

public class Scada implements Runnable{

	private CommunicationManager commManager;
	private ApplicationConfiguration appConf;
	
	public Scada(Configuration conf) {
		commManager = new SingleInboxCommunicationManager("SCADA", conf);
		ApplicationConfigurationFileReader fileReader = new ApplicationConfigurationFileReader("ApplicationConfiguration.ini");
		appConf = fileReader.readConfiguration();
		Message message = createConfigurationMessage();
//		commManager.sendMessage(message);
	}

	private Message createConfigurationMessage() {
		Message message = new Message("SCADA.CONF", null, true);
		Field[] configurationOptions = ApplicationConfiguration.class.getFields();
		for (Field f:configurationOptions){
			String parameter = null;
			parameter = f.getName();
			try {
				String value = null;
				value = (String) f.get(appConf);
				if (parameter != null)
					message.addAttribute(parameter, value);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return message;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
