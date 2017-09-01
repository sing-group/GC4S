package org.sing_group.gc4s.list;

import java.util.EventListener;

/**
 * The listener interface for receiving {@code ExtendedDefaultListModel} events.
 * 
 * @author hlfernandez
 * @see ExtendedDefaultListModel
 */
public interface ExtendedDefaultListModelListener extends EventListener {
	void elementsAdded(ExtendedDefaultListModel<?> source);
}
