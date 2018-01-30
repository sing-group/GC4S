package org.sing_group.gc4s.ui;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import org.sing_group.gc4s.input.filechooser.JFileChooserPanel;

/**
 * A builder class for {@link CardsPanel}.
 * 
 * @author hlfernandez
 * @see JFileChooserPanel
 */
public class CardsPanelBuilder {
	private Map<Object, Component> cardsMap = new HashMap<Object, Component>();
	private String selectionLabel = "Card:";

	/**
	 * Returns a new {@code CardsPanelBuilder} instance to build a
	 * {@code CardsPanel}.
	 * 
	 * @return a new {@code CardsPanelBuilder} instance
	 */
	public static CardsPanelBuilder newBuilder() {
		return new CardsPanelBuilder();
	}

	/**
	 * Adds a new {@code Component} as card with the specified {@code label}.
	 * 
	 * @param label any object whose {@code toString} method will be used as 
	 * 		  label
	 * @param card a {@code Component} to add as card
	 * 
	 * @return the {@code CardsPanelBuilder} instance
	 */
	public CardsPanelBuilder withCard(Object label, Component card) {
		cardsMap.put(label, card);
		return this;
	}

	/**
	 * Sets the selection label shown to the user in the main panel.
	 * 
	 * @param label the selection label
	 * @return the {@code CardsPanelBuilder} instance
	 */
	public CardsPanelBuilder withSelectionLabel(String label) {
		selectionLabel = label;
		return this;
	}

	/**
	 * Builds the {@code CardsPanel} using the introduced configuration.
	 * 
	 * @return a new {@code CardsPanel} instance.
	 */
	public CardsPanel build() {
		return new CardsPanel(this.cardsMap, this.selectionLabel);
	}
}