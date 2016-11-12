package es.uvigo.ei.sing.hlfernandez.demo;

import static es.uvigo.ei.sing.hlfernandez.demo.DemoUtils.showDialog;
import static java.util.Arrays.asList;

import javax.swing.JFrame;

import es.uvigo.ei.sing.hlfernandez.input.ItemSelectionDialog;

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