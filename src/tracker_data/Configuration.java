package tracker_data;

import java.util.HashMap;
import java.util.Map;

//Contains the settings for the application, realized as Singleton
public class Configuration {
	// singleton
	private static final Configuration _instance = new Configuration();
	
	private Configuration() {}
	
	// Returns single instance of class Configuration
	public static Configuration instance() {
		return _instance;
	}


	// Default configuration parameters
	private boolean _isLoggingEnabled = false;
	private String _replaceValue = "0";
	private boolean _convertDelimiter = true;
	
	// Constants
	public static final String LOG_FILE_NAME = "tracker_data.log";
	
	// Returns true if logging is enabled, otherwise false.
	public boolean isLoggingEnabled() {
		return _isLoggingEnabled;
	}

	// Returns value the second column in the file is replaced with.
	public String replaceValue() {
		return _replaceValue;
	}

	// Returns true of the floating point delimiter is replaced, otherwise false.
	public boolean convertDelimiter() {
		return _convertDelimiter;
	}
	
	
	// Create configuration from specified arguments
	public void initialize(String[] configParams) {	
		Map<String, String> params = parseArgs(configParams);
		
		if (params.containsKey("l")) {
			_isLoggingEnabled = true;
		}
		
		if (params.containsKey("r")) {
			_replaceValue = params.get("r");
		}
		
		if (params.containsKey("c")) {
			_convertDelimiter = false;
		}
	}
	
	// Parse command line argument parameters
	private Map<String, String> parseArgs(String[] args) {
		Map<String, String> params = new HashMap<>();

		// ignore first two arguments which are input and output file
	    for (int i = 2; i < args.length; i++) {
	    	
	    	if (args[i].charAt(0) == '-') {
	    		
	            if (args[i].length() < 2)
	                throw new IllegalArgumentException("Not a valid argument: " + args[i]);
	            
	            if (args[i].charAt(1) == '-') {
	            	
	                if (args[i].length() < 3)
	                    throw new IllegalArgumentException("Not a valid argument: " + args[i]);
	                
	                // --option
	                params.put(args[i].substring(2),  "");
	            }
	            else { 
		            if (args.length - 1 == i)
	                    throw new IllegalArgumentException("Expected arg after: " + args[i]);
		            
		            // -option + value
		            params.put(args[i].substring(1),  args[i + 1]);
		            i++;
	            }
	    	}
	    	else {
	    		throw new IllegalArgumentException("Optional arguments must be preceded with '-': " + args[i]);
	    	}
	    }
    	return params;
	}
}
