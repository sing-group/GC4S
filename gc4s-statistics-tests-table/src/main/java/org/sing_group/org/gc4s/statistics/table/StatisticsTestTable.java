/*
 * #%L
 * GC4S statistics tests table
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
package org.sing_group.org.gc4s.statistics.table;

import static es.uvigo.ei.sing.math.statistical.StatisticsTestsUtils.correct;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.SortOrder.ASCENDING;
import static javax.swing.UIManager.getColor;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.RowSorter;
import javax.swing.SwingWorker;

import org.sing_group.gc4s.visualization.table.csv.CsvTable;
import org.sing_group.org.gc4s.statistics.data.Dataset;
import org.sing_group.org.gc4s.statistics.data.FeatureValues;
import org.sing_group.org.gc4s.statistics.data.tests.PValuesCorrection;
import org.sing_group.org.gc4s.statistics.data.tests.Test;
import org.sing_group.org.gc4s.statistics.table.progress.ProgressEvent;
import org.sing_group.org.gc4s.statistics.table.progress.ProgressEventListener;

/**
 * <p>
 * A {@code StatisticsTestTable} displays the data in a given {@code Dataset<T>}
 * and applies the specified {@code Test<T>} to each feature (i.e. row) in the
 * dataset. If a {@code Correction} is provided, then the p-values are corrected
 * and displayed in another column.
 * </p>
 *
 * <p>
 * {@code ProgressEventListener}s can be added to this table in order to receive
 * notifications about the progress of the computation of the p-values.
 * </p>
 *
 * @author hlfernandez
 *
 * @param <T> the type of the elements in the dataset
 */
public class StatisticsTestTable<T> extends CsvTable {
	private static final long serialVersionUID = 1L;

	public static final String ACTION_TESTS_INFO = "column.export";

	private Test<T> test;
	private Dataset<T> dataset;
	private StatisticsTestTableModel<T> model;
	private PValuesCorrection correction;

