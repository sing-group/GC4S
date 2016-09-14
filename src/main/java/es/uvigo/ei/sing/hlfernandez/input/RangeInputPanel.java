package es.uvigo.ei.sing.hlfernandez.input;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.stream.Stream;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * <p>
 * A panel that allows user selecting a range of values, taking into account
 * that the minimum value should always be lower or equal to the maximum value.
 * </p>
 * 
 * <p>
 * It uses {@code JSlider}s for receiving user inputs.
 * </p>
 * 
 * <p>
 * Changes in range selection can be listened by adding a {@code ChangeListener}
 * to this component.
 * </p>
 * 
 * @author hlfernandez
 *
 */
public class RangeInputPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final String MINIMUM_VALUE = "Minimum value";
	private static final String MAXIMUM_VALUE = "Maximum value";
	
	private int min;
	private int max;
	
	private JLabel minValueLabel;
	private String minValueLabelText;
	private JSlider minValueSlider;
	
	private JLabel maxValueLabel;
	private String maxValueLabelText;
	private JSlider maxValueSlider;

	/**
	 * Constructs a new {@code RangeInputPanel} instance using {@code min} and 
	 * {@code max} as minimum and maximum values for the input sliders.
	 * 
	 * @param min minimum value.
	 * @param max maximum value.
	 */
	public RangeInputPanel(int min, int max) {
		this(min, max, MINIMUM_VALUE, MAXIMUM_VALUE);
	}
	
	/**
	 * Constructs a new {@code RangeInputPanel} instance using {@code min} and 
	 * {@code max} as minimum and maximum values for the input sliders.
	 * 
	 * @param min minimum value.
	 * @param max maximum value.
	 * @param minLabel minimum range label.
	 * @param maxLabel maximum range label.
	 */
	public RangeInputPanel(int min, int max, String minLabel, String maxLabel) {
		this.min = min;
		this.max = max;
		this.minValueLabelText = minLabel;
		this.maxValueLabelText = maxLabel;
		
		this.initComponent();
	}

	private void initComponent() {
		this.setLayout(new GridLayout(1, 2));
		this.add(this.getMinPanel(), BorderLayout.WEST);
		this.add(this.getMaxPanel(), BorderLayout.EAST);
		this.initializeSliders();
	}

	private Component getMinPanel() {
		JPanel minPanel = new JPanel(new BorderLayout());
		minValueLabel = new JLabel(minValueLabelText);
		minValueSlider = new JSlider(min, max, min);
		
		minPanel.add(minValueLabel, BorderLayout.NORTH);
		minPanel.add(minValueSlider, BorderLayout.CENTER);
		return minPanel;
	}
	
	private Component getMaxPanel() {
		JPanel maxPanel = new JPanel(new BorderLayout());
		maxValueLabel = new JLabel(maxValueLabelText);
		maxValueSlider = new JSlider(min, max, max);
		
		maxPanel.add(maxValueLabel, BorderLayout.NORTH);
		maxPanel.add(maxValueSlider, BorderLayout.CENTER);
		return maxPanel;
	}
	
	private void initializeSliders() {
		minValueSlider.setPaintTicks(true);
		minValueSlider.setPaintLabels(true);
		minValueSlider.setMajorTickSpacing(1);
		minValueSlider.setMinorTickSpacing(1);
		minValueSlider.addChangeListener((e) -> {
			if(minValueSlider.getValue() > maxValueSlider.getValue()) {
				maxValueSlider.setValue(minValueSlider.getValue());
			}
			rangeChanged();
		});
		
		maxValueSlider.setPaintTicks(true);
		maxValueSlider.setPaintLabels(true);
		maxValueSlider.setMajorTickSpacing(1);
		maxValueSlider.setMinorTickSpacing(1);
		maxValueSlider.addChangeListener((e) -> {
			if(maxValueSlider.getValue() < minValueSlider.getValue()) {
				minValueSlider.setValue(maxValueSlider.getValue());
			}
			rangeChanged();
		});
	}
	
	/**
	 * Returns the minimum value introduced.
	 * 
	 * @return the minimum value introduced.
	 */
	public int getMinValue() {
		return minValueSlider.getValue();
	}
	
	/**
	 * Returns the maximum value introduced.
	 * 
	 * @return the maximum value introduced.
	 */
	public int getMaxValue() {
		return maxValueSlider.getValue();
	}
	
	private void rangeChanged() {
		Stream.of(getListeners(ChangeListener.class)).forEach(l -> {
			l.stateChanged(new ChangeEvent(this));
		});
	}

	public synchronized void addChangeListener(ChangeListener l) {
		this.listenerList.add(ChangeListener.class, l);
	}

	public synchronized void removeChangeListener(ChangeListener l) {
		this.listenerList.remove(ChangeListener.class, l);
	}
}
