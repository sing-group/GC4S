package org.sing_group.gc4s.table;

import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * Provides functionalities to deal with tables.
 * 
 * @author hlfernandez
 *
 */
public class TableUtils {

	/**
	 * Disables row sorting in {@code table}.
	 * 
	 * @param table a {@code JTable}.
	 */
	public static void disableRowSorting(JTable table) {
		table.setRowSorter(new TableRowSorter<TableModel>(table.getModel()) {
		    @Override
		    public boolean isSortable(int column) {
		        return false;
		    }
		});
	}
}
