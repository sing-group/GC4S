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
package org.sing_group.gc4s.ui.menu;

import java.util.stream.Stream;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * This component extends {@code JPopupMenu} so that it only becomes visible if
 * there is at least one {@code JMenuItem} enabled. Otherwise, it will not
 * become visible.
 * 
 * @author hlfernandez
 *
 */
public class JPopupMenuActionsVisibility extends JPopupMenu {
	private static final long serialVersionUID = 1L;

	@Override
	public void setVisible(boolean b) {
		if(b) {
			b = Stream.of(this.getComponents())
				.filter(c -> c instanceof JMenuItem)
				.filter(c -> {
					return ((JMenuItem) c).isEnabled();
				}).findAny().isPresent();
		}
		super.setVisible(b);
	}
}
