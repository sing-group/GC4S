package org.sing_group.gc4s.input.list;

import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.BorderFactory.createTitledBorder;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.InvalidClassException;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.sing_group.gc4s.ui.CenteredJPanel;
import org.sing_group.gc4s.utilities.builder.JButtonBuilder;

/**
 * A {@code JParallelListsPanel} displays two parallel {@code JListPanel}s,
 * allowing to move elements between them.
 *
 * @author hlfernandez
 *
 * @param <E> the type of the elements in the lists.
 */
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
	private String leftTitle;
	private String rightTitle;

	/**
	 * Creates a new {@code JParallelListsPanel} with {@code left} and
	 * {@code right} lists.
	 *
	 * @param left the left {@code JList}.
	 * @param right the right {@code JList}.
	 * @throws InvalidClassException if list's model is not an
	 * 	{@code ExtendedDefaultModel}.
	 */
	public JParallelListsPanel(JList<E> left, JList<E> right)
		throws InvalidClassException
	{
		this(left, right, true, false);
	}

	/**
	 * Creates a new {@code JParallelListsPanel} with {@code left} and
	 * {@code right} lists.
	 *
	 * @param left the left {@code JList}.
	 * @param right the right {@code JList}.
	 * @param buttons if {@code true}, the action buttons are shown.
	 * @param filter if {@code true}, list filters are shown.
	 * @throws InvalidClassException if list's model is not an
	 * 	{@code ExtendedDefaultModel}.
	 */
	public JParallelListsPanel(JList<E> left, JList<E> right, boolean buttons,
			boolean filter )
		throws InvalidClassException
	{
		this(left, right, "", "", buttons, filter);
	}

	/**
	 * Creates a new {@code JParallelListsPanel} with {@code left} and
	 * {@code right} lists.
	 *
	 * @param left the left {@code JList}.
	 * @param right the right {@code JList}.
	 * @param leftTitle the title for the left list.
	 * @param rightTitle the title for the right list.
	 * @param buttons if {@code true}, the action buttons are shown.
	 * @param filter if {@code true}, list filters are shown.
	 * @throws InvalidClassException if list's model is not an
	 * 	{@code ExtendedDefaultModel}.
	 */
	public JParallelListsPanel(JList<E> left, JList<E> right, String leftTitle,
			String rightTitle, boolean buttons, boolean filter)
		throws InvalidClassException
	{
		this.leftTitle = leftTitle;
		this.rightTitle = rightTitle;
		this.left 	= new JListPanel<E>(left, buttons, filter);
		this.right 	= new JListPanel<E>(right, buttons, filter);
		init();
	}

	private void init() {
		this.setLayout(new BorderLayout());
		this.add(getLeftList(), BorderLayout.WEST);
		this.add(this.getCenterComponent(), BorderLayout.CENTER);
		this.add(getRightList(), BorderLayout.EAST);
		this.addListeners();
	}

	private Component getLeftList() {
		if (leftTitle != null && !leftTitle.equals("")) {
			left.setBorder(createTitledBorder(createEmptyBorder(), leftTitle));
		}
		return left;
	}

	private Component getRightList() {
		if (rightTitle != null && !rightTitle.equals("")) {
			right.setBorder(createTitledBorder(createEmptyBorder(), rightTitle));
		}
		return right;
	}

	private Component getCenterComponent() {
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

		btnMoveLeft = JButtonBuilder.newJButtonBuilder()
			.thatDoes(actionMoveLeft).disabled()
			.withText("")
			.withTooltip("Moves the selected element to the right list")
			.build();

		btnMoveRight = JButtonBuilder.newJButtonBuilder()
			.thatDoes(actionMoveRight).disabled()
			.withText("")
			.withTooltip("Moves the selected element to the left list")
			.build();

		JPanel buttons = new JPanel(new GridLayout(2, 1));
		buttons.add(btnMoveLeft);
		buttons.add(btnMoveRight);

		return new CenteredJPanel(buttons);
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

	public JListPanel<E> getLeftListPanel() {
		return left;
	}

	public JListPanel<E> getRightListPanel() {
		return right;
	}
}
