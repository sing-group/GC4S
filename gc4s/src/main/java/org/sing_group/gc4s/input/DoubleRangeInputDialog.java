package org.sing_group.gc4s.input;

import static org.sing_group.gc4s.input.DoubleRangeInputPanel.PROPERTY_RANGE;

import java.awt.Window;
import java.beans.PropertyChangeEvent;

import javax.swing.JPanel;

import org.sing_group.gc4s.dialog.AbstractInputJDialog;

/**
 * A dialog that allows user to introduce a range of double values.
 * 
 * @author hlfernandez
 *
 */
public class DoubleRangeInputDialog extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;
	
	private DoubleRange range;
	private DoubleRangeInputPanel rangePanel;

	/**
	 * Creates a new {@code DoubleRangeInputDialog} with the initial
	 * {@code range}.
	 * 
	 * @param parent the parent {@code Window}.
	 * @param range the initial {@code DoubleRange}.
	 */
	public DoubleRangeInputDialog(Window parent, DoubleRange range) {
		super(parent);
		
		this.range = range;
		this.init();
	}

	private void init() {
		rangePanel.setRange(range);
		
		this.pack();
	}

	@Override
	protected String getDialogTitle() {
		return "Introduce range";
	}

	@Override
	protected String getDescription() {
		return "This dialog allows you to select a range of values.";
	}

	@Override
	protected JPanel getInputComponentsPane() {
		rangePanel = new DoubleRangeInputPanel();
		rangePanel.addPropertyChangeListener(PROPERTY_RANGE,this::rangeChanged);
		
		return rangePanel;
	}

	private void rangeChanged(PropertyChangeEvent e) {
		this.okButton.setEnabled(rangePanel.isValidRange());
	}
	
	/**
	 * Returns the {@code DoubleRange} introduced.
	 * 
	 * @return the {@code DoubleRange} introduced.
	 */
	public DoubleRange getSelectedRange() {
		return rangePanel.getRange();
	}
}
