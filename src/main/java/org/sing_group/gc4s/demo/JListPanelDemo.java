package org.sing_group.gc4s.demo;

import java.io.InvalidClassException;
import java.util.Arrays;
import java.util.List;

import javax.swing.JList;

import org.sing_group.gc4s.list.ExtendedDefaultListModel;
import org.sing_group.gc4s.list.JListPanel;

/**
 * An example showing the use of {@link ExtendedDefaultListModel} and
 * {@link JListPanel}.
 * 
 * @author hlfernandez
 *
 */
public class JListPanelDemo {

	public static void main(String[] args) throws InvalidClassException {
		List<String> data = Arrays.asList(new String[] { "a", "b", "c", "d",
				"e" });

		ExtendedDefaultListModel<String> listModel = new ExtendedDefaultListModel<String>();
		listModel.addElements(data);

		JList<String> list = new JList<String>(listModel);

		JListPanel<String> listPanel = new JListPanel<String>(list);

		DemoUtils.showComponent(listPanel);
	}
}
