package es.uvigo.ei.sing.hlfernandez.demo;

import static es.uvigo.ei.sing.hlfernandez.demo.DemoUtils.showDialog;
import javax.swing.JFrame;

import es.uvigo.ei.sing.hlfernandez.dialog.FontConfigurationDialog;

/**
 * An example showing the use of {@link FontConfigurationDialog}.
 * 
 * @author hlfernandez
 *
 */
public class FontConfigurationDialogDemo {
	public static void main(String[] args) {
		showDialog(new FontConfigurationDialog(new JFrame()));
	}
}
