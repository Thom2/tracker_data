package tracker_data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

	private static String VERSION ="0.1";
	
	public static void main(String[] args) {

		System.out.println("Tracker Data Tool v." + VERSION + "\n\n");
		
        // this program requires two 
        // arguments on the command line 
        if (args.length == 2) {
        	if (args[0].compareToIgnoreCase(args[1]) != 0) {
        		openFile(args[0], args[1]);
        	}
        	else {
        		System.out.println("Error: input and output file cannot be the same");
        	}
        }
        else {
        	System.out.println("Arguments missing.\n\n"
        			+ "Usage: tracker_data.jar <input_file> <output_file>\n\n"
        			+ "Example: java -jar tracker_data.jar input.dat output.dat");
        }
	}

	private static void openFile(String inputFile, String outputFile) {
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

}
