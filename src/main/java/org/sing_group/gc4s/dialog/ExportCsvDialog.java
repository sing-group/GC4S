package org.sing_group.gc4s.dialog;

import static javax.swing.BorderFactory.createEmptyBorder;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Window;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;

import org.sing_group.gc4s.filechooser.JFileChooserPanel;
import org.sing_group.gc4s.input.csv.CsvFormat;
import org.sing_group.gc4s.input.csv.CsvPanel;

public class ExportCsvDialog extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;
	
	private static final String TITLE = "Export to CSV";
	private static final String DESCRIPTION = 
		"This dialog allows you to export the data as a CSV file(s). " + 
		"You can select a preconfigured format (Excel, " +
		"Libre/Open Office) or Custom to customize it.";

	private CsvPanel csvPanel;

	private JFileChooserPanel fileChooserPanel;
	
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
			JFileChooserPanel.Mode.SAVE, getFileChooser(), "csv"
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
				&& 	!this.fileChooserPanel.getSelectedFile().equals("");
	}

	/**
	 * Returns the selected file.
	 * 
	 * @return the selected file.
	 */
	public File getSelectedFile() {
		return fileChooserPanel.getSelectedFile();
	}

	/**
	 * Return the {@code CsvFormat} based on the configuration selected by the
	 * user.
	 * 
	 * @return Return the {@code CsvFormat} based on the configuration selected
	 *         by the user.
	 */
	public CsvFormat getSelectedCsvFormat() {
		return csvPanel.getCsvFormat();
	}
	
	/**
	 * Establishes the selected {@code CsvFormat.FileFormat}.
	 * 
	 * @param format a {@code CsvFormat.FileFormat}.
	 */
	public void setSelectedCsvFileFormat(CsvFormat.FileFormat format) {
		csvPanel.setSelectedCsvFileFormat(format);
	}
}
