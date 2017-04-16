package es.uvigo.ei.sing.hlfernandez.demo;

import static es.uvigo.ei.sing.hlfernandez.demo.DemoUtils.showComponent;
import static java.util.Arrays.asList;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import es.uvigo.ei.sing.hlfernandez.filechooser.ExtensionFileFilter;
import es.uvigo.ei.sing.hlfernandez.filechooser.JFileChooserPanel;
import es.uvigo.ei.sing.hlfernandez.filechooser.JFileChooserPanelBuilder;

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

		showComponent(panel);
	}

	private static JFileChooserPanel createJFileChooserPanel() {
		return JFileChooserPanelBuilder
			.createOpenJFileChooserPanel()
			.withFileChooserSelectionMode(JFileChooserPanel.SelectionMode.FILES)
			.withFileFilters(FILTERS)
			.withAllowAllFilter(true)
		.build();
	}
}
