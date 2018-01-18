package org.sing_group.gc4s.demo;

import javax.swing.JFrame;

import org.sing_group.gc4s.dialog.ExportCsvDialog;

/**
 * An example showing the use of {@link ExportCsvDialog}.
 * 
 * @author hlfernandez
 *
 */
public class ExportCsvDialogDemo {
	public static void main(String[] args) {
		ExportCsvDialog dialog = new ExportCsvDialog(new JFrame());
		dialog.setVisible(true);
	}
}
