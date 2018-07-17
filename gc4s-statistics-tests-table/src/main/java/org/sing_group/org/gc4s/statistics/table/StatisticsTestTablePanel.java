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

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.SOUTH;
import static javax.swing.SwingUtilities.invokeLater;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

import org.sing_group.org.gc4s.statistics.data.Dataset;
import org.sing_group.org.gc4s.statistics.data.tests.PValuesCorrection;
import org.sing_group.org.gc4s.statistics.data.tests.Test;
import org.sing_group.org.gc4s.statistics.table.progress.ProgressEvent;
import org.sing_group.org.gc4s.statistics.table.progress.ProgressEventListener;

/**
 * A panel that shows a {@code StatisticsTestTable} along with a progress bar to
 * display the progress of the computation of the p-values.
 *
 * @author hlfernandez
 *
 * @param <T> the type of the elements in the dataset
 */
public class StatisticsTestTablePanel<T> extends JPanel
	implements ProgressEventListener {
	private static final long serialVersionUID = 1L;

	private StatisticsTestTable<T> table;
	private JProgressBar progressBar;

	/**
	 * Creates a new {@code StatisticsTestTablePanel} with the specified
	 * {@code dataset} and statistic {@code test}, without correction.
	 *
	 * @param dataset a {@code Dataset<T>}
	 * @param test a {@code Test<T>}
	 */
	public StatisticsTestTablePanel(Dataset<T> dataset, Test<T> test) {
		this(dataset, test, null);
	}

	/**
	 * Creates a new {@code StatisticsTestTablePanel} with the specified
	 * {@code dataset}, statistic {@code test} and {@code correction}.
	 *
	 * @param dataset a {@code Dataset<T>}
	 * @param test a {@code Test<T>}
	 * @param correction a {@code PValuesCorrection}
	 */
	public StatisticsTestTablePanel(Dataset<T> dataset, Test<T> test,
		PValuesCorrection correction) {
		this.table = new StatisticsTestTable<>(dataset, test, correction);
		this.init();
	}

	private void init() {
		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(this.table), CENTER);
		this.add(this.getProgressBar(), SOUTH);
		this.table.addProgressEventListener(this);
	}

	private JComponent getProgressBar() {
		if (this.progressBar == null) {
			this.progressBar = new JProgressBar();
			this.progressBar.setStringPainted(true);
		}

		return this.progressBar;
	}

	@Override
	public void onStart() {
		this.setProgressBarValue(0);
	}

	@Override
	public void onProgress(ProgressEvent event) {
		this.setProgressBarValue((int) (event.getProgress() * 100));
	}

	@Override
	public void onFinish() {
		this.setProgressBarValue(0);
	}

	private void setProgressBarValue(int value) {
		invokeLater(() -> {
			this.progressBar.setValue(value);
		});
	}

	/**
	 * Returns the {@code StatisticsTestTable} shown in this component.
	 *
	 * @return the {@code StatisticsTestTable} shown in this component
	 */
	public StatisticsTestTable<T> getTable() {
		return table;
	}
}
