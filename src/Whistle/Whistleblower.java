package Whistle;

import java.io.IOException;
import java.io.PrintStream;

/**
 * Whistle Baby whistle Baby... oh one moment :D i mean this class is for something like logging!
 * @author Tajoa
 *
 */
public class Whistleblower {
	
	public static Whistleblower instance;
	/**
	 * <pre>
	 *		(Level of whistle request)
	 * 		ERROR	INFO	ALL	E-Lean
	 *	Error	x	/	x	  /
	 *	INFO  	x	x	x	  /
	 *	All  	x	x	x	  x
	 *	E-Lean  x	/	x	  x
	 * 
	 * </pre>
	 */
	private final boolean[][] matrix = {{true,false,true,false},{true,true,true,false},{true,true,true,true},{true,false,true,true}};

	private WhistleLevel level; 
	private PrintStream output;
	
	/**
	 * Singleton implementation
	 * @return
	 */
	public static Whistleblower getInstance(){
		if(instance == null) instance = new Whistleblower();
		return instance;
	}
	
	/**
	 * Returns true if <code>level</code> will be whistlet at this time.
	 * @param level
	 */
	public boolean doIWhistle(WhistleLevel level){
		return this.getMatrix()[this.getLevel().ordinal()][level.ordinal()];
	}
	
	/**
	 * Use this function to whistle information at a specific level.
	 * Text will only be printed to the target if the input level is currently whistled.
	 * @param text
	 * @param level
	 * @throws IOException
	 */
	public static void whistle(String text, WhistleLevel level) {
		Whistleblower currentWhistleblower = Whistleblower.getInstance();
		if(currentWhistleblower.doIWhistle(level)){
			currentWhistleblower.getWriter().println(currentWhistleblower.getLevelIdent(level) + text);
		}
	}
	
	/**
	 * Returns the identify String for eg. syso calls.
	 * <pre> ERROR level <=> "[ERROR] "</pre>
	 * @param level
	 * @return
	 */
	private String getLevelIdent(WhistleLevel level){
		switch(level){
		case E_LEARN:
			return "[E-Lerning] ";
		case ERROR:
			return "[Error] ";
		case INFO:
			return "[Info] ";
		default:
			return "[Not specified] ";
		}
	}

	/**
	 * @return the level
	 */
	public WhistleLevel getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(WhistleLevel level) {
		this.level = level;
	}

	/**
	 * @return the writer
	 */
	public PrintStream getWriter() {
		return output;
	}

	/**
	 * @param writer the writer to set
	 */
	public void setWriter(PrintStream writer) {
		this.output = writer;
	}

	/**
	 * @return the matrix
	 */
	public boolean[][] getMatrix() {
		return matrix;
	}

}
