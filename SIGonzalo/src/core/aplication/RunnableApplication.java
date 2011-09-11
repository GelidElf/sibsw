/**
 * 
 */
package core.aplication;

import core.file.ConfigurationFileReader;
import core.utilities.args.Args;
import core.utilities.args.ArgsException;

/**
 * @author gonzalo
 *
 */
public class RunnableApplication {

	public static Configuration configuration = null;
	private static final String usage = "a,f*";
	
	public static void initialize (String[] args,String configurationFilePath){
		configuration = new Configuration();
		setConfigurationFileName(configurationFilePath);
		processArguments(args);
	}
	
	private static void processArguments(String [] args){
		try { 
			Args arg = new Args(usage, args); 
			boolean autoDiscovery = arg.getBoolean('a');
			if (autoDiscovery){
				configuration.autoDiscovery = "yes";
				System.out.println("Using Autodiscovery.");
			}else{
				String confFileName = arg.getString('f');
				if(!confFileName.equals("")){
					configuration.configurationFileName = confFileName;
				}
				System.out.println("Loading configuration from "+configuration.configurationFileName);	
				loadConfigurationFile();
			}
		} catch (ArgsException e) { 
			System.out.printf("Argument error: %s\n", e.errorMessage()); 
		} 
	}
	
	private static void loadConfigurationFile(){
		ConfigurationFileReader fileReader = new ConfigurationFileReader(configuration.configurationFileName);
		 configuration = fileReader.readConfiguration();
	}
	
	protected static void setConfigurationFileName(String confFileName){
		configuration.configurationFileName = confFileName;
	}
	
}
