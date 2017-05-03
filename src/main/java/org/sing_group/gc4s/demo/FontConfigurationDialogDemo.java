package org.sing_group.gc4s.demo;

import static org.sing_group.gc4s.demo.DemoUtils.showDialog;
import javax.swing.JFrame;

import org.sing_group.gc4s.dialog.FontConfigurationDialog;

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
