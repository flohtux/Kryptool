package gui; // TODO Threads begrenzen

import java.util.Vector;

import javax.swing.SwingWorker;

import Util.Progresser;

import core.util.PosBigInt;

public class SwingWorkers {
	
	public class KeyGenWorker extends SwingWorker<Vector<String>, Void>{	
		private final CallHelper<Vector<String>> caller;

		public KeyGenWorker(final CallHelper<Vector<String>> caller){
			this.caller = caller;
		}
		
		@Override
		protected Vector<String> doInBackground() throws Exception {
			final Vector<String> result = GuiManager.getInstance().generateKeys();
			this.caller.callBack(result);
			return result;
		}
	}
	
	public class EnCodeWorker extends SwingWorker<String, Void>{
		
		private final String text;
		private final Progresser prg;
		private final CallHelper<String> caller;
		
		public EnCodeWorker(final String text, final Progresser prg, final CallHelper<String> caller){
			this.text= text;
			this.prg = prg;
			this.caller= caller;
		}
		@Override
		protected String doInBackground() throws Exception {
			final String result = GuiManager.getInstance().doEnCode(this.text, this.prg);
			this.caller.callBack(result);
			return result;
		}
		
	}
	
	public class EnCodeFileWorker extends SwingWorker<String, Void>{
		
		private final Progresser prg;
		private final CallHelper<String> caller;
		
		public EnCodeFileWorker(final Progresser prg, final CallHelper<String> caller){
			this.prg = prg;
			this.caller= caller;
		}
		@Override
		protected String doInBackground() throws Exception {
			final String result = GuiManager.getInstance().doEnCodeFile(this.prg);
			this.caller.callBack(result);
			return result;
		}
		
	}
	public class DeCodeFileWorker extends SwingWorker<String, Void>{
		private final Progresser prg;
		private final CallHelper<String> caller;

		public DeCodeFileWorker(final Progresser prg,final CallHelper<String> caller){
			this.prg = prg;
			this.caller = caller;
		}
		@Override
		protected String doInBackground() throws Exception {
			final String result = GuiManager.getInstance().doDeCodeFile(this.prg);
			this.caller.callBack(result);
			return result;
		}
		
	}
	
	public class DeCodeWorker extends SwingWorker<String, Void>{
		private final String cliper;
		private final Progresser prg;
		private final CallHelper<String> caller;

		public DeCodeWorker(final String cliper, final Progresser prg,final CallHelper<String> caller){
			this.cliper= cliper;
			this.prg = prg;
			this.caller = caller;
		}
		@Override
		protected String doInBackground() throws Exception {
			final String result = GuiManager.getInstance().doDeCode(this.cliper, this.prg);
			this.caller.callBack(result);
			return result;
		}
		
	}
	public class VerifyWorker extends SwingWorker<Boolean, Void>{
		private final String text;
		private final PosBigInt sign;
		private final CallHelper<Boolean> caller;
		
		public VerifyWorker(final String text, final PosBigInt sign, final CallHelper<Boolean> caller){
			this.text= text;
			this.sign = sign;
			this.caller = caller;
		}
		@Override
		protected Boolean doInBackground() throws Exception {
			final Boolean result = GuiManager.getInstance().doVerify(this.text, this.sign);
			this.caller.callBack(result);
			return result;
		}
		
	}
	public class SignWorker extends SwingWorker<String, Void>{
		private final String text;
		private final CallHelper<String> caller;

		public SignWorker(final String text, final CallHelper<String> caller){
			this.text= text;
			this.caller = caller;
		}
		@Override
		protected String doInBackground() throws Exception {
			final String result = GuiManager.getInstance().doSign(this.text);
			this.caller.callBack(result);
			return result;
		}
		
	}
}