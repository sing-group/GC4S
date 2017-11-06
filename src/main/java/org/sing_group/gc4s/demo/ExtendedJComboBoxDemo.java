package org.sing_group.gc4s.demo;

import java.awt.Dimension;

import javax.swing.JPanel;

import org.sing_group.gc4s.combobox.ExtendedJComboBox;

/**
 * An example showing the use of {@link ExtendedJComboBox}.
 * 
 * @author hlfernandez
 *
 */
public class ExtendedJComboBoxDemo {

	public static void main(String[] args) {
        String[] items = {
        	"Item 1: this item needs more width to be visible",
            "Item 2: this item also needs more width to be visible", 
        };
        ExtendedJComboBox<String> combobox = new ExtendedJComboBox<String>(items);
        combobox.setPreferredSize(new Dimension(180, 20));
        combobox.setAutoAdjustWidth(true);
        
        JPanel demoPanel = DemoUtils.createPanelAndCenterComponent(combobox);
        
        DemoUtils.showComponent(demoPanel, "Extended JComboBox demo");
	}
}
