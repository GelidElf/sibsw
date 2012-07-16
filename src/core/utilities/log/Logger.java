package core.utilities.log;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class to register several loggers (file, standar output)
 * @author gonzalo
 *
 */
public class Logger {

	public static List<LoggerListener> listeners; 
	static{
		listeners = new ArrayList<LoggerListener>(); 
		listeners.add(new StandardOutputLoggerListener());
	}
	
	public static void  registerListener (LoggerListener listener){
		listeners.add(listener);
	}
	
	public static void println(String text){
		for (LoggerListener listener:listeners){
			listener.println(text);
		}
	}
	
}
