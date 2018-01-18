package org.sing_group.gc4s.demo;

import org.sing_group.gc4s.input.RangeInputPanel;
import org.sing_group.gc4s.visualization.VisualizationUtils;

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
		
		VisualizationUtils.showComponent(rangeInputPanel);
	}
}
