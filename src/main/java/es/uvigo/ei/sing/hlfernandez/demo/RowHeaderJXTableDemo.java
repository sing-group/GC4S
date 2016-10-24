package es.uvigo.ei.sing.hlfernandez.demo;

import static es.uvigo.ei.sing.hlfernandez.demo.DemoUtils.showComponent;
import javax.swing.JScrollPane;

import es.uvigo.ei.sing.hlfernandez.table.RowHeaderExtendedJXTable;

/**
 * An example showing the use of {@link RowHeaderExtendedJXTable}.
 * 
 * @author hlfernandez
 *
 */
public class RowHeaderJXTableDemo {
	private static final String columnNames[] = 
		{ "Column 1", "Column 2", "Column 3" };

	private static final String rowNames[] = 
		{ "Row 1", "Row 2", "Row 3", "Row 4" };

	private static final String dataValues[][] =
		{
			{ "12", "234", "67" },
			{ "-123", "43", "853" },
			{ "93", "89.2", "109" },
			{ "279", "9033", "3092" }
		};
	
	public static void main(String[] args) {
		java.util.Locale.setDefault(java.util.Locale.ENGLISH);
		
		RowHeaderExtendedJXTable table = 
			new RowHeaderExtendedJXTable(dataValues, columnNames, rowNames);
		table.setColumnControlVisible(true);
		
		showComponent(new JScrollPane(table), "RowHeaderJXTableDemo demo dialog");
	}
}
