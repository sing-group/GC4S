package org.sing_group.gc4s.input.csv.event;

import java.util.EventListener;

import javax.swing.event.ChangeEvent;

/**
 * The listener interface for receiving CSV events.
 * 
 * @author hlfernandez
 *
 */
public interface CsvListener extends EventListener {
	void onFormatEdited(ChangeEvent event);
}
