package com.olfybsppa.inglesaventurero.worker;

import com.olfybsppa.inglesaventurero.exceptions.TracedException;


public abstract class Worker implements CancelListener {
  protected boolean cancelRequested = false;
  protected boolean cancelCompleted = false;
	protected boolean workDone        = false;
  protected TracedException exception;
	abstract public void work();

	@Override
	public void setCancelRequestedToTrue() {
		cancelRequested = true;
	}
	
	public boolean isCancelCompleted() {
		return cancelCompleted;
	}
	
	public boolean isCancelRequested() {
		return cancelRequested;
	}
	
	public boolean workIsDone() {
		return workDone;
	}

	public TracedException getException () {
		return exception;
	}

	public boolean wasException () {
		return (exception != null);
	}
	
}