	private Action actionTestsInfo = new AbstractAction(
		"Statistical tests info"
	) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			showTestsInfo();
		}
	};

	/**
	 * Creates a new {@code StatisticsTestTable} with the specified
	 * {@code dataset} and statistic {@code test}, without correction.
	 *
	 * @param dataset a {@code Dataset<T>}
	 * @param test a {@code Test<T>}
	 */
	public StatisticsTestTable(Dataset<T> dataset, Test<T> test) {
		this(dataset, test, null);
	}

	/**
	 * Creates a new {@code StatisticsTestTable} with the specified
	 * {@code dataset}, statistic {@code test} and {@code correction}.
	 *
	 * @param dataset a {@code Dataset<T>}
	 * @param test a {@code Test<T>}
	 * @param correction a {@code PValuesCorrection}
	 */
	@SuppressWarnings("unchecked")
	public StatisticsTestTable(Dataset<T> dataset, Test<T> test,
		PValuesCorrection correction
	) {
		super(new StatisticsTestTableModel<T>(dataset, correction != null));

		this.dataset = dataset;
		this.model = (StatisticsTestTableModel<T>) super.getModel();
		this.test = test;
		this.correction = correction;

		this.init();
		this.updateTable();
	}

	private void init() {
		this.getActionMap().put(ACTION_TESTS_INFO, actionTestsInfo);
		this.setHorizontalScrollEnabled(true);
	}

	private void updateTable() {
		new UpdateSwingWorker().execute();
	}

	private final class UpdateSwingWorker
		extends SwingWorker<Map<String, Double>, Float> {

		@Override
		protected Map<String, Double> doInBackground() throws Exception {
			fireProgressStarted();

			Map<String, Double> toret = new HashMap<>();

			int count = 0;
			for (String feature : dataset.getFeatures()) {
				FeatureValues<T> fValues = dataset.getFeatureValues(feature);
				Double pValue = test.test(fValues);
				toret.put(feature, pValue);
				publish(
					(float) ++count / (float) dataset.getFeatures().length);
			}

			return toret;
		}

		@Override
		protected void done() {
			try {
				final Map<String, Double> pValues = this.get();
				model.setPvalues(pValues);
				if (correction != null) {
					model.setQvalues(correct(correction, pValues));
				}
				model.fireTableDataChanged();
				statisticalSort();
			} catch (Exception e) {
				e.printStackTrace();
				showConfirmDialog(StatisticsTestTable.this,
					"An error has been produced while updating the statistical "
					+ "values. Please, try again to execute them.",
					"Error updating values", JOptionPane.OK_OPTION,
					JOptionPane.ERROR_MESSAGE);
			} finally {
				fireProgressFinished();
			}
		}

		@Override
		protected void process(List<Float> chunks) {
			Optional<Float> max = chunks.stream().max(Float::compare);
			if (max.isPresent()) {
				fireProgressEvent(new ProgressEvent(max.get()));
			}
		}
	}

	private void showTestsInfo() {
		showMessageDialog(
			this, getTestsInfo(), "Statistical tests info", INFORMATION_MESSAGE);
	}

	public void statisticalSort() {
		List<RowSorter.SortKey> list = new ArrayList<>();
		list.add(new RowSorter.SortKey(correction == null ? 1 : 2, ASCENDING));
		getRowSorter().setSortKeys(list);
	}

	private JEditorPane getTestsInfo() {
		JEditorPane testsInfo = new JEditorPane();
		testsInfo.setContentType("text/html");
		testsInfo.setEditable(false);
		testsInfo.setText(getTestsText());
		testsInfo.setBorder(createEmptyBorder(10, 5, 10, 5));
		testsInfo.setBackground(getColor("Panel.background"));

		return testsInfo;
	}

	private String getTestsText() {
		StringBuilder info = new StringBuilder();
		info
			.append("<html>")
			.append("p-values calculated using <b>")
			.append(test.getName())
			.append("</b>");

		if(test.getAdditionalInfoUrl().isPresent()) {
			info
				.append(" (More info at: ")
				.append(test.getAdditionalInfoUrl().get())
				.append(")");
		}

		info
			.append(".<br/>")
			.append("q-values calculated using <b>")
			.append(correction.getName())
			.append("</b>");

		if(correction.getAdditionalInfoUrl().isPresent()) {
			info
				.append(" (More info at: ")
				.append(correction.getAdditionalInfoUrl().get())
				.append(")");
		}

		info
			.append(".")
			.append("</html>");

		return info.toString();
	}

	/**
	 * Adds the specified progress listener to receive component events from
	 * this component. If listener {@code l} is {@code null}, no exception is
	 * thrown and no action is performed.
	 *
	 * @param l the {@code ProgressEventListener}
	 */
	public synchronized void addProgressEventListener(ProgressEventListener l) {
		this.listenerList.add(ProgressEventListener.class, l);
	}

	/**
	 * Removes the specified progress listener to stop receiving component
	 * events from this component. If listener {@code l} is {@code null}, no
	 * exception is thrown and no action is performed.
	 *
	 * @param l the {@code ProgressEventListener}
	 */
	public synchronized void removeProgressEventListener(ProgressEventListener l) {
		this.listenerList.remove(ProgressEventListener.class, l);
	}

	/**
	 * Returns an array of all the progress listeners registered on this
	 * component.
	 *
	 * @return all {@code ProgressEventListener}s of this component or an empty array
	 *         if no component listeners are currently registered
	 */
	public synchronized ProgressEventListener[] getProgressEventListenerListeners() {
		return this.listenerList.getListeners(ProgressEventListener.class);
	}

	private void fireProgressEvent(ProgressEvent event) {
		for (ProgressEventListener listener : getProgressEventListenerListeners()) {
			listener.onProgress(event);
		}
	}

	private void fireProgressFinished() {
		for (ProgressEventListener listener : getProgressEventListenerListeners()) {
			listener.onFinish();
		}
	}

	private void fireProgressStarted() {
		for (ProgressEventListener listener : getProgressEventListenerListeners()) {
			listener.onStart();
		}
	}

	/**
	 * Returns the {@code StatisticsTestTableModel<T>} used by the table.
	 *
	 * @return the {@code StatisticsTestTableModel<T>} used by the table
	 */
	public StatisticsTestTableModel<T> getStatisticsTestTableModel() {
		return model;
	}
}
