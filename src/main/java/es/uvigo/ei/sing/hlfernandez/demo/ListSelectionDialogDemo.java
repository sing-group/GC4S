package es.uvigo.ei.sing.hlfernandez.demo;

import static es.uvigo.ei.sing.hlfernandez.demo.DemoUtils.showDialog;

import java.io.InvalidClassException;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.JFrame;

import es.uvigo.ei.sing.hlfernandez.dialog.ListSelectionDialog;

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
