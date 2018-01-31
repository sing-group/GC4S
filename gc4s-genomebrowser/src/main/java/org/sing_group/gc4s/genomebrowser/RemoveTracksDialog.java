/*
 * #%L
 * GC4S genome browser
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
package org.sing_group.gc4s.genomebrowser;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import org.sing_group.gc4s.ui.icons.Icons;

/**
 * A dialog that allows users to remove tracks.
 * 
 * @author hlfernandez
 *
 */
public class RemoveTracksDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	private static final ImageIcon DIALOG_OK = Icons.ICON_OK_24;
	private static final ImageIcon DIALOG_CANCEL = Icons.ICON_CANCEL_24;
	
	private GenomeBrowser genomeBrowser;
	private JButton acceptBtn;
	private JButton cancelBtn;

	private JPanel mainPanel = new JPanel(new BorderLayout());
	private JPanel buttonsPanel;
	private JTable filesTable;
	private HashMap<String, File> stringToFile = new HashMap<String, File>();
	private DefaultTableModel filesTableModel;

	/**
	 * Creates a new {@code RemoveTracksDialog} for removing tracks at the 
	 * specified {@code GenomeBrowser}.
	 * 
	 * @param genomeBrowser a {@code GenomeBrowser} instance
	 */
	public RemoveTracksDialog(GenomeBrowser genomeBrowser) {
		super(new Frame(), "Remove track", true);

		this.genomeBrowser = genomeBrowser;

		buttonsPanel = new JPanel(new GridLayout(1, 2));
		acceptBtn = new JButton("Accept", DIALOG_OK);
		cancelBtn = new JButton("Cancel", DIALOG_CANCEL);
		buttonsPanel.add(acceptBtn);
		buttonsPanel.add(cancelBtn);

		mainPanel.add(createFilesTable(), BorderLayout.CENTER);
		mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

		getContentPane().add(mainPanel);

		pack();

		setResizable(false);

		Dimension screen, square;
		screen = Toolkit.getDefaultToolkit().getScreenSize();
		square = this.getSize();

		this.setLocation(((screen.width - square.width) / 2),
			(screen.height - square.height) / 2);

		acceptBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				for (int i = 0; i < filesTableModel.getRowCount(); i++) {
					if (filesTableModel.getValueAt(i, 1) != null) {
						genomeBrowser.removeFile(
							stringToFile.get(filesTableModel.getValueAt(i, 0)));
					}
				}

				RemoveTracksDialog.this.dispose();

			}
		});

		cancelBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				RemoveTracksDialog.this.dispose();
			}
		});

		this.setSize(400, 200);
		this.setResizable(true);
		this.pack();
	}

	private JComponent createFilesTable() {
		filesTableModel = new DefaultTableModel() {

			private static final long serialVersionUID = 1L;

			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == 1) {
					return Boolean.class;
				} else {
					return super.getColumnClass(columnIndex);
				}
			}
		};
		Object[] columns = new Object[2];
		Object[] values = new Object[2];

		columns[0] = "File";
		columns[1] = "Delete";

		filesTableModel.setColumnIdentifiers(columns);
		filesTable = new JTable();
		filesTable
			.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		filesTable.setModel(filesTableModel);
		for (File f : genomeBrowser.getFiles()) {
			values[0] = f.getName();
			stringToFile.put(f.getName(), f);
			filesTableModel.addRow(values);
		}

		JScrollPane scroll = new JScrollPane(filesTable);
		scroll.setBorder(BorderFactory
			.createTitledBorder(BorderFactory.createEtchedBorder(), "Tracks"));

		return scroll;
	}
}
