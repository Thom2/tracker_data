package tracker_data;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	private static BufferedWriter _logWriter = null;
	// Avoid repeating creation of logfile if failed once
	private static boolean _LogFileCreationFailed = false;
	// Date time format for log outputs
	private static SimpleDateFormat _dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	// Writes log output to console
	public static void out(String text) {	
		System.out.print(text);
	}
	
	// Writes log output into file
	public static void log(String text) {
		if (GeneralSettings.IsLoggingEnabled) {
			if (null == _logWriter && !_LogFileCreationFailed) {
				createLogFile();
			}
			
			if (null != _logWriter) {

				try {
					_logWriter.write(String.format("%s  - %s", getCurrentTimeStamp(), text));
					_logWriter.newLine();
				} catch (IOException e) {
					System.err.format("Error writing log file. IOException: %s%n", e);
					e.printStackTrace();
				}
				
				Logger.flush();
			}
		}
	}
	
	public static void error(String text) {
		System.err.println(text);
		log(text);
	}
	
	public static void flush() {
		if (null != _logWriter) {
			try {
				_logWriter.flush();
			} catch (IOException e) {
				Logger.error(String.format("Error flushing file. IOException: %s%n", e));
				e.printStackTrace();
			}
		}
	}
	
	private static void createLogFile() {
		Path logFilePath = Paths.get(System.getProperty("user.dir"), GeneralSettings.LOG_FILE_NAME);
		Charset charset = Charset.forName("US-ASCII");
		
		 try {
			_logWriter = Files.newBufferedWriter(logFilePath, charset);
		} catch (IOException e) {
			_LogFileCreationFailed = true;
			
			System.err.format("Log file could not be created. IOException: %s%n", e);
			e.printStackTrace();
		}		
	}
	
	private static String getCurrentTimeStamp() {
	    return _dateTimeFormat.format(new Date());
	}
}
