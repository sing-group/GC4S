package org.sing_group.gc4s.demo;

import javax.swing.event.ChangeEvent;

import org.sing_group.gc4s.input.filechooser.JMultipleFileChooserPanel;
import org.sing_group.gc4s.input.filechooser.Mode;
import org.sing_group.gc4s.input.filechooser.event.MultipleFileChooserListener;
import org.sing_group.gc4s.ui.CenteredJPanel;
import org.sing_group.gc4s.visualization.VisualizationUtils;

/**
 * An example showing the use of {@link JMultipleFileChooserPanel}.
 * 
 * @author hlfernandez
 *
 */
public class JMultipleFileChooserPanelDemo {
	public static void main(String[] args) {
		JMultipleFileChooserPanel fileChooserPanel = 
			new JMultipleFileChooserPanel(Mode.OPEN);
		
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
		
		VisualizationUtils.showComponent(new CenteredJPanel(fileChooserPanel));
	}
}
