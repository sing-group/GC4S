package org.sing_group.gc4s.input;

import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * A component that allows user selecting a single item from a list of items
 * using radio buttons.
 *
 * @author hlfernandez
 *
 * @param <T>  the type of the elements in this component
 */
public class RadioButtonsPanel<T> extends JPanel implements ItemListener {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger
		.getLogger(RadioButtonsPanel.class.getName());

	private static final int DEFAULT_PANEL_ROWS = 0;
	private static final int DEFAULT_PANEL_COLUMNS = 1;

	private Map<JRadioButton, T> buttonToItem = new HashMap<>();
	private ButtonGroup group = new ButtonGroup();
	private List<T> values;
	private int buttonsPanelRows = DEFAULT_PANEL_ROWS;
	private int buttonsPanelColumns = DEFAULT_PANEL_COLUMNS;

	/**
	 * Creates a {@code RadioButtonsPanel} that contains the elements in the
	 * specified array. By default the first item in the array becomes selected.
	 *
	 * @param items an array of objects to insert into the component
	 */
	public RadioButtonsPanel(T[] items) {
		this(Arrays.asList(items));
	}

	/**
	 * Creates a {@code RadioButtonsPanel} that contains the elements in the
	 * specified list. By default the first item in the list becomes selected.
	 * Buttons will be layed out using the specified {@code rows} and
	 * {@code columns}.
	 *
	 * @param items a list of objects to insert into the component
	 */
	public RadioButtonsPanel(List<T> items) {
		this(items, DEFAULT_PANEL_ROWS, DEFAULT_PANEL_COLUMNS);
	}

	/**
	 * Creates a {@code RadioButtonsPanel} that contains the elements in the
	 * specified array. By default the first item in the array becomes selected.
	 *
	 * @param items an array of objects to insert into the component
	 * @param rows the number of rows to use
	 * @param columns the number of columns to use
	 */
	public RadioButtonsPanel(T[] items, int rows, int columns) {
		this(Arrays.asList(items), rows, columns);
	}

	/**
	 * Creates a {@code RadioButtonsPanel} that contains the elements in the
	 * specified list. By default the first item in the list becomes selected.
	 * Buttons will be layed out using the specified {@code rows} and
	 * {@code columns}.
	 *
	 * @param items a list of objects to insert into the component
	 * @param rows the number of rows to use
	 * @param columns the number of columns to use
	 */
	public RadioButtonsPanel(List<T> items, int rows, int columns) {
		this.values = items;
		this.buttonsPanelRows = rows;
		this.buttonsPanelColumns = columns;

		this.initComponent();
	}

	private void initComponent() {
		this.add(getRadioButtonsPanel());
	}

	private JPanel getRadioButtonsPanel() {
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(createLayout());

		for (T o : values) {
			JRadioButton button = new JRadioButton(o.toString());
			button.addItemListener(this);
			group.add(button);
			buttonsPanel.add(button);
			buttonToItem.put(button, o);
		}

		if(group.getButtonCount() > 0) {
			group.getElements().nextElement().setSelected(true);
		}

		return buttonsPanel;
	}

	private LayoutManager createLayout() {
		if(this.buttonsPanelColumns == 0 && this.buttonsPanelRows == 0) {
			LOGGER.warning("Warning: both rows and columns can't be 0, default "
				+ "values are used");
			this.buttonsPanelRows = DEFAULT_PANEL_ROWS;
			this.buttonsPanelColumns = DEFAULT_PANEL_COLUMNS;
		}
		return new GridLayout(this.buttonsPanelRows, this.buttonsPanelColumns);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		for (ItemListener i : getItemListeners()) {
			i.itemStateChanged(e);
		}
	}

    /**
     * Returns the selected item.
     *
     * @return the selected item
     */
	public Optional<T> getSelectedItem() {
		T selectedItem = null;
		Enumeration<AbstractButton> buttons = group.getElements();
		while (buttons.hasMoreElements()) {
			AbstractButton button = buttons.nextElement();
			if (button.isSelected()) {
				selectedItem = buttonToItem.get(button);
				break;
			}
		}
		return Optional.ofNullable(selectedItem);
	}

	/**
	 * Sets selected the radio button associated to {@code item}.
	 *
	 * @param item the item that must be selected
	 *
	 * @return {@code true} if the item has been selected and {@code false}
	 *         otherwise
	 */
	public boolean setSelectedItem(T item) {
		item = Objects.requireNonNull(item);
		Enumeration<AbstractButton> buttons = group.getElements();
		while (buttons.hasMoreElements()) {
			AbstractButton button = buttons.nextElement();
			if (buttonToItem.get(button).equals(item)) {
				button.setSelected(true);
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns {@code true} it the selection is valid and {@code false}
	 * otherwise.
	 *
	 * @return {@code true} it the selection is valid and {@code false}
	 *         otherwise
	 */
	public boolean isValidSelection() {
		return getSelectedItem().isPresent();
	}

    /**
	 * Adds an {@code ItemListener} to this component to listen events occurred
	 * in the radio buttons.
	 *
	 * @param l the {@code ItemListener} to be added
	 */
    public void addItemListener(ItemListener l) {
        listenerList.add(ItemListener.class, l);
    }

    /**
     * Removes an {@code ItemListener} from this component.
     *
     * @param l the {@code ItemListener} to be removed
     */
    public void removeItemListener(ItemListener l) {
        listenerList.remove(ItemListener.class, l);
    }

    /**
     * Returns an array of all the {@code ItemListener}s added
     * to this component with {@code addItemListener()}.
     *
     * @return all of the {@code ItemListener}s added or an empty
     *         array if no listeners have been added
     */
    public ItemListener[] getItemListeners() {
        return listenerList.getListeners(ItemListener.class);
    }
}
