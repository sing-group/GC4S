package org.sing_group.gc4s.dialog.wizard.event;

import org.sing_group.gc4s.dialog.wizard.WizardStep;

/**
 * A {@code WizardStepEvent} is used to notify interested parties that state has
 * changed in the {@code WizardStep} source.
 * 
 * @author hlfernandez
 *
 */
public class WizardStepEvent {

	private WizardStep source;

	public WizardStepEvent(WizardStep source) {
		this.source = source;
	}
	
	public WizardStep getSource() {
		return source;
	}
}
