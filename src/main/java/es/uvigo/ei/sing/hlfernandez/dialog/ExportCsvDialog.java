package es.uvigo.ei.sing.hlfernandez.dialog;

import static javax.swing.BorderFactory.createEmptyBorder;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;

import es.uvigo.ei.sing.hlfernandez.filechooser.JFileChooserPanel;
import es.uvigo.ei.sing.hlfernandez.input.csv.CsvFormat;
import es.uvigo.ei.sing.hlfernandez.input.csv.CsvPanel;

public class ExportCsvDialog extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;
	
	private static final String TITLE = "Export to CSV";
	private static final String DESCRIPTION = 
		"This dialog allows you to export the data as a CSV file(s). " + 
		"You can select a preconfigured format (Excel, " +
		"Libre/Open Office) or Custom to customize it.";

	private CsvPanel csvPanel;

	private JFileChooserPanel fileChooserPanel;
	
	public ExportCsvDialog(JFrame parent) {
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
		csvPanel.addCsvListener(this::onCsvEvent);
		
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
			JFileChooserPanel.Mode.SAVE, getFileChooser()
		);
		JLabel help = new JLabel(ICON_HELP);
		help.setToolTipText("The file to save the data");
		help.setBorder(createEmptyBorder(0, 14, 0, 7));
		
		fileChooserComponent.add(fileChooserPanel, BorderLayout.CENTER);
		fileChooserComponent.add(help, BorderLayout.EAST);
		return fileChooserComponent;
	}

	private void onCsvEvent(ChangeEvent e) {
		this.okButton.setEnabled(csvPanel.isValidFormat());
		this.pack();
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
}
