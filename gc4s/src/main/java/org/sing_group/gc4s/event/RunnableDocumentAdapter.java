/*
 * #%L
 * GC4S components
 * %%
 * Copyright (C) 2014 - 2020 Hugo López-Fernández, Daniel Glez-Peña, Miguel Reboiro-Jato,
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
package org.sing_group.gc4s.event;

import javax.swing.event.DocumentEvent;

/**
 * A {@code DocumentAdapter} implementation that calls the run method of a {@code Runnable} every time
 * {@code insertUpdate} or {@code removeUpdate} are called.
 * 
 * @author hlfernandez
 *
 */
public class RunnableDocumentAdapter extends DocumentAdapter {
	private Runnable run;

	/**
	 * Creates a new {@code RunnableDocumentAdapter} with the specified {@code Runnable}.
	 * 
	 * @param run the {@code Runnable} to call on insert and remove updates
	 */
	public RunnableDocumentAdapter(Runnable run) {
		this.run = run;
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		this.run.run();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		this.run.run();
	}
}
