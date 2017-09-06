package org.sing_group.gc4s.demo;

import static org.sing_group.gc4s.demo.DemoUtils.showComponent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.sing_group.gc4s.input.FontConfigurationPanel;

/**
 * An example showing the use of {@link FontConfigurationPanel}.
 *
 * @author hlfernandez
 *
 */
public class FontConfigurationPanelDemo {

	public static void main(String[] args) {
		FontConfigurationPanel fontConfigurationPanel = new FontConfigurationPanel();
		fontConfigurationPanel.addPropertyChangeListener(
			FontConfigurationPanel.SELECTED_FONT, new PropertyChangeListener() {

				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					System.err.println("Selected font changed:");
					System.err.println("\t- Old value: " + evt.getOldValue());
					System.err.println("\t- New value: " + evt.getNewValue() + "\n");
				}
			});
		showComponent(fontConfigurationPanel, "FontConfigurationPanel demo");
	}
}