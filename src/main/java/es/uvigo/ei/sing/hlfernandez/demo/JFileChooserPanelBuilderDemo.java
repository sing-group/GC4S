package es.uvigo.ei.sing.hlfernandez.demo;

import static es.uvigo.ei.sing.hlfernandez.demo.DemoUtils.showComponent;
import static java.util.Arrays.asList;

import es.uvigo.ei.sing.hlfernandez.filechooser.ExtensionFileFilter;
import es.uvigo.ei.sing.hlfernandez.filechooser.JFileChooserPanel;
import es.uvigo.ei.sing.hlfernandez.filechooser.JFileChooserPanelBuilder;

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
