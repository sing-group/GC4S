package org.sing_group.gc4s.demo;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import org.sing_group.gc4s.input.RadioButtonsPanel;
import org.sing_group.gc4s.visualization.VisualizationUtils;

/**
 * An example showing the use of {@link RadioButtonsPanel}.
 * 
 * @author hlfernandez
 *
 */
public class RadioButtonsPanelDemo {

	private static enum DemoEnum {
		OptionA, OptionB, OptionC,
		OptionD, OptionE, OptionF
	};

	public static void main(String[] args) {
		RadioButtonsPanel<DemoEnum> panel = 
			new RadioButtonsPanel<DemoEnum>(DemoEnum.values(), 2, 3);

		panel.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					System.err.println("Valid = " + panel.isValidSelection()
						+ "\t" + panel.getSelectedItem());
				}
			}
		});

		VisualizationUtils.showComponent(panel, "RadioButtonsPanel demo");
	}
}