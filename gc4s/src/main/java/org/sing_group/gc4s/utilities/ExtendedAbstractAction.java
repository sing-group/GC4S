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
package org.sing_group.gc4s.utilities;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;

/**
 * <p>
 * An extension of {@code AbstractAction} to reduce verbosity when creating
 * actions by allowing to construct actions by passing references to methods
 * without parameters.
 * </p>
 * 
 * <p>
 * For example:
 * </p>
 * <code>
 * class MyClass {
 * 
 * 	Action myAction = new ExtendedAbstractAction("An action", this::performAction);
 * 
 * 	public void performAction() {
 * 	}
 * 
 * }
 * </code>
 * 
 * @author hlfernandez
 *
 */
public class ExtendedAbstractAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	
	private Action action;

	/**
	 * Creates a new {@code ExtendedAbstractAction} that invokes {@code action} 
	 * on action performed.
	 * 
	 * @param action the {@code Action} to invoke.
	 */
	public ExtendedAbstractAction(Action action) {
		super();
		this.action = action;
	}

	public ExtendedAbstractAction(String name, Action action) {
		super(name);
		this.action = action;
	}

	public ExtendedAbstractAction(String name, Icon icon, Action action) {
		super(name, icon);
		this.action = action;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		action.doAction();
	}
	
	@FunctionalInterface
	public interface Action {
		void doAction();
	}
}
