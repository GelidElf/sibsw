package core.utilities.log;

public class StandardOutputLoggerListener implements LoggerListener {

	@Override
	public void println(String text) {
		System.out.println(text);
	}

}
