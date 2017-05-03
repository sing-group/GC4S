package org.sing_group.gc4s.wizard.event;

/**
 * The listener interface for receiving wizard step events.
 *
 * @author hlfernandez
 *
 */
public interface WizardStepListener {

	/**
	 * Invoked when the wizard step is completed.
	 * 
	 * @param event the generated {@code WizardStepEvent}.
	 */
	public void wizardStepCompleted(WizardStepEvent event);

	/**
	 * Invoked when the wizard step is uncompleted.
	 * 
	 * @param event the generated {@code WizardStepEvent}.
	 */
	public void wizardStepUncompleted(WizardStepEvent event);
}
