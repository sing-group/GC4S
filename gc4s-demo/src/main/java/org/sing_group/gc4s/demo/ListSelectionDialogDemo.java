package org.sing_group.gc4s.demo;

import static org.sing_group.gc4s.visualization.VisualizationUtils.showDialog;

import java.io.InvalidClassException;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.JFrame;

import org.sing_group.gc4s.dialog.ListSelectionDialog;

/**
 * An example showing the use of {@link ListSelectionDialog}.
 * 
 * @author hlfernandez
 *
 */
public class ListSelectionDialogDemo {

	public static void main(String[] args) throws InvalidClassException {
		ListSelectionDialog<String> dialog = new ListSelectionDialog<>(
			new JFrame(), Collections.emptyList(), 
			Arrays.asList(new String[] { "a", "b", "c", "d" })
		);

		showDialog(dialog);
	}
}
