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

import javax.swing.event.ChangeEvent;

import org.sing_group.gc4s.input.csv.CsvPanel;
import org.sing_group.gc4s.input.csv.event.CsvListener;
import org.sing_group.gc4s.visualization.VisualizationUtils;

/**
 * An example showing the use of {@link CsvPanel}.
 * 
 * @author hlfernandez
 *
 */
public class CsvPanelDemo {

	public static void main(String[] args) {
		CsvPanel csvPanel = new CsvPanel();
		csvPanel.addCsvListener(new CsvListener() {

			@Override
			public void onFormatEdited(ChangeEvent event) {
				System.err.println("CSV format edited. Is valid: "
					+ csvPanel.isValidFormat());
			}
		});
		VisualizationUtils.showComponent(csvPanel);
	}
}