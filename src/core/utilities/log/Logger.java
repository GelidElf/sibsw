package core.utilities.log;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Helper class to register several loggers (file, standar output)
 * @author gonzalo
 *
 */
public class Logger {

	public static CopyOnWriteArrayList<LoggerListener> listeners;
	static {
		listeners = new CopyOnWriteArrayList<LoggerListener>();
		listeners.add(new StandardOutputLoggerListener());
	}

	public static void registerListener(LoggerListener listener) {
		listeners.add(listener);
	}


	public static void unregisterListener(LoggerListener listener) {
		listeners.remove(listener);
	}

	public static void println(String text) {
		for (LoggerListener listener : listeners) {
			listener.println(Thread.currentThread().getName() + ":>" + text);
		}
	}

	public static void println() {
		println("");
	}

	public static void print(String text) {
		for (LoggerListener listener : listeners) {
			listener.print(Thread.currentThread().getName() + ":>" + text);
		}
	}
}
