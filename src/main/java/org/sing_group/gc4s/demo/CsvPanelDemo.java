package org.sing_group.gc4s.demo;

import javax.swing.event.ChangeEvent;

import org.sing_group.gc4s.input.csv.CsvListener;
import org.sing_group.gc4s.input.csv.CsvPanel;

/**
 * An example showing the use of {@link CsvPanel}.
 * 
 * @author hlfernandez
 *
 */
public class CsvPanelDemo {

	public static void main(String[] args) {
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