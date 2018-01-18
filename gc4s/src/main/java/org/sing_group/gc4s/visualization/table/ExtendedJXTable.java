package org.sing_group.gc4s.visualization.table;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static org.jdesktop.swingx.table.ColumnControlButton.COLUMN_CONTROL_MARKER;

import java.util.List;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.table.ColumnControlButton;

/**
 * An extension of {@link JXTable} that allows to hide/show the column
 * visibility actions and also facilitates adding own actions to the
 * {@code ColumnControlButton}.
 * 
 * @author hlfernandez
 *
 */
public class ExtendedJXTable extends JXTable {

	private static final long serialVersionUID = 1L;
	private CustomColumnControlButton columnControlButton;
	private boolean showVisibilityActions = true; 

	/** 
	 * Instantiates an ExtendedJXTable with a default table model, no data. 
	 */
    public ExtendedJXTable() {
        super();
        init();
    }

    /**
     * Instantiates an ExtendedJXTable with a specific table model.
     * 
     * @param dm The model to use.
     */
    public ExtendedJXTable(TableModel dm) {
        super(dm);
        init();
    }

    /**
     * Instantiates an ExtendedJXTable with a specific table model.
     * 
     * @param dm The model to use.
     * @param cm The column model to use.
     */
    public ExtendedJXTable(TableModel dm, TableColumnModel cm) {
        super(dm, cm);
        init();
    }

    /**
     * Instantiates an ExtendedJXTable with a specific table model, column model, and
     * selection model.
     * 
     * @param dm The table model to use.
     * @param cm The column model to use.
     * @param sm The list selection model to use.
     */
    public ExtendedJXTable(TableModel dm, TableColumnModel cm, 
    	ListSelectionModel sm
    ) {
        super(dm, cm, sm);
        init();
    }

    /**
     * Instantiates an ExtendedJXTable for a given number of columns and rows.
     * 
     * @param numRows Count of rows to accommodate.
     * @param numColumns Count of columns to accommodate.
     */
    public ExtendedJXTable(int numRows, int numColumns) {
        super(numRows, numColumns);
        init();
    }

    /**
     * Instantiates an ExtendedJXTable with data in a vector or rows and column names.
     * 
     * @param rowData Row data, as a Vector of Objects.
     * @param columnNames Column names, as a Vector of Strings.
     */
    public ExtendedJXTable(Vector<?> rowData, Vector<?> columnNames) {
        super(rowData, columnNames);
        init();
    }

    /**
     * Instantiates an ExtendedJXTable with data in a array or rows and column names.
     * 
     * @param rowData Row data, as a two-dimensional Array of Objects (by row,
     *        for column).
     * @param columnNames Column names, as a Array of Strings.
     */
    public ExtendedJXTable(Object[][] rowData, Object[] columnNames) {
        super(rowData, columnNames);
        init();
    }

    private void init(){
    	columnControlButton = new CustomColumnControlButton(this);
    	setColumnControl(columnControlButton);
    }
    
    /**
	 * Adds {@code Action a} so that it will appear in the
	 * {@code ColumnControlButton} of the table.
	 * 
	 * @param a The action to add.
	 */
	public void addAction(Action a) {
		this.getActionMap().put(getActionName(a), a);
		this.columnControlButton.update();
	}
	
	private String getActionName(Action a) {
		return COLUMN_CONTROL_MARKER + a.getValue(Action.NAME);
	}

	/**
	 * Sets the visibility of the column visibility actions at the {@code ColumnControlButton}.
	 * 
	 * @param enabled If true, the column visibility actions are displayed. 
	 */
	public void setColumVisibilityActionsEnabled(boolean enabled) {
		this.showVisibilityActions = enabled;
		this.columnControlButton.update();
	}

	private class CustomColumnControlButton extends ColumnControlButton {
		private static final long serialVersionUID = 1L;

		public CustomColumnControlButton(JXTable table) {
			super(table);
		}

		protected void createVisibilityActions() {
			if(showVisibilityActions == true){
				super.createVisibilityActions();
			}
		}
	
		public void update() {
			super.populatePopup();
		}
	}
	
	public List<String> getColumnNames() {
		return 	range(0, this.getModel().getColumnCount())
				.boxed().map(this.getModel()::getColumnName)
				.collect(toList());
	}
	
	public Object[][] getData() {
		TableModel model = this.getModel();
		Object[][] data = new Object[model.getRowCount()][model.getColumnCount()];
		
		for (int i = 0; i < model.getRowCount(); i++) {
			for (int j = 0; j < model.getColumnCount(); j++) {
				Object value = model.getValueAt(i, j);
				data[i][j] = value == null? "" : value;
			}
		}
		
		return data;
	}
}
