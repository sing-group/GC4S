package es.uvigo.ei.sing.hlfernandez.table;

import java.util.List;
import java.util.Optional;

import javax.swing.RowFilter;

import org.jdesktop.swingx.JXTable;

/**
 * An extension of {@link JXTable} that allows to establish a row filter.
 * 
 * @author hlfernandez
 *
 */
public class FilterableJXTable extends ExtendedJXTable {
	private static final long serialVersionUID = 1L;
	private Optional<List<Integer>> visibleRows;

    /**
     * Instantiates an FilterableJXTable with data in a array or rows and column
     *  names.
     * 
     * @param datavalues row data, as a two-dimensional Array of Objects (by row,
     * 	for column).
     * @param columnnames column names, as a Array of Strings.
     */
	public FilterableJXTable(String[][] datavalues, String[] columnnames) {
		super(datavalues, columnnames);
		visibleRows = Optional.empty();
	}

	/**
	 * Sets all rows visible (i.e. removes the current row filter).
	 */
	public void setAllRowsVisible() {
		visibleRows = Optional.empty();
		updateRowFilter();
	}

	/**
	 * Sets visible only row indexes specified in {@code visibleRows}. 
	 * 
	 * @param visibleRows a list containing the row indexes that must remain
	 * 	visible.
	 */
	public void setVisibleRows(List<Integer> visibleRows) {
		this.visibleRows = Optional.of(visibleRows);
		updateRowFilter();
	}
	
	private void updateRowFilter() {
		this.setRowFilter(getRowFilter());
	}

	public TestRowFilter<Object, Object> getRowFilter() {
		return new TestRowFilter<>();
	}
	
	public Optional<List<Integer>> getVisibleRows() {
		return visibleRows;
	}

	class TestRowFilter<M, I> extends RowFilter<M, I> {
		
		@Override
		public boolean include(RowFilter.Entry<? extends M, ? extends I> entry) {
			if(getVisibleRows().isPresent()) {
				return getVisibleRows().get().contains(entry.getIdentifier());
			} else {
				return true;
			}
		}
	}
}