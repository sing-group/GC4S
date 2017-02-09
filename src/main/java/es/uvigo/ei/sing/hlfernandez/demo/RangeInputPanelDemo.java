package es.uvigo.ei.sing.hlfernandez.demo;

import es.uvigo.ei.sing.hlfernandez.input.RangeInputPanel;

/**
 * An example showing the use of {@link RangeInputPanel}.
 * 
 * @author hlfernandez
 *
 */
public class RangeInputPanelDemo {

	public static void main(String[] args) {
		RangeInputPanel rangeInputPanel = new RangeInputPanel(0,10);
		
		rangeInputPanel.addChangeListener((e) -> {
			System.err.println("Range changed to: ["
				+ rangeInputPanel.getMinValue() + ", "
				+ rangeInputPanel.getMaxValue() + "]");
		});
		
		DemoUtils.showComponent(rangeInputPanel);
	}
}
