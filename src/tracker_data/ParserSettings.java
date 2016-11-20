package tracker_data;

public class ParserSettings {
	public final String ReplaceValue;
	public final boolean ConvertDelimiter;
	
	public ParserSettings(String replaceValue, boolean convertDelimeter) {
		ReplaceValue = replaceValue;
		ConvertDelimiter = convertDelimeter;
	}
	
	public static ParserSettings create(String[] args) {
		String replaceValue = "0";
		boolean convertDelimeter = true;

		if (args.length > 2) {
			for (int i = 2; i < args.length; i += 2) {
				if (args[i].compareTo("-r") == 0 && args.length > i + 1) {
					replaceValue = args[i + 1];
				}
				else if (args[i].compareTo("-c") == 0 && args.length > i + 1) {
					convertDelimeter = getInteger(args[i + 1], 1) == 1;
				}
			}
		}

		return new ParserSettings(replaceValue, convertDelimeter);
	}
	
	private static int getInteger(String input, int defaultValue) {
		int returnValue = defaultValue;
		
		try {
			returnValue = Integer.parseInt(input);
		} catch (NumberFormatException e) {}
		
		return returnValue;
	}
}
