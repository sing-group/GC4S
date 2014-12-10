package es.uvigo.ei.sing.hlfernandez.list;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.InvalidClassException;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import es.uvigo.ei.sing.hlfernandez.ComponentFactory;

/**
 * This class encloses a {@link JList} based on a
 * {@link ExtendedDefaultListModel} to provide common list functionalities
 * (moving elements, select all or clear selection).
 * 
 * @author hlfernandez
 *
 */
public class JListPanel<E> extends JPanel {
	
	private static final long serialVersionUID = 1L;
	protected static final ImageIcon ICON_ARROW_DOWN = new ImageIcon(
			JListPanel.class.getResource("icons/down.png"));
	protected static final ImageIcon ICON_ARROW_UP = new ImageIcon(
			JListPanel.class.getResource("icons/up.png"));
	protected static final ImageIcon ICON_CLEAR = new ImageIcon(
			JListPanel.class.getResource("icons/clear.png"));
	protected static final ImageIcon ICON_SELECT_ALL = new ImageIcon(
			JListPanel.class.getResource("icons/check.png"));
	
	private JList<E> list;
	private ExtendedDefaultListModel<E> listModel;
	private JButton btnMoveDown;
	private JButton btnMoveUp;
	private JButton btnClearSelection;
	private JButton btnSelectAll;
	private JPanel buttonsPanel;
	private AbstractAction actionMoveDown;
	private AbstractAction actionMoveUp;
	private AbstractAction actionSelectAll;
	private AbstractAction actionClearSelection;
	private boolean buttons;

	/**
	 * Constructs an  {@link JListPanel}.
	 * @param list
	 * @throws InvalidClassException 
	 */
	public JListPanel(JList<E> list) throws InvalidClassException{
		this(list, true);
	}

	/**
	 * Constructs an  {@link JListPanel}.
	 * @param list
	 * @throws InvalidClassException 
	 */
	public JListPanel(JList<E> list, boolean buttons) throws InvalidClassException{
		super();
		this.list = list;
		if(!(list.getModel() instanceof ExtendedDefaultListModel)){
			throw new InvalidClassException("List should have a ExtendedDefaultModel");
		}
		this.listModel = (ExtendedDefaultListModel<E>) list.getModel();
		this.buttons = buttons;
		initComponent();
	}

	private void initComponent() {
		this.setLayout(new BorderLayout());
		if (buttons) {
			this.add(getButtonsPane(), BorderLayout.NORTH);
		}
		this.add(new JScrollPane(list), BorderLayout.CENTER);
		this.list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				checkActionButtons();
			}
		});
		this.listModel.addListDataListener(new ListDataListener() {
			
			@Override
			public void intervalRemoved(ListDataEvent e) {
				checkActionButtons();
			}
			
			@Override
			public void intervalAdded(ListDataEvent e) {
				checkActionButtons();
			}
			
			@Override
			public void contentsChanged(ListDataEvent e) {
				checkActionButtons();
			}
		});
	}

	private Component getButtonsPane() {
		if(this.buttonsPanel == null){
			this.buttonsPanel = new JPanel();
			BoxLayout layout = new BoxLayout(this.buttonsPanel, BoxLayout.X_AXIS);
			this.buttonsPanel.setLayout(layout);

			actionMoveDown = new AbstractAction("Move down", ICON_ARROW_DOWN) {
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					moveDownSelectedElement();
				}
			};
			actionMoveUp = new AbstractAction("Move up", ICON_ARROW_UP) {
				private static final long serialVersionUID = 1L;
				
				public void actionPerformed(ActionEvent e) {
					moveUpSelectedElement();
				}
			};
			actionClearSelection = new AbstractAction("Clear selection", ICON_CLEAR) {
				private static final long serialVersionUID = 1L;
				
				public void actionPerformed(ActionEvent e) {
					clearSelection();
				}
			};
			actionSelectAll = new AbstractAction("Select all", ICON_SELECT_ALL) {
				private static final long serialVersionUID = 1L;
				
				public void actionPerformed(ActionEvent e) {
					selectAll();
				}
			};
			
			btnMoveDown = ComponentFactory.createButton(actionMoveDown, false,
					"Moves down the selected element", false);

			btnMoveUp = ComponentFactory.createButton(actionMoveUp, false,
					"Moves up the selected element", false);

			btnSelectAll = ComponentFactory.createButton(actionSelectAll,
					this.listModel.getSize() > 0, "Select all the elements",
					false);

			btnClearSelection = ComponentFactory.createButton(
					actionClearSelection, false, "Clear the current selection",
					false);

			buttonsPanel.add(Box.createHorizontalGlue());
			buttonsPanel.add(btnMoveDown);
			buttonsPanel.add(btnMoveUp);
			buttonsPanel.add(btnSelectAll);
			buttonsPanel.add(btnClearSelection);
			buttonsPanel.add(Box.createHorizontalGlue());
		}
		return buttonsPanel;
	}
	
	private void moveDownSelectedElement() {
		if (listModel.moveDown(list.getSelectedIndex())) {
			list.setSelectedIndex(list.getSelectedIndex() + 1);
		}
	}

	private void moveUpSelectedElement() {
		if (listModel.moveUp(list.getSelectedIndex())) {
			list.setSelectedIndex(list.getSelectedIndex() - 1);
		}
	}
	
	private void checkActionButtons() {
		int selectedIndexesCount = this.list.getSelectedIndices().length;
		boolean moveEnabled = selectedIndexesCount == 1
				&& this.listModel.getSize() > 1;
		btnMoveUp.setEnabled(moveEnabled);
		btnMoveDown.setEnabled(moveEnabled);
		btnClearSelection.setEnabled(selectedIndexesCount > 0);
		btnSelectAll.setEnabled(selectedIndexesCount < this.listModel
				.getSize() && this.listModel.getSize() > 0);
	}
	
	private void clearSelection() {
		this.list.clearSelection();
	}
	
	private void selectAll() {
		int size = this.listModel.getSize();
		int[] selectedIndexes = new int[size];
		for (int i = 0; i < size; i++) {
			selectedIndexes[i] = i;
		}
		this.list.setSelectedIndices(selectedIndexes);
	}

	/**
	 * Returns the move up button.
	 * @return the move up button.
	 */
	public JButton getBtnMoveUp() {
		return btnMoveUp;
	}
	
	/**
	 * Returns the move down button.
	 * @return the move down button.
	 */
	public JButton getBtnMoveDown() {
		return btnMoveDown;
	}
	
	/**
	 * Returns the clear selection button.
	 * @return the clear selection button.
	 */
	public JButton getBtnClearSelection() {
		return btnClearSelection;
	}
	
	/**
	 * Returns the select all button.
	 * @return the select all button.
	 */
	public JButton getBtnSelectAll() {
		return btnSelectAll;
	}
	
	/**
	 * Returns the move down action.
	 * @return the move down action.
	 */
	public AbstractAction getActionMoveUp() {
		return actionMoveUp;
	}
	
	/**
	 * Returns the move down action.
	 * @return the move down action.
	 */
	public AbstractAction getActionMoveDown() {
		return actionMoveDown;
	}
	
	/**
	 * Returns the clear selection action.
	 * @return the clear selection action.
	 */
	public AbstractAction getActionClearSelection() {
		return actionClearSelection;
	}
	
	/**
	 * Returns the select all action.
	 * @return the select all action.
	 */
	public AbstractAction getActionSelectAll() {
		return actionSelectAll;
	}
}


