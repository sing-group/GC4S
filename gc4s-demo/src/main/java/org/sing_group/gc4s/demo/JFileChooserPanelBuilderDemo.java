package org.sing_group.gc4s.demo;

import static java.util.Arrays.asList;
import static org.sing_group.gc4s.visualization.VisualizationUtils.showComponent;

import org.sing_group.gc4s.input.filechooser.ExtensionFileFilter;
import org.sing_group.gc4s.input.filechooser.JFileChooserPanel;
import org.sing_group.gc4s.input.filechooser.JFileChooserPanelBuilder;
import org.sing_group.gc4s.input.filechooser.SelectionMode;

/**
 * An example showing the use of {@link JFileChooserPanelBuilder} to create a 
 * {@code JFileChooserPanel}.
 * 
 * @author hlfernandez
 *
 */
public class JFileChooserPanelBuilderDemo {
	public static void main(String[] args) {
		JFileChooserPanel fileChooserPanel = JFileChooserPanelBuilder
			.createOpenJFileChooserPanel()
				.withFileChooserSelectionMode(SelectionMode.FILES)
				.withFileFilters(asList(
					new ExtensionFileFilter(".*\\.csv", "CSV files", false),
					new ExtensionFileFilter(".*\\.txt", "TXT files")
				))
				.withAllowAllFilter(true)
			.build(); 
		fileChooserPanel.addFileChooserListener(e -> {
			System.err.println("File selected changed: " + fileChooserPanel.getSelectedFile());
		});
		showComponent(fileChooserPanel);
	}
}
