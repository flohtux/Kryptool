package Util;


public class Progresser {
	
	private long value;
	private final ProgressObsever receiver;
	
	public Progresser(final ProgressObsever receiver, final long startValue){
		this.setValue(startValue);
		this.receiver = receiver;
	}
	
	public long getValue(){
		return this.value;
	}
	
	public void setValue(final long newValue){
		this.value = newValue;
	}
	/**
	 * Pushes the progress one step forward.
	 */
	public void step(){
		this.setValue(this.getValue() + 1);
		this.getReceiver().progressUpdate(this.getValue());
	}
	
	public void step(final long progressEnd){
		this.setValue(this.getValue() + 1);
		this.getReceiver().progressUpdate(this.getValue(),progressEnd);
	}

	public ProgressObsever getReceiver() {
		return this.receiver;
	}
	
	/**
	 * Constructor for empty Progresser, used if no Progress observation is necessary 
	 */
	public Progresser(){
		this.value = 0;
		this.receiver = new ProgressObsever() {	
			@Override
			public void progressUpdate(final long value) {				
			}

			@Override
			public void progressUpdate(final long value, final long progressEnd) {				
			}
		};
	}

}
