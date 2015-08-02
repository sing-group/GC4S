package es.uvigo.ei.hlfernandez.combobox;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

/**
 * An extension of {@code JComboBox} that allows specify whether the size of the
 * combo box must be adapted to its items' width when the drop down list is being
 * displayed.
 * 
 * @author hlfernandez
 * @see JComboBox
 *
 * @param <T> the class of the elements of the combo box.
 */
public class ExtendedJComboBox<T> extends JComboBox<T> {
	private static final int WIDTH_MARGIN = 25;
	private static final long serialVersionUID = 1L;
    private boolean layingOut = false;
    private boolean autoAdjustWidth = false;
    private int maxItemWidth = 0;

	/**
	 * Creates a {@code JComboBox} with a default data model. The default data
	 * model is an empty list of objects. Use addItem to add items. By default
	 * the first item in the data model becomes selected.
	 */
    public ExtendedJComboBox() {
    	super();
    }
    
	/**
	 * Creates a {@code JComboBox} that contains the elements in the specified
	 * array.
	 * 
	 * @param items an array of objects to insert into the combo box.
	 */
    public ExtendedJComboBox(T[] items) {
		super(items);
	}
    
	/**
	 * Creates a {@code JComboBox} that takes its items from an existing 
	 * {@code ComboBoxModel}.
	 * 
	 * @param model the {@code ComboBoxModel} that provides the displayed 
	 * list of items
	 */
    public ExtendedJComboBox(ComboBoxModel<T> model) {
    	super(model);
    }

    /**
     * Sets whether the size of the combo box must be adapted to its items
     * width or not.
     * @param wide {@code true} if the size of the combo box must be adapted 
     * to its items width and {@code false} otherwise. 
     */
	public void setAutoAdjustWidth(boolean wide) {
    	this.autoAdjustWidth = wide;
    	maxItemWidth = getWidestItemWidth();
    }
	
	private int getWidestItemWidth() {
		FontMetrics metrics = getFontMetrics();
		int widest = 0;
		for (int i = 0; i < this.getItemCount(); i++) {
			Object item = this.getItemAt(i);
			int lineWidth = metrics.stringWidth(item.toString());
			widest = Math.max(widest, lineWidth);
		}

		return widest + WIDTH_MARGIN;
	}
    
    private FontMetrics getFontMetrics() {
    	Font font = this.getFont();
        return this.getFontMetrics(font);
	}

	private boolean isAutoAdjustWidth() {
        return autoAdjustWidth;
    }
    
	@Override
	public Dimension getSize() {
		Dimension dim = super.getSize();
		if (!layingOut && isAutoAdjustWidth()) {
			dim.width = Math.max(maxItemWidth, dim.width);
		}
		return dim;
	}

	@Override
	public void doLayout() {
		try {
			layingOut = true;
			super.doLayout();
		} finally {
			layingOut = false;
		}
	}
}