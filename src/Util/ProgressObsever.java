package Util;

public interface ProgressObsever {

	public void progressUpdate(long value);

	public void progressUpdate(long value, long progressEnd);
	
}
