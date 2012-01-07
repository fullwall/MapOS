package com.fullwall.maps.os;

import com.fullwall.maps.applications.Application;
import com.fullwall.maps.applications.ApplicationProvider;
import com.fullwall.maps.interfaces.ApplicationController;

public class SingleApplicationController implements ApplicationController {
	private Application application = null;
	private ApplicationState state = ApplicationState.Shutdown;
	private final OperatingSystem os;

	public SingleApplicationController(OperatingSystem os) {
		this.os = os;
	}

	@Override
	public void endSession(InterruptReason reason) {
		if (application != null) {
			interrupt(reason);
			os.saveApplication(application);
			application = null;
		}
		state = ApplicationState.Shutdown;
	}

	@Override
	public ApplicationState getApplicationState() {
		return this.state;
	}

	@Override
	public Application getCurrentApplication() {
		return this.application;
	}

	@Override
	public void interrupt(InterruptReason reason) {
		if (state != ApplicationState.Paused
				&& state != ApplicationState.Shutdown) {
			if (application != null)
				application.interrupt(reason);
			state = ApplicationState.Paused;
		}
	}

	@Override
	public void resume() {
		if (state != ApplicationState.Running) {
			if (application != null)
				application.resume();
			state = ApplicationState.Running;
		}
	}

	@Override
	public void switchApplication(ApplicationProvider provider) {
		endSession(InterruptReason.SwitchApplication);
		application = provider == null ? null : provider.create(os);
		os.notifyApplicationSwitched(provider, application);
		state = ApplicationState.Running;
	}
}
