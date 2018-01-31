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
package org.sing_group.gc4s.dialog;

import static javax.swing.BorderFactory.createEmptyBorder;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Window;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;

import org.sing_group.gc4s.input.csv.CsvFormat;
import org.sing_group.gc4s.input.csv.CsvPanel;
import org.sing_group.gc4s.input.filechooser.JFileChooserPanel;
import org.sing_group.gc4s.input.filechooser.Mode;

/**
 * An input dialog that allows the configuration of a CSV format.
 * 
 * @author hlfernandez
 * @see CsvFormat
 */
public class ExportCsvDialog extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;
	
	private static final String TITLE = "Export to CSV";
	private static final String DESCRIPTION = 
		"This dialog allows you to export the data as a CSV file(s). " + 
		"You can select a preconfigured format (Excel, " +
		"Libre/Open Office) or Custom to customize it.";

	private CsvPanel csvPanel;
	private JFileChooserPanel fileChooserPanel;

	/**
	 * Constructs an {@code ExportCsvDialog}.
	 * 
	 * @param parent the parent {@code Window}.
	 */
	public ExportCsvDialog(Window parent) {
		super(parent);
		this.pack();
	}

	@Override
	protected String getDialogTitle() {
		return TITLE;
	}

	@Override
	protected String getDescription() {
		return DESCRIPTION;
	}

	@Override
	protected JPanel getInputComponentsPane() {
		csvPanel = new CsvPanel();
		csvPanel.addCsvListener(this::onChangeEvent);
		
		JPanel inputComponents = new JPanel(new BorderLayout());
		inputComponents.setBorder(createEmptyBorder(5, 0, 0, 0));
		inputComponents.add(getFileChooserComponent(), BorderLayout.NORTH);
		inputComponents.add(csvPanel, BorderLayout.CENTER);
		
		return inputComponents;
	}

	private Component getFileChooserComponent() {
		JPanel fileChooserComponent = new JPanel(new BorderLayout());
		fileChooserComponent.setBorder(createEmptyBorder(0, 12, 0, 5));
		fileChooserPanel = new JFileChooserPanel(
			Mode.SAVE, getFileChooser(), "csv"
		);
		fileChooserPanel.addFileChooserListener(this::onChangeEvent);
		JLabel help = new JLabel(ICON_HELP);
		help.setToolTipText("The file to save the data");
		help.setBorder(createEmptyBorder(0, 14, 0, 7));
		
		fileChooserComponent.add(fileChooserPanel, BorderLayout.CENTER);
		fileChooserComponent.add(help, BorderLayout.EAST);
		return fileChooserComponent;
	}

	private void onChangeEvent(ChangeEvent e) {
		checkOkButton();
	}
	
	private void checkOkButton() {
		this.okButton.setEnabled(
			csvPanel.isValidFormat() && 
			isSelectedFileValid()
		);
		this.pack();
	}
	
	private boolean isSelectedFileValid() {
		return 		this.fileChooserPanel.getSelectedFile() != null
				&& 	!this.fileChooserPanel.getSelectedFile().getName().equals("");
	}

	/**
	 * Returns the selected file.
	 * 
	 * @return the selected file
	 */
	public File getSelectedFile() {
		return fileChooserPanel.getSelectedFile();
	}

	/**
	 * Return the {@code CsvFormat} based on the configuration selected by the
	 * user.
	 * 
	 * @return the {@code CsvFormat} based on the configuration selected by the 
	 *         user
	 */
	public CsvFormat getSelectedCsvFormat() {
		return csvPanel.getCsvFormat();
	}
	
	/**
	 * Establishes the selected {@code CsvFormat.FileFormat}.
	 * 
	 * @param format a {@code CsvFormat.FileFormat}
	 */
	public void setSelectedCsvFileFormat(CsvFormat.FileFormat format) {
		csvPanel.setSelectedCsvFileFormat(format);
	}
}
