package org.sing_group.gc4s.demo;

import static java.util.Arrays.asList;
import static org.sing_group.gc4s.demo.DemoUtils.showComponent;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import org.sing_group.gc4s.filechooser.ExtensionFileFilter;
import org.sing_group.gc4s.filechooser.JFileChooserPanel;
import org.sing_group.gc4s.filechooser.JFileChooserPanelBuilder;
import org.sing_group.gc4s.filechooser.SelectionMode;

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
			.withFileChooserSelectionMode(SelectionMode.FILES)
			.withFileFilters(FILTERS)
			.withAllowAllFilter(true)
		.build();
	}
}
