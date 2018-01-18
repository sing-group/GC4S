package org.sing_group.gc4s.demo;

import java.io.InvalidClassException;
import java.util.Arrays;
import java.util.List;

import javax.swing.JList;

import org.sing_group.gc4s.input.list.ExtendedDefaultListModel;
import org.sing_group.gc4s.input.list.JListPanel;
import org.sing_group.gc4s.visualization.VisualizationUtils;

/**
 * An example showing the use of {@link ExtendedDefaultListModel} and
 * {@link JListPanel}.
 * 
 * @author hlfernandez
 *
 */
public class JListPanelDemo2 {

	public static void main(String[] args) throws InvalidClassException {
		List<String> data = Arrays.asList(new String[] { 
				"a", "ab", "abc", "abcd", "abcde" });

		ExtendedDefaultListModel<String> listModel = new ExtendedDefaultListModel<String>();
		listModel.addElements(data);

		JList<String> list = new JList<String>(listModel);

		JListPanel<String> listPanel = new JListPanel<String>(list, false, true);

		VisualizationUtils.showComponent(listPanel, "JList filter demo");
	}
}
