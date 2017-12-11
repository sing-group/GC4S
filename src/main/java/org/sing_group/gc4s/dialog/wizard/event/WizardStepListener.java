package org.sing_group.gc4s.dialog.wizard.event;

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
	 * @param event the generated {@code WizardStepEvent}
	 */
	public void wizardStepCompleted(WizardStepEvent event);

	/**
	 * Invoked when the wizard step is uncompleted.
	 *
	 * @param event the generated {@code WizardStepEvent}
	 */
	public void wizardStepUncompleted(WizardStepEvent event);

	/**
	 * Invoked when the wizard step wants to change the next button tooltip.
	 *
	 * @param nextButtonTooltip the new next button tooltip
	 */
	public void wizardStepNextButtonTooltipChanged(String nextButtonTooltip);
}
