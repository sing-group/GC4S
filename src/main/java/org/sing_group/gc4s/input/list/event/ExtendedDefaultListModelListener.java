package org.sing_group.gc4s.input.list.event;

import java.util.EventListener;

import org.sing_group.gc4s.input.list.ExtendedDefaultListModel;

/**
 * The listener interface for receiving {@code ExtendedDefaultListModel} events.
 * 
 * @author hlfernandez
 * @see ExtendedDefaultListModel
 */
public interface ExtendedDefaultListModelListener extends EventListener {
	void elementsAdded(ExtendedDefaultListModel<?> source);
}
