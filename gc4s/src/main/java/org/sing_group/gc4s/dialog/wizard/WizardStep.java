/*
 * #%L
 * GC4S components
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
package org.sing_group.gc4s.dialog.wizard;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;

import org.sing_group.gc4s.dialog.wizard.event.WizardStepEvent;
import org.sing_group.gc4s.dialog.wizard.event.WizardStepListener;

/**
 * An abstract class that represents a step in a {@code Wizard}. Concrete steps
 * must implement abstract methods in this class in order to allow the
 * {@code Wizard} dialog constructing the assistant.
 *
 * @author hlfernandez
 *
 */
public abstract class WizardStep {

	private final List<WizardStepListener> listeners = new LinkedList<>();

	/**
	 * Returns the title of the step.
	 *
	 * @return the title of the step
	 */
	public abstract String getStepTitle();

	/**
	 * Returns the {@code JComponent} that shows the step.
	 *
	 * @return the {@code JComponent} that shows the step.
	 */
	public abstract JComponent getStepComponent();

	/**
	 * Returns {@code true} if the step is completed and {@code false}
	 * otherwise.
	 *
	 * @return {@code true} if the step is completed and {@code false} otherwise
	 */
	public abstract boolean isStepCompleted();

	/**
	 * This method is invoked when the step is entered. This happens after a
	 * click in he {@code Next} button of the corresponding {@code Wizard}.
	 */
	public abstract void stepEntered();

	/**
	 * Adds the specified wizard step listener to receive component events from
	 * this component. If listener {@code l} is {@code null}, no exception is
	 * thrown and no action is performed.
	 *
	 * @param listener the {@code WizardStepListener}
	 */
	public void addWizardStepListener(WizardStepListener listener) {
		this.listeners.add(listener);
	}

	/**
	 * Removes the specified wizard step listener to stop receiving component
	 * events from this component. Returns {@code true} if the specified
	 * listener was removed and {@code false} otherwise.
	 *
	 * @param listener the {@code WizardStepListener}
	 * @return {@code true} if the specified listener was removed and
	 *         {@code false} otherwise
	 */
	public boolean removeWizardStepListener(WizardStepListener listener) {
		return this.listeners.remove(listener);
	}

	/**
	 * Returns the list of all registered wizard step listeners.
	 *
	 * @return the list of registered {@code WizardStepListener}s
	 */
	public List<WizardStepListener> getWizardStepListeners() {
		return this.listeners;
	}

	protected void notifyWizardStepStatus() {
		WizardStepEvent event = new WizardStepEvent(this);
		if (isStepCompleted()) {
			notifyWizardStepCompleted(event);
		} else {
			notifyWizardStepUncompleted(event);
		}
	}

	protected void notifyWizardStepCompleted(WizardStepEvent event) {
		this.getWizardStepListeners().forEach(l -> l.wizardStepCompleted(event));
	}

	protected void notifyWizardStepUncompleted(WizardStepEvent event) {
		this.getWizardStepListeners().forEach(l -> l.wizardStepUncompleted(event));
	}

	protected void notifyWizardStepNextButtonTooltipChanged(String nextButtonTooltip) {
		this.getWizardStepListeners().forEach(l ->
			l.wizardStepNextButtonTooltipChanged(nextButtonTooltip)
		);
	}
}
