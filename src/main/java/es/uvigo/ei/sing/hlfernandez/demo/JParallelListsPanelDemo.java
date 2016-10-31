package es.uvigo.ei.sing.hlfernandez.demo;

import static java.util.Arrays.asList;

import java.io.InvalidClassException;
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
		JParallelListsPanel<String> parallelLists = new JParallelListsPanel<>(
			createLeftList(), createRightList(), "Left", "Right", true, false);

		DemoUtils.showComponent(parallelLists);
	}

	private static JList<String> createLeftList() {
		return createList(asList("a", "b", "c", "d"));
	}

	private static JList<String> createRightList() {
		return createList(asList("e", "f", "g", "h", "i", "j", "k", "l", "m"));
	}

	private static JList<String> createList(List<String> data) {
		ExtendedDefaultListModel<String> listModel = 
			new ExtendedDefaultListModel<String>();
		listModel.addElements(data);

		return new JList<String>(listModel);
	}
}
