package org.sing_group.gc4s.input.filechooser.event;

import java.util.EventListener;

import javax.swing.event.ChangeEvent;

/**
 * The listener interface for receiving file chooser panel events.
 * 
 * @author hlfernandez
 *
 */
public interface FileChooserListener extends EventListener {
	void onFileChoosed(ChangeEvent event);
}
