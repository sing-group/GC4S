package es.uvigo.ei.sing.hlfernandez.demo;

import java.io.InvalidClassException;

import javax.swing.event.ChangeEvent;

import es.uvigo.ei.sing.hlfernandez.input.csv.CsvListener;
import es.uvigo.ei.sing.hlfernandez.input.csv.CsvPanel;

/**
 * An example showing the use of {@link CsvPanel}.
 * 
 * @author hlfernandez
 *
 */
public class CsvPanelDemo {

	public static void main(String[] args) throws InvalidClassException {
		CsvPanel csvPanel = new CsvPanel();
		csvPanel.addCsvListener(new CsvListener() {

			@Override
			public void onFormatEdited(ChangeEvent event) {
				System.err.println("CSV format edited. Is valid: "
					+ csvPanel.isValidFormat());
			}
		});
		DemoUtils.showComponent(csvPanel);
	}
}