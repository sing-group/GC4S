package org.sing_group.gc4s.demo;

import static org.sing_group.gc4s.visualization.VisualizationUtils.*;

import javax.swing.JLabel;

import org.sing_group.gc4s.ui.text.VerticalLabelUI;

/**
 * An example showing the use of {@link VerticalLabelUI}.
 * 
 * @author hlfernandez
 *
 */
public class JVerticalLabelDemo {
	public static void main(String[] args) {
		JLabel verticalLabel = new JLabel("A vertical label");
		verticalLabel.setUI(new VerticalLabelUI(false));
		showComponent(createPanelAndCenterComponent(verticalLabel));
	}
}
