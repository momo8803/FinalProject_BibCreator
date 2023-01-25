package basisClass;

public class FileInvalidException extends Exception{

	public FileInvalidException() {
		
		super("Error: Input file cannot be parsed due to missing information (i.e. month={}, title={}, etc.)");
		// TODO Auto-generated constructor stub
	}

	public FileInvalidException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
}
