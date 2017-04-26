package es.uvigo.ei.sing.hlfernandez.demo;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import es.uvigo.ei.sing.hlfernandez.input.RadioButtonsPanel;

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

		DemoUtils.showComponent(panel, "RadioButtonsPanel demo");
	}
}