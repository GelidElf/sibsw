/**
 * 
 */
package core.aplication;

import core.file.ConfigurationFileReader;

/**
 * @author gonzalo
 *
 */
public class RunnableApplication {

	public static String fileName = "conf.ini";
	public static Configuration configuration = null;
	
	public static void initialize (String[] args){
		
		configuration = new Configuration();
		//First we check that we received the correct amount of paramenters
		if ((args.length % 2) != 0)
			System.out.println("Error: Incorrect number of parameters: "+ args.length);
		else if (args.length > 1){
			int argumentIndex = 1;
			while (argumentIndex < args.length){
				argumentIndex+=processOption(args,argumentIndex);
			}
		}else if (args.length == 0){
			loadConfigurationFile();
		}
	}
	
	private static int processOption (String[] args, int position){
		String option = args[position];
		if (position >= args.length)
			System.out.println("Error: insuficcient number of parameters");
		String optionText = args[position++];
		if (option.equals("-f")){
			fileName = optionText;
			loadConfigurationFile();
			return 2;
		}
		return 1;
	}
	
	private static void loadConfigurationFile(){
		ConfigurationFileReader fileReader = new ConfigurationFileReader(fileName);
		 configuration = fileReader.readConfiguration();
	}
}
