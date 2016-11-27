package tracker_data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class DataFile {
	private ArrayList<String[]> _dataArray = new ArrayList<String[]>();
	private boolean _isValid = false;
	
	// Loads given data file and stores content into a string array. Each
	// line in the file corresponds to an entry in an array. Each entry is
	// split by the '\t' (tab) character and stored again as string array.
	public void load(String fileName) {
		reset();
		
		Path dataFilePathIn = Paths.get(System.getProperty("user.dir"), fileName);
		Charset charset = Charset.forName("US-ASCII");
		
		try (BufferedReader reader = Files.newBufferedReader(dataFilePathIn, charset)) {
		    String line = null;
		    int lineCounter = 0;
		    
		    Logger.log("Start reading data file " + dataFilePathIn.toString());
		    
		    while ((line = reader.readLine()) != null) {
		    	Logger.log(line);
		    	
		        // First line contains data specifier
		        if (lineCounter == 0) {
		        	_dataArray.add(new String[]{line});   	
		        }
		        else {
		        	_dataArray.add(line.split("\\t"));
		        }
		        
		        _isValid = true;
		        lineCounter++;
		    }	    	
		} catch (IOException x) {
		    Logger.error(String.format("Input file not found. IOException: %s%n", x));
		}
		
	    Logger.log("End reading data file");
	}
	
	// Writes content of string array to specified file. Each entry in the array
	// creates a new line in the file.
	public void write(String fileName) {
		Path dataFilePathOut = Paths.get(System.getProperty("user.dir"), fileName);
		Charset charset = Charset.forName("US-ASCII");
		
		try (BufferedWriter writer = Files.newBufferedWriter(dataFilePathOut, charset)) {
			for (int i = 0; i < _dataArray.size(); i++) {
			
				if (0 == i) {
					writer.write(_dataArray.get(i)[0]);
					writer.newLine();
				}
				else {
					String[] line = _dataArray.get(i);
					
					if (3 == line.length) {
						String newLine = String.format("%s\t%s\t%s", line[0], line[1], line[2]);
						
		        		writer.write(newLine);
			        	writer.newLine();
					}
					else {
						Logger.error("Parser error: elements lenght is incorrect");
					}
				}
			}			
		} catch (IOException e) {
		    Logger.error(String.format("Output file could not be created. IOException: %s%n", e));
		}

	}
	
	// Processes content of data array according the parameters given at command line.
	public void processData() {	
		for (int i = 0; i < _dataArray.size(); i++) {
			
			// ignore first two lines
			if (i > 1 ) {
				String[] line = _dataArray.get(i);
				
				if (3 == line.length) {
					Logger.out(String.format("%s\t%s\t%s", line[0], line[1], line[2]));
					
					line[1] = Configuration.instance().replaceValue();
					if (Configuration.instance().convertDelimiter()) {
						line[0] = line[0].replace(',', '.');
						line[2] = line[2].replace(',', '.');
					}
					
					Logger.out(String.format("      => %s\t%s\t%s\n", line[0], line[1], line[2]));
	        	}
				else {
					Logger.error("Parser error: elements lenght is incorrect");
				}
			}
		}			
		
	}
	
	public boolean isValid() {
		return _isValid;
	}
	
	public void reset() {
		_dataArray.clear();
		_isValid = false;
	}
}
