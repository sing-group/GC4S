package org.sing_group.gc4s.demo;

import static org.sing_group.gc4s.demo.DemoUtils.showComponent;
import static java.util.Arrays.asList;

import org.sing_group.gc4s.filechooser.ExtensionFileFilter;
import org.sing_group.gc4s.filechooser.JFileChooserPanel;
import org.sing_group.gc4s.filechooser.JFileChooserPanelBuilder;

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
				.withFileChooserSelectionMode(JFileChooserPanel.SelectionMode.FILES)
				.withFileFilters(asList(
					new ExtensionFileFilter(".*\\.csv", "CSV files", false),
					new ExtensionFileFilter(".*\\.txt", "TXT files")
				))
				.withAllowAllFilter(true)
			.build(); 
			
		showComponent(fileChooserPanel);
	}
}
