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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import es.uvigo.ei.sing.hlfernandez.ComponentFactory;

public class JParallelListsPanel<E> extends JPanel {
	
	private static final long serialVersionUID = 1L;
	protected static final ImageIcon ICON_ARROW_RIGHT = new ImageIcon(
		JParallelListsPanel.class.getResource("icons/right.png"));
	protected static final ImageIcon ICON_ARROW_LEFT = new ImageIcon(
		JParallelListsPanel.class.getResource("icons/left.png"));
	
	private JListPanel<E> left;
	private JListPanel<E> right;
	private AbstractAction actionMoveLeft;
	private AbstractAction actionMoveRight;
	private JButton btnMoveRight;
	private JButton btnMoveLeft;
	
	public JParallelListsPanel(JList<E> left, JList<E> right) 
		throws InvalidClassException 
	{
		this.left = new JListPanel<E>(left, true, false);
		this.right = new JListPanel<E>(right, true, false);
		init();
	}

	private void init() {
		this.setLayout(new BorderLayout());
		this.add(this.left, BorderLayout.WEST);
		this.add(this.getCenterComponent(), BorderLayout.CENTER);
		this.add(this.right, BorderLayout.EAST);
		this.addListeners();
	}

	private Component getCenterComponent() {
		JPanel center = new JPanel();
		center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
		
		actionMoveLeft = new AbstractAction("Move left", ICON_ARROW_LEFT) {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				moveLeftSelectedElement();
			}
		};
		actionMoveRight = new AbstractAction("Move right", ICON_ARROW_RIGHT) {
			private static final long serialVersionUID = 1L;
			
			public void actionPerformed(ActionEvent e) {
				moveRightSelectedElement();
			}
		};
		
		btnMoveLeft = ComponentFactory.createButton(actionMoveLeft, false,
			"Moves the selected element to the left list", false);
		btnMoveRight = ComponentFactory.createButton(actionMoveRight, false,
			"Moves the selected element to the right list", false);

		center.add(Box.createVerticalGlue());
		center.add(btnMoveLeft);
		center.add(btnMoveRight);
		center.add(Box.createVerticalGlue());
		return center;
	}

	protected void moveRightSelectedElement() {
		moveSelectedElements(this.left.getList(), this.right.getList());
		checkLeftListSelection();
	}

	protected void moveLeftSelectedElement() {
		moveSelectedElements(this.right.getList(), this.left.getList());
		checkRightListSelection();
	}
	
	private void moveSelectedElements(JList<E> source, JList<E> dest) {
		FilteredListModel<E> sFLM = (FilteredListModel<E>) source.getModel();
		ExtendedDefaultListModel<E> sEDLM = 
			(ExtendedDefaultListModel<E>) sFLM.getSourceListModel();
		
		FilteredListModel<E> dFLM = (FilteredListModel<E>) dest.getModel();
		ExtendedDefaultListModel<E> dEDLM = 
			(ExtendedDefaultListModel<E>) dFLM.getSourceListModel();
		
		for (E sV : source.getSelectedValuesList()) {
			sEDLM.removeElement(sV);
			dEDLM.addElement(sV);
		}
		
		source.clearSelection();
		source.updateUI();
		dest.updateUI();
	}

	
	private void addListeners() {
		this.left.getList().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				checkLeftListSelection();
			}
		});
		this.right.getList().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				checkRightListSelection();
			}
		});
	}

	protected void checkLeftListSelection() {
		int selectedIndexesCount = this.left.getList().getSelectedIndices().length;
		boolean moveEnabled = selectedIndexesCount > 0;
		this.btnMoveRight.setEnabled(moveEnabled);
	}
	
	protected void checkRightListSelection() {
		int selectedIndexesCount = this.right.getList().getSelectedIndices().length;
		boolean moveEnabled = selectedIndexesCount > 0;
		this.btnMoveLeft.setEnabled(moveEnabled);
	}
}
