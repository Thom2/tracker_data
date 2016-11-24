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
        		//openFile(args[0], args[1]);
        		
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
        			+ "Options: --c: disables conversion of delimeters. Default: 1 (enabled)\n"
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
	
/*
	private void openFile(String inputFile, String outputFile) {
		Path dataFilePathIn = Paths.get(System.getProperty("user.dir"), inputFile);
		Path dataFilePathOut = Paths.get(outputFile);

		Charset charset = Charset.forName("US-ASCII");
		try (BufferedReader reader = Files.newBufferedReader(dataFilePathIn, charset)) {
			try (BufferedWriter writer = Files.newBufferedWriter(dataFilePathOut, charset)) {
				
			    String line = null;
			    int lineCounter = 0;
			    
			    while ((line = reader.readLine()) != null) {
			    	System.out.print(line);
			        
			        // First two lines are header and column names
			        if (lineCounter < 2) {
			        	writer.write(line, 0, line.length());
			        	writer.newLine();
			        	
			        	System.out.println("          =>  " + line);
			        }
			        else {
			        	// Start manipulating data at third line
			        	String[] values = line.split("\\t");
			        	
			        	if (values.length == 3) {
			        		values[0] = values[0].replace(',', '.');
			        		values[1] = "0";
			        		values[2] = values[2].replace(',', '.');
			        		
			        		String newLine = String.format("%s\t%s\t%s", values[0], values[1], values[2]);
			        		
			        		writer.write(newLine, 0, newLine.length());
				        	writer.newLine();
				        	
				        	System.out.println("      =>  " + newLine);
			        	}
			        	else {
			        		System.out.println("      =>  Error: incorrect count of data fields.");
			        	}
			        }
			        
			        lineCounter++;
			        
			    }
			} catch (IOException x) {
			    System.err.format("Output file could not be created. IOException: %s%n", x);
			}
		} catch (IOException x) {
		    System.err.format("Input file not found. IOException: %s%n", x);
		}
		
	}
*/
}
