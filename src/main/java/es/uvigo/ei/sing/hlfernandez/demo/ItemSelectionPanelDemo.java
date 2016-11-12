package es.uvigo.ei.sing.hlfernandez.demo;

import static es.uvigo.ei.sing.hlfernandez.demo.DemoUtils.showComponent;
import static java.util.Arrays.asList;

import java.awt.Component;

import es.uvigo.ei.sing.hlfernandez.input.ItemSelectionPanel;

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