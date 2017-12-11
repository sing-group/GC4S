package org.sing_group.gc4s.demo;

import java.awt.event.ActionEvent;
import java.io.InvalidClassException;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JList;

import org.sing_group.gc4s.input.list.ExtendedDefaultListModel;
import org.sing_group.gc4s.input.list.JListPanel;
import org.sing_group.gc4s.ui.icons.Icons;

/**
 * An example showing the use of {@link ExtendedDefaultListModel} and
 * {@link JListPanel}.
 *
 * @author hlfernandez
 *
 */
public class JListPanelDemo3 {

	public static void main(String[] args) throws InvalidClassException {
		List<String> data = Arrays.asList(new String[] { "a", "b", "c", "d" });

		ExtendedDefaultListModel<String> listModel = new ExtendedDefaultListModel<String>();
		listModel.addElements(data);

		JList<String> list = new JList<String>(listModel);

		JListPanel<String> listPanel = new JListPanel<String>(list);
		listPanel.addAction(new AbstractAction("Test", Icons.ICON_EDIT_16) {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				System.err.println("A custom list action.");
			}
		});

		DemoUtils.showComponent(listPanel);
	}
}
