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

import javax.swing.event.ChangeEvent;

import org.sing_group.gc4s.input.filechooser.ExtensionFileFilter;
import org.sing_group.gc4s.input.filechooser.JMultipleFileChooserPanel;
import org.sing_group.gc4s.input.filechooser.JMultipleFileChooserPanelBuilder;
import org.sing_group.gc4s.input.filechooser.SelectionMode;
import org.sing_group.gc4s.input.filechooser.event.MultipleFileChooserListener;

/**
 * An example showing the use of {@link JMultipleFileChooserPanelBuilder} to
 * create a {@code JMultipleFileChooserPanel}.
 * 
 * @author hlfernandez
 *
 */
public class JMultipleFileChooserPanelBuilderDemo {
	public static void main(String[] args) {
		JMultipleFileChooserPanel fileChooserPanel = 
			JMultipleFileChooserPanelBuilder.createOpenJMultipleFileChooserPanel()
				.withFileChooserSelectionMode(SelectionMode.FILES)
				.withFileFilters(asList(
					new ExtensionFileFilter(".*\\.csv", "CSV files", false),
					new ExtensionFileFilter(".*\\.txt", "TXT files")
				))
				.withAllowAllFilter(true)
			.build();
		
		fileChooserPanel
			.addFileChooserListener(new MultipleFileChooserListener() {

				@Override
				public void onFileChoosed(ChangeEvent event) {
					fileSelectionChanged();
				}

				@Override
				public void onFileRemoved(ChangeEvent event) {
					fileSelectionChanged();
				}

				private void fileSelectionChanged() {
					System.err.println("\nSelected files list:");
					fileChooserPanel.getSelectedFiles().forEach(f -> {
						System.err.println("\t - " + f);
					});
				}
			});
			
		showComponent(fileChooserPanel);
	}
}
