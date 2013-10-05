package Util;

import java.util.Iterator;
import java.util.LinkedList;

public class Observable {

	private final LinkedList<Observer> observer;

	public Observable(){
		this.observer = new LinkedList<Observer>();
	}
	
	/**
	 * @return the observer
	 */
	private LinkedList<Observer> getObserver() {
		return this.observer;
	}
	
	public void notifyObservers(){
		final Iterator<Observer> i = this.getObserver().iterator();
		while(i.hasNext()){
			i.next().handleNotifycation();
		}
	}
	
	public void register(final Observer o){
		this.getObserver().add(o);
	}
	
	public boolean deregister(final Observer o){
		return this.getObserver().remove(o);
	}
	
}
