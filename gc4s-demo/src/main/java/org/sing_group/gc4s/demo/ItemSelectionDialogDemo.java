package org.sing_group.gc4s.demo;

import static org.sing_group.gc4s.demo.DemoUtils.showDialog;
import static java.util.Arrays.asList;

import javax.swing.JFrame;

import org.sing_group.gc4s.input.ItemSelectionDialog;

/**
 * An example showing the use of {@link ItemSelectionDialog}.
 * 
 * @author hlfernandez
 *
 */
public class ItemSelectionDialogDemo {

	public static void main(String[] args)  {
		showDialog(
			new ItemSelectionDialog<>(new JFrame(), asList("A", "B", "C"), 2)
		);
	}
}