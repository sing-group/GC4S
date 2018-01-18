package org.sing_group.gc4s.demo;

import static org.sing_group.gc4s.visualization.VisualizationUtils.showComponent;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.sing_group.gc4s.ui.icons.Icons;
import org.sing_group.gc4s.utilities.builder.JButtonBuilder;
import org.sing_group.gc4s.visualization.VisualizationUtils;

/**
 * An example showing the use of {@link JButtonBuilder}.
 * 
 * @author hlfernandez
 *
 */
public class JButtonBuilderDemo {

	public static void main(String[] args)  {
		VisualizationUtils.setNimbusLookAndFeel();
		showComponent(createButton(), "JButtonBuilder demo");
	}

	private static Component createButton() {
		return 	JButtonBuilder.newJButtonBuilder()
				.withText("A sample button")
				.withIcon(Icons.ICON_ADD_16)
				.withTooltip("The tooltiptext")
				.thatDoes(new AbstractAction() {
					private static final long serialVersionUID = 1L;

					@Override
					public void actionPerformed(ActionEvent e) {
						System.err.println("The action!");
					}
				})
				.build();
	}
}