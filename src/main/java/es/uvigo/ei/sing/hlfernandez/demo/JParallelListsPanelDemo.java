package es.uvigo.ei.sing.hlfernandez.demo;

import java.io.InvalidClassException;
import java.util.Arrays;
import java.util.List;

import javax.swing.JList;

import es.uvigo.ei.sing.hlfernandez.list.ExtendedDefaultListModel;
import es.uvigo.ei.sing.hlfernandez.list.JParallelListsPanel;

/**
 * An example showing the use of {@link JParallelListsPanel}.
 * 
 * @author hlfernandez
 *
 */
public class JParallelListsPanelDemo {

	public static void main(String[] args) throws InvalidClassException {
		List<String> data = Arrays.asList(new String[] { "a", "b", "c", "d"});

		ExtendedDefaultListModel<String> listModel = 
			new ExtendedDefaultListModel<String>();
		listModel.addElements(data);

		JList<String> left = new JList<String>(listModel);
		JList<String> right = new JList<String>(
			new ExtendedDefaultListModel<String>());

		JParallelListsPanel<String> parallelLists = 
			new JParallelListsPanel<>(left, right);

		DemoUtils.showComponent(parallelLists);
	}
}
