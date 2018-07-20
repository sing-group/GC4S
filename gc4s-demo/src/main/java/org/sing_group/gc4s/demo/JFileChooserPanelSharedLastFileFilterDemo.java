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

import static java.util.Arrays.asList;
import static org.sing_group.gc4s.visualization.VisualizationUtils.showComponent;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import org.sing_group.gc4s.input.filechooser.ExtensionFileFilter;
import org.sing_group.gc4s.input.filechooser.JFileChooserPanel;
import org.sing_group.gc4s.input.filechooser.JFileChooserPanelBuilder;
import org.sing_group.gc4s.input.filechooser.SelectionMode;

/**
 * An example showing the creation of two {@link JFileChooserPanel} that share
 * file filters and also the last file filter selected by the user.
 * 
 * @author hlfernandez
 *
 */
public class JFileChooserPanelSharedLastFileFilterDemo {
	private static final List<FileFilter> FILTERS = asList(
		new ExtensionFileFilter(".*\\.csv", "CSV files"),
		new ExtensionFileFilter(".*\\.txt", "TXT files"),
		new ExtensionFileFilter(".*\\.tsv", "TSV files")
	);

	public static void main(String[] args) {
		JFileChooserPanel fileChooserPanel1 = createJFileChooserPanel(); 
		fileChooserPanel1.setUseSharedLastFileFilter(true);

		JFileChooserPanel fileChooserPanel2 = createJFileChooserPanel(); 
		fileChooserPanel2.setUseSharedLastFileFilter(true);

		JPanel panel = new JPanel(new GridLayout(0, 1));
		panel.add(fileChooserPanel1);
		panel.add(fileChooserPanel2);

		showComponent(panel, "JFileChooserPanel demo");
	}

	private static JFileChooserPanel createJFileChooserPanel() {
		return JFileChooserPanelBuilder
					.createOpenJFileChooserPanel()
					.withFileChooserSelectionMode(SelectionMode.FILES)
					.withFileFilters(FILTERS)
					.withAllowAllFilter(true)
				.build();
	}
}
