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
package org.sing_group.gc4s.ui;

import java.awt.Component;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

/**
 * A {@code JPanel} that only accepts one {@code Component} and displays it
 * in the center.
 * 
 * @author hlfernandez
 *
 */
public class CenteredJPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new {@code CenteredJPanel}. 
	 */
	public CenteredJPanel() {
		super(new GridBagLayout());
	}

	/**
	 * Creates a new {@code CenteredJPanel} with the specified {@code Component}.
	 * 
	 * @param aComponent the initial {@code Component}.
	 */
	public CenteredJPanel(Component aComponent) {
		this();
		this.add(aComponent);
	}

	@Override
	public Component add(Component comp) {
		this.removeAll();
		return super.add(comp);
	}
}
