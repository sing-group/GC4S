package org.sing_group.gc4s.wizard.event;

import org.sing_group.gc4s.wizard.WizardStep;

public class WizardStepEvent {

	private WizardStep source;

	public WizardStepEvent(WizardStep source) {
		this.source = source;
	}
	
	public WizardStep getSource() {
		return source;
	}
}
