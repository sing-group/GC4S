package org.sing_group.gc4s.demo;

import static java.util.Arrays.asList;
import static org.sing_group.gc4s.visualization.VisualizationUtils.showDialog;

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