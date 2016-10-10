package es.uvigo.ei.sing.hlfernandez.table;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.swingx.renderer.DefaultTableRenderer;

import es.uvigo.ei.sing.hlfernandez.table.RowHeaderTableModel.RowHeaderCell;

/**
 * A {@code RowHeaderExtendedJXTable} extends a {@code ExtendedJXTable} to allow
 * users creating tables within a row names column.
 * 
 * @author hlfernandez
 *
 */
public class RowHeaderExtendedJXTable extends ExtendedJXTable {
	private static final long serialVersionUID = 1L;
	private Object[] rowNames;

	/**
	 * Instantiates an RowHeaderExtendedJXTable with data in a array or rows,
	 * column names and row names.
	 * 
	 * @param rowData row data, as a two-dimensional Array of Objects (by row, for
	 * 		column).
	 * @param columnNames column names array
	 * @param rowNames row names array
	 */
	public RowHeaderExtendedJXTable(Object[][] rowData, Object[] columnNames,
		Object[] rowNames
	) {
        super(rowData, columnNames);

        this.rowNames = rowNames;
        this.init();
    }
    
	private void init() {
		this.setModel(new RowHeaderTableModel(this.getModel(), this.rowNames));
		this.setDefaultRenderer(RowHeaderCell.class, 
			new RowHeaderRenderer(this.getTableHeader().getDefaultRenderer())
		);
	}
    
	private class RowHeaderRenderer extends DefaultTableRenderer {
		private static final long serialVersionUID = 1L;
		
		private TableCellRenderer defaultRenderer;

		public RowHeaderRenderer(TableCellRenderer defaultRenderer) {
			this.defaultRenderer = defaultRenderer;
		}

		public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus, int row,
			int column
		) {
			return defaultRenderer.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);
		}
    }
}
