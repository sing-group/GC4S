package org.sing_group.gc4s.event;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * An abstract adapter class for receiving data list events. The methods in this
 * class are empty. This class exists as convenience for creating listener
 * objects.
 * 
 * @author hlfernandez
 *
 */
public class ListDataAdapter implements ListDataListener {

	@Override
	public void intervalAdded(ListDataEvent e) { }

	@Override
	public void intervalRemoved(ListDataEvent e) { }

	@Override
	public void contentsChanged(ListDataEvent e) { }
}
