package es.uvigo.ei.sing.hlfernandez.demo;

import java.awt.event.ItemEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

import es.uvigo.ei.sing.hlfernandez.ui.CenteredJPanel;
import es.uvigo.ei.sing.hlfernandez.wizard.Wizard;
import es.uvigo.ei.sing.hlfernandez.wizard.WizardStep;
import es.uvigo.ei.sing.hlfernandez.wizard.event.WizardStepEvent;

/**
 * An example showing the use of {@link Wizard} and {@link WizardStep}.
 * 
 * @author hlfernandez
 *
 */
public class WizardDemo {
	public static void main(String[] args) {
		Wizard dialog = 
			new Wizard(new JFrame(), "Demo Wizard", getWizardSteps()) {
				private static final long serialVersionUID = 1L;
	
				@Override
				protected void wizardFinished() {
					System.err.println("Wizard finished!");
					super.wizardFinished();
				}
			};
		DemoUtils.showDialog(dialog);
	}

	private static List<WizardStep> getWizardSteps() {
		return Arrays.asList(
			new WizardStep() {
				
				private JCheckBox checkBox;

				@Override
				public boolean isStepCompleted() {
					return checkBox.isSelected();
				}
				
				@Override
				public String getStepTitle() {
					return "Step 1";
				}
				
				@Override
				public JComponent getStepComponent() {
					return new CenteredJPanel(getCheckBox());
				}

				private JCheckBox getCheckBox() {
					this.checkBox = new JCheckBox("This is the step 1. Select the check box to be able to go to step 2");
					this.checkBox.addItemListener(this::checkBoxItemStateChanged);
					return this.checkBox ;
				}
				
				private void checkBoxItemStateChanged(ItemEvent e) {
						WizardStepEvent event = new WizardStepEvent(this);
						if (this.checkBox.isSelected()) {
							notifyWizardStepCompleted(event);
						} else {
							notifyWizardStepUncompleted(event);
						}
				}

				@Override
				public void stepEntered() {
					System.err.println("Entered in step 1");
				}
			},
			new WizardStep() {
				
				@Override
				public boolean isStepCompleted() {
					return true;
				}
				
				@Override
				public String getStepTitle() {
					return "Step 2";
				}
				
				@Override
				public JComponent getStepComponent() {
					return new CenteredJPanel(new JLabel("This is the step 2"));
				}

				@Override
				public void stepEntered() {
					System.err.println("Entered in step 2");
				}
			},
			new WizardStep() {
				
				@Override
				public boolean isStepCompleted() {
					return true;
				}
				
				@Override
				public String getStepTitle() {
					return "Step 3";
				}
				
				@Override
				public JComponent getStepComponent() {
					return new CenteredJPanel(new JLabel("This is the final step!"));
				}

				@Override
				public void stepEntered() {
					System.err.println("Entered in step 3");
				}
			}
		);
	}
}
