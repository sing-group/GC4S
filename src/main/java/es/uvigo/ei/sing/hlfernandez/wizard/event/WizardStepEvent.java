package es.uvigo.ei.sing.hlfernandez.wizard.event;

import es.uvigo.ei.sing.hlfernandez.wizard.WizardStep;

public class WizardStepEvent {

	private WizardStep source;

	public WizardStepEvent(WizardStep source) {
		this.source = source;
	}
	
	public WizardStep getSource() {
		return source;
	}
}
