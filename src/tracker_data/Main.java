package tracker_data;

public class Main {
	
	private final String VERSION = "0.2";
	
	public static void main(String[] args) {
		new Main().execute(args);
	}
	
	
	private void execute(String[] args) {	
		Logger.out("Tracker Data Tool v." + VERSION + "\n\n");
		
        // this program requires at least two 
        // arguments on the command line 
        if (args.length >= 2) {
        	if (args[0].compareToIgnoreCase(args[1]) != 0) {       		
        		// Parse command line for optional values
        		try {
        			Configuration.instance().initialize(args);
        	    		
        			start(args[0], args[1]);
        		} catch (IllegalArgumentException e) {
        			Logger.error(String.format("Error in arguments list. IllegalArgumentException: %s%n", e));
        		}
        	}
        	else {
        		Logger.out("Error: input and output file cannot be the same");
        	}
        }
        else {
        	Logger.out("Arguments missing.\n\n"
        			+ "Usage: tracker_data.jar <input_file> <output_file> [options]\n\n"
        			+ "Example: java -jar tracker_data.jar input.dat output.dat\n\n"
        			+ "Options: --c: disables conversion of delimeters. Default: enabled\n"
        			+ "         -r [value]: Replaces values of second column with specified value. Default: 0\n"
        			+ "         --l: enables logging");
        }
	}
		
	private void start(String inFile, String outFile) {
		DataFile dataFile = new DataFile();
		
		dataFile.load(inFile);
		
		if (dataFile.isValid()) {
			dataFile.processData();
			dataFile.write(outFile);
		}
	}
}
