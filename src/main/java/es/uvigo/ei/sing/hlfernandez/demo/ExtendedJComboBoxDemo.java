package es.uvigo.ei.sing.hlfernandez.demo;

import java.awt.Dimension;
import java.io.InvalidClassException;

import javax.swing.JPanel;

import es.uvigo.ei.sing.hlfernandez.combobox.ExtendedJComboBox;

/**
 * An example showing the use of {@link ExtendedJComboBox}.
 * 
 * @author hlfernandez
 *
 */
public class ExtendedJComboBoxDemo {

	public static void main(String[] args) throws InvalidClassException {
        String[] items = {
        	"Item 1: this item needs more with to be visible",
            "Item 2: this item also needs more with to be visible", 
        };
        ExtendedJComboBox<String> combobox = new ExtendedJComboBox<String>(items);
        combobox.setPreferredSize(new Dimension(180, 20));
        combobox.setAutoAdjustWidth(true);
        
        JPanel demoPanel = DemoUtils.createPanelAndCenterComponent(combobox);
        
        DemoUtils.showComponent(demoPanel, "Extended JComboBox demo");
	}
}
