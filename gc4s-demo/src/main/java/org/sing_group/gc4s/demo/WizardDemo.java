/*
 * #%L
 * GC4S demo
 * %%
 * Copyright (C) 2014 - 2018 Hugo López-Fernández, Daniel Glez-Peña, Miguel Reboiro-Jato, 
 * 			Florentino Fdez-Riverola, Rosalía Laza-Fidalgo, Reyes Pavón-Rial
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
package org.sing_group.gc4s.demo;

import java.awt.event.ItemEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.sing_group.gc4s.dialog.wizard.Wizard;
import org.sing_group.gc4s.dialog.wizard.WizardStep;
import org.sing_group.gc4s.dialog.wizard.event.WizardStepEvent;
import org.sing_group.gc4s.ui.CenteredJPanel;
import org.sing_group.gc4s.visualization.VisualizationUtils;

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
		VisualizationUtils.showDialog(dialog);
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
