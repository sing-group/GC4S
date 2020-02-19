/*
 * #%L
 * GC4S components
 * %%
 * Copyright (C) 2014 - 2018 Hugo López-Fernández, Daniel Glez-Peña, Miguel Reboiro-Jato, 
 * 			Florentino Fdez-Riverola, Rosalía Laza-Fidalgo, Reyes Pavón-Rial
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
package org.sing_group.gc4s.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.util.Map;
import java.util.Set;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * A {@code JPanel} that displays different components using a {@code CardLayout} and creates a combo box to control
 * which one should be visible.
 * 
 * @author hlfernandez
 *
 */
public class CardsPanel extends JPanel {
  private static final long serialVersionUID = 1L;

  public static final String PROPERTY_VISIBLE_CARD = "gc4s.cardspanel.visiblecard";

  private JPanel cards;
  private Map<Object, Component> cardsMap;
  private JComboBox<Object> cardSelectionCombo;
  private String selectionLabel;
  private Object selectedCard;
  private boolean disableSelectionWithOneCard;

	/**
	 * Creates a new {@code CardsPanel} with the specified selection label and components.
	 * 
	 * @param cardsMap a map from labels to cards components
	 * @param selectionLabel the combo box label
	 */
	public CardsPanel(Map<Object, Component> cardsMap, String selectionLabel) {
		this(cardsMap, selectionLabel, false, null);
	}

	/**
	 * Creates a new {@code CardsPanel} with the specified selection label and components. This constructor also allows to
	 * specify whether the selection component is disabled when there is only one card or not.
	 * 
	 * 
	 * @param cardsMap a map from labels to cards components
	 * @param selectionLabel the combo box label
	 * @param disableSelectionWithOneCard whether the selection component is disabled when there is only one card or not
	 */
	public CardsPanel(Map<Object, Component> cardsMap, String selectionLabel, boolean disableSelectionWithOneCard) {
		this(cardsMap, selectionLabel, disableSelectionWithOneCard, null);
	}

  /**
   * Creates a new {@code CardsPanel} with the specified selection label and components. This constructor also allows to
   * specify whether the selection component is disabled when there is only one card or not and the name of the card
   * that must be selected by default.
   * 
   * 
   * @param cardsMap a map from labels to cards components
   * @param selectionLabel the combo box label
   * @param disableSelectionWithOneCard whether the selection component is disabled when there is only one card or not
   * @param selectedCard the index of the card that must be selected by default
   */
  public CardsPanel(Map<Object, Component> cardsMap, String selectionLabel, boolean disableSelectionWithOneCard, Object selectedCard) {
    this.cardsMap = cardsMap;
    this.selectionLabel = selectionLabel;
    this.disableSelectionWithOneCard = disableSelectionWithOneCard;
    this.selectedCard = validateSelectedCard(selectedCard, this.cardsMap.keySet());

    this.init();
  }

	private static Object validateSelectedCard(
		Object selectedCard, Set<Object> validCards
	) {
		if (selectedCard == null) {
			return null;
		} else if (validCards.contains(selectedCard)) {
			return selectedCard;
		} else {
			throw new IllegalArgumentException(
				"The default card must be a key in the cards map"
			);
		}
	}

  private void init() {
    this.setLayout(new BorderLayout());
    this.add(getCardsPanel(), BorderLayout.CENTER);
    this.add(getSelectionPanel(), BorderLayout.NORTH);

    if (this.selectedCard != null) {
      this.cardSelectionCombo.setSelectedItem(this.selectedCard);
    }
  }

  private Component getCardsPanel() {
    this.cards = new JPanel(new CardLayout());
    for (Object cardLabel : this.cardsMap.keySet()) {
      this.cards.add(cardsMap.get(cardLabel), cardLabel.toString());
    }
    return this.cards;
  }

  private Component getSelectionPanel() {
    JPanel cardsNorthPanel = new JPanel();
    cardsNorthPanel.setLayout(new FlowLayout());
    cardsNorthPanel.add(Box.createHorizontalGlue());
    cardsNorthPanel.add(new JLabel(this.selectionLabel));
    cardsNorthPanel.add(getCardSelectionCombo());

    return cardsNorthPanel;
  }

  private Component getCardSelectionCombo() {
    this.cardSelectionCombo =
      new JComboBox<Object>(
        this.cardsMap.keySet().toArray(new Object[this.cardsMap.size()])
      );
    this.cardSelectionCombo.setEditable(false);
    this.cardSelectionCombo.addItemListener(this::cardItemChanged);
    this.cardSelectionCombo.setEnabled(!(this.disableSelectionWithOneCard && this.cardsMap.size() == 1));

    return this.cardSelectionCombo;
  }

  private void cardItemChanged(ItemEvent evt) {
    Component oldValue = getSelectedCard();

    CardLayout cl = (CardLayout) (cards.getLayout());
    cl.show(cards, (String) evt.getItem().toString());
    firePropertyChange(PROPERTY_VISIBLE_CARD, oldValue, getSelectedCard());
  }

  /**
   * Returns the current visible component.
   * 
   * @return the current visible component
   */
  public Component getSelectedCard() {
    Component visible = null;
    for (Component c : this.cardsMap.values()) {
      if (c.isVisible()) {
        visible = c;
      }
    }
    return visible;
  }

	/**
	 * Sets the selected card.
	 * 
	 * @param cardLabel the object that is used as label of the selected card
	 */
	public void setSelectedCard(Object cardLabel) {
		this.cardSelectionCombo
			.setSelectedItem(validateSelectedCard(cardLabel, this.cardsMap.keySet()));
	}
}
