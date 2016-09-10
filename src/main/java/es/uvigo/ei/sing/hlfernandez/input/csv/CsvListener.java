package es.uvigo.ei.sing.hlfernandez.input.csv;

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
