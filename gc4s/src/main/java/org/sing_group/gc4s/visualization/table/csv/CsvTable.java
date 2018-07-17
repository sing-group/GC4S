/*
 * #%L
 * GC4S components
 * %%
 * Copyright (C) 2014 - 2018 Hugo López-Fernández, Daniel Glez-Peña, Miguel Reboiro-Jato,
 * 			Florentino Fdez-Riverola, Rosalía Laza-Fidalgo, Reyes Pavón-Rial
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
package org.sing_group.gc4s.visualization.table.csv;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.table.ColumnControlButton;
import org.sing_group.gc4s.dialog.ExportCsvDialog;
import org.sing_group.gc4s.visualization.table.csv.CsvTransferHandler.Converter;

/**
 * A table to display CSV data.
 *
 * @author hlfernandez
 * @author mrjato
 *
 */
public class CsvTable extends JXTable {
	private static final long serialVersionUID = 1L;

	public static final String ACTION_EXPORT_CSV = "column.export";

	private CsvTransferHandler transferHandler;
	private Map<Integer, String> formats;
	private Map<Integer, TableCellRenderer> columnRenderers;
	private Action actionExportCsv = new AbstractAction("Export to CSV") {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			exportToCsv();
		}
	};

	/**
	 * Instantiates a CsvTable with a default table model, no data.
	 */
	public CsvTable() {
		super();
		this.configure();
	}

    /**
     * Instantiates a CsvTable for a given number of columns and rows.
     *
     * @param numRows Count of rows to accommodate
     * @param numColumns Count of columns to accommodate
     */
	public CsvTable(int numRows, int numColumns) {
		super(numRows, numColumns);
		this.configure();
	}

    /**
     * Instantiates a CsvTable with data in a array or rows and column names.
     *
     * @param rowData Row data, as a two-dimensional Array of Objects (by row,
     *        for column)
     * @param columnNames Column names, as a Array of Strings
     */
	public CsvTable(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
		this.configure();
	}

    /**
     * Instantiates a CsvTable with a specific table model, column model, and
     * selection model.
     *
     * @param dm The table model to use
     * @param cm The column model to use
     * @param sm The list selection model to use
     */
	public CsvTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
		super(dm, cm, sm);
		this.configure();
	}

    /**
     * Instantiates a CsvTable with specific table and column models.
     *
     * @param dm The table model to use
     * @param cm The column model to use
     */
	public CsvTable(TableModel dm, TableColumnModel cm) {
		super(dm, cm);
		this.configure();
	}

    /**
     * Instantiates a CsvTable with a specific table model
     *
     * @param dm The model to use
     */
	public CsvTable(TableModel dm) {
		super(dm);
		this.configure();
	}

    /**
     * Instantiates a CsvTable with data in a vector or rows and column names.
     *
     * @param rowData Row data, as a Vector of Objects
     * @param columnNames Column names, as a Vector of Strings
     */
	public CsvTable(Vector<?> rowData, Vector<?> columnNames) {
		super(rowData, columnNames);
		this.configure();
	}

	/**
	 * Sets the format of a model column.
	 *
	 * @param modelColumn the model column affected
	 * @param format the new format
	 */
	public void setFormat(int modelColumn, String format) {
		this.formats.put(modelColumn, format);
	}

	/**
	 * Returns the format of a model column.
	 *
	 * @param modelColumn the model column index
	 * @return the format of the specified model column
	 */
	public String getFormat(int modelColumn) {
		return this.formats.get(modelColumn);
	}

	/**
	 * Removes the format of a model column.
	 *
	 * @param modelColumn the model column index
	 */
	public void removeFormat(int modelColumn) {
		this.formats.remove(modelColumn);
	}

	/**
	 * Sets the {@code Converter} of a model column.
	 *
	 * @param modelColumn the model column index
	 * @param converter the {@code Converter}
	 */
	public void setConverter(int modelColumn, Converter converter) {
		this.transferHandler.setConverter(modelColumn, converter);
	}

	/**
	 * Returns the {@code Converter} of a model column.
	 *
	 * @param modelColumn the model column index
	 * @return the {@code Converter}
	 */
	public Converter getConverter(int modelColumn) {
		return this.transferHandler.getConverter(modelColumn);
	}

	/**
	 * Removes the {@code Converter} of a model column.
	 *
	 * @param modelColumn the model column index
	 */
	public void removeConverter(int modelColumn) {
		this.transferHandler.removeConverter(modelColumn);
	}

	/**
	 * Exports the current table view into the specified file.
	 *
	 * @param file the file where the data must be saved
	 *
	 * @throws FileNotFoundException if the specified file is not found
	 */
	public void exportViewToFile(File file) throws FileNotFoundException {
		this.selectAll();

		final BasicTransferable transferable =
			this.transferHandler.createTransferable(this);
		if (transferable == null) {
			throw new RuntimeException("There's not data to export.");
		} else {
			try (PrintWriter pw = new PrintWriter(file)) {
				pw.print(transferable.getPlainData());
			}
		}
		this.clearSelection();
	}

	/**
	 * Exports the current table selection into the specified file.
	 *
	 * @param file the file where the data must be saved
	 *
	 * @throws FileNotFoundException if the specified file is not found
	 */
	public void exportSelectionToFile(File file) throws FileNotFoundException {
		final BasicTransferable transferable =
			this.transferHandler.createTransferable(this);

		if (transferable == null) {
			throw new RuntimeException("There's not data to export.");
		} else {
			if (transferable.getPlainData().trim().equals("")) {
				throw new RuntimeException("No cell selected");
			} else {
				try (PrintWriter pw = new PrintWriter(file)) {
					pw.print(transferable.getPlainData());
				}
			}
		}
	}

	/**
	 * Sets the {@code TableCellRenderer} for the specified column.
	 *
	 * @param index the column index
	 * @param renderer the {@code TableCellRenderer} to use for the specified
	 *        column
	 * @return the previous value associated to that column or null if it was
	 *        not associated
	 */
	public TableCellRenderer setColumnCellRenderer(int index,
		TableCellRenderer renderer
	) {
		return this.columnRenderers.put(index, renderer);
	}

	/**
	 * Returns the {@code TableCellRenderer} associated with the specified
	 * column.
	 *
	 * @param index the column index
	 * @return the {@code TableCellRenderer} to use for the specified
	 *        column
	 */
	public TableCellRenderer getColumnCellRenderer(int index) {
		return this.columnRenderers.get(index);
	}

	@Override
	public TableCellRenderer getCellRenderer(int row, int column) {
		try {
			if (this.columnRenderers.containsKey(column)) {
				return this.columnRenderers.get(column);
			} else if (this.getValueAt(row, column) instanceof Float) {
				return this.getDefaultRenderer(Float.class);
			} else if (this.getValueAt(row, column) instanceof Double) {
				return this.getDefaultRenderer(Double.class);
			} else {
				return super.getCellRenderer(row, column);
			}
		} catch (Exception e) {
			return super.getCellRenderer(row, column);
		}
	}

	private void configure() {
		this.getActionMap().put(ACTION_EXPORT_CSV, actionExportCsv);
		this.transferHandler = new CsvTransferHandler();
		this.formats = new HashMap<Integer, String>();
		this.columnRenderers = new HashMap<Integer, TableCellRenderer>();
		for (int i = 0; i < this.getColumnCount(); i++) {
			this.formats.put(i, "%.4f");
		}

		this.setCellSelectionEnabled(true);
		this.setColumnControlVisible(true);
		this.setTransferHandler(this.transferHandler);
		this.setEditable(false);

		final TableCellRenderer renderer = new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column
			) {
				final Component component = super.getTableCellRendererComponent(
					table, value, isSelected, hasFocus, row, column);

				if ((value instanceof Float || value instanceof Double)
					&& component instanceof JLabel
				) {
					final JLabel label = (JLabel) component;
					label.setHorizontalAlignment(SwingConstants.RIGHT);

					final int modelColumn = table
						.convertColumnIndexToModel(column);
					label.setText(String
						.format(CsvTable.this.formats.get(modelColumn), value));
				}
				return component;
			}
		};

		this.setDefaultRenderer(Float.class, renderer);
		this.setDefaultRenderer(Double.class, renderer);
	}

	private Window getDialogsFrame() {
		return SwingUtilities.getWindowAncestor(this);
	}

	protected void exportToCsv() {
		ExportCsvDialog dialog = new ExportCsvDialog(getDialogsFrame());
		dialog.setVisible(true);

		if (!dialog.isCanceled()) {
			try {
				saveToCSV(this,
					new DefaultCsvTableExporter(dialog.getSelectedCsvFormat()),
					dialog.getSelectedFile()
				);
			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(getDialogsFrame(),
					"An error occured while exporting the table.",
					"Export Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private final void saveToCSV(JTable table, CsvTableExporter exporter,
		File file
	) throws FileNotFoundException {
		StringBuilder plainSB = new StringBuilder();

		for (int j = 0; j < table.getColumnCount(); j++) {
			final String columnName = table.getColumnName(j);
			if (exporter.quoteFields()) {
				plainSB
					.append("\"")
					.append(columnName)
					.append("\"")
					.append(exporter.getColumnSeparator());
			} else {
				plainSB
					.append(columnName)
					.append(exporter.getColumnSeparator());
			}
		}

		plainSB.replace(
			plainSB.length() - 1, plainSB.length(),
			exporter.getLineBreak()
		);

		for (int i = 0; i < table.getRowCount(); i++) {
			for (int j = 0; j < table.getColumnCount(); j++) {
				if (exporter.quoteFields()) {
					plainSB
						.append("\"")
						.append(exporter.parseValue(table.getValueAt(i, j)))
						.append("\"")
						.append(exporter.getColumnSeparator()
				);
				} else {
					plainSB
						.append(exporter.parseValue(table.getValueAt(i, j)))
						.append(exporter.getColumnSeparator()
					);
				}
			}
			plainSB
				.deleteCharAt(plainSB.length() - 1)
				.append(exporter.getLineBreak());
		}

		try (PrintWriter pw = new PrintWriter(file)) {
			pw.print(plainSB.toString());
		}
	}

	/**
	 * Disables the column visibility actions.
	 */
	public void disableColumVisibilityActions() {
		setColumnControl(new CustomColumnControlButton(this));
	}

	private class CustomColumnControlButton extends ColumnControlButton {
		private static final long serialVersionUID = 1L;

		public CustomColumnControlButton(JXTable table) {
			super(table);
		}

		protected void createVisibilityActions() {
		}
	}
}
