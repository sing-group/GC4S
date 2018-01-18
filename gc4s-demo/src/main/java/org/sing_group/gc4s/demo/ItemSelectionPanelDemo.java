package org.sing_group.gc4s.demo;

import static org.sing_group.gc4s.demo.DemoUtils.showComponent;
import static java.util.Arrays.asList;

import java.awt.Component;

import org.sing_group.gc4s.input.ItemSelectionPanel;

/**
 * An example showing the use of {@link ItemSelectionPanel}.
 * 
 * @author hlfernandez
 *
 */
public class ItemSelectionPanelDemo {

	public static void main(String[] args)  {
		showComponent(createItemSelectionPanel(), "ItemSelectionPanel demo");
	}

	private static Component createItemSelectionPanel() {
		ItemSelectionPanel<String> itemSelectionPanel = 
			new ItemSelectionPanel<String>(asList("A", "B", "C", "D"), 2);

		itemSelectionPanel.addPropertyChangeListener(
			ItemSelectionPanel.PROPERTY_SELECTION,
			e -> {
				System.err.println(itemSelectionPanel.getSelectedItems());
			}
		);

		return itemSelectionPanel;
	}
}