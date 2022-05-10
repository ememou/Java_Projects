import java.lang.*;

public class UnsupportedFileFormatException extends Exception{
	
	String msg;
	
	public UnsupportedFileFormatException(){}
	
	public UnsupportedFileFormatException(String msg){
		this.msg=msg;
	}
}
