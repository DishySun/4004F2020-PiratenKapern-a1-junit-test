package game;

public class ChestException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errMsg;
	private int errCode;
	
	public ChestException(String msg, int code) {
		this.errMsg = msg;
		this.errCode = code;
		//1 - null chest
		//2 - invalid index size
		//3 - invalid index, out of bound
		//4 - moving skulls
	}
	
	public String getMsg() {return errMsg;}
	public int getCode() {return errCode;}
}
