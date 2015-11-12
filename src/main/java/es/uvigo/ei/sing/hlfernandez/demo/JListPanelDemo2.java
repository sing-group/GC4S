package es.uvigo.ei.sing.hlfernandez.demo;

import java.io.InvalidClassException;
import java.util.Arrays;
import java.util.List;

import javax.swing.JList;

import es.uvigo.ei.sing.hlfernandez.list.ExtendedDefaultListModel;
import es.uvigo.ei.sing.hlfernandez.list.JListPanel;

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

		DemoUtils.showComponent(listPanel, "JList filter demo");
	}
}
