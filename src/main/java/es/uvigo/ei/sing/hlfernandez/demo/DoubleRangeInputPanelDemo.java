package es.uvigo.ei.sing.hlfernandez.demo;

import static es.uvigo.ei.sing.hlfernandez.demo.DemoUtils.showComponent;
import es.uvigo.ei.sing.hlfernandez.input.DoubleRangeInputPanel;

/**
 * An example showing the use of {@link DoubleRangeInputPanel}.
 * 
 * @author hlfernandez
 *
 */
public class DoubleRangeInputPanelDemo {
	public static void main(String[] args) {
		DoubleRangeInputPanel panel = new DoubleRangeInputPanel(0d, 3d);
		panel.addPropertyChangeListener(DoubleRangeInputPanel.PROPERTY_RANGE,
			e -> {
				System.err.println(e.getNewValue().toString());
			});
		showComponent(panel, "Double range input panel demo");
	}
}
