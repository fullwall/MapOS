package com.fullwall.maps.interfaces;

import com.fullwall.maps.applications.Application;
import com.fullwall.maps.applications.ApplicationProvider;

/**
 * Manages the state of an application, forwarding calls and ensuring its state.
 * Other classes can make calls to it to update the application.
 * 
 * @author fullwall
 */
public interface ApplicationController {
	/**
	 * Shuts down the application, and saves its state.
	 * 
	 * @param reason
	 */
	void endSession(InterruptReason reason);

	/**
	 * Returns the running state of an application. The state is mutable, and
	 * will change over the lifetime of the controller depending on the
	 * application's state.
	 * 
	 * @return the application's state.
	 */
	ApplicationState getApplicationState();

	/**
	 * @return the current application. May be null.
	 */
	Application getCurrentApplication();

	/**
	 * Interrupt the current application, giving a reason. Notifies the
	 * application that it will be interrupted, and halts any processing being
	 * performed.
	 * 
	 * @param reason
	 *            the reason for interrupting the application.
	 */
	void interrupt(InterruptReason reason);

	/**
	 * Resumes the application. Note: although this may be called at any time,
	 * it should only forward calls to the application if it is actually
	 * interrupted or paused.
	 */
	void resume();

	/**
	 * Switches applications, using a provider, and cleans up the application.
	 * 
	 * @param provider
	 *            the provider to use to create a new application. May be null.
	 */
	void switchApplication(ApplicationProvider provider);

	public enum ApplicationState {
		Running,
		Paused,
		Shutdown;
	}

	public enum InterruptReason {
		Shutdown,
		TemporaryInterruption,
		SwitchApplication;
	}
}
