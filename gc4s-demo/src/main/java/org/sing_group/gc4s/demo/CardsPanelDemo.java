package org.sing_group.gc4s.demo;

import static org.sing_group.gc4s.visualization.VisualizationUtils.showComponent;

import java.awt.Component;

import javax.swing.JLabel;

import org.sing_group.gc4s.ui.CardsPanel;
import org.sing_group.gc4s.ui.CardsPanelBuilder;
import org.sing_group.gc4s.ui.CenteredJPanel;

/**
 * An example showing the use of {@link CardsPanel}.
 * 
 * @author hlfernandez
 *
 */
public class CardsPanelDemo {

	public static void main(String[] args) {
		showComponent(
			getCardsPanel(), "GroupLayoutPanel demo");
	}

	private static Component getCardsPanel() {
		return CardsPanelBuilder.newBuilder()
			.withCard("Card 1", new CenteredJPanel(new JLabel("Card 1 component")))
			.withCard("Card 2", new CenteredJPanel(new JLabel("Card 2 component")))
			.build();
	}
}