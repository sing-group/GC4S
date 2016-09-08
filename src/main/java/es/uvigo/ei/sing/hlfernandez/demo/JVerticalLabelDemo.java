package es.uvigo.ei.sing.hlfernandez.demo;

import static es.uvigo.ei.sing.hlfernandez.demo.DemoUtils.*;
import javax.swing.JLabel;

import es.uvigo.ei.sing.hlfernandez.ui.VerticalLabelUI;

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
