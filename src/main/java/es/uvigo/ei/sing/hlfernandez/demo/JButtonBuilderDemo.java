package es.uvigo.ei.sing.hlfernandez.demo;

import static es.uvigo.ei.sing.hlfernandez.demo.DemoUtils.showComponent;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import es.uvigo.ei.sing.hlfernandez.ui.icons.Icons;
import es.uvigo.ei.sing.hlfernandez.utilities.builder.JButtonBuilder;

/**
 * An example showing the use of {@link JButtonBuilder}.
 * 
 * @author hlfernandez
 *
 */
public class JButtonBuilderDemo {

	public static void main(String[] args)  {
		DemoUtils.setNimbusLookAndFeel();
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