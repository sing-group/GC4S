package es.uvigo.ei.sing.hlfernandez.demo;

import javax.swing.JFrame;

import es.uvigo.ei.sing.hlfernandez.dialog.ExportCsvDialog;

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
