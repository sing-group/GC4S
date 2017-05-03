package org.sing_group.gc4s.input;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.InvalidClassException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataListener;

import org.jdesktop.swingx.JXTextField;

import org.sing_group.gc4s.ComponentFactory;
import org.sing_group.gc4s.list.ExtendedDefaultListModel;
import org.sing_group.gc4s.list.JListPanel;

/**
 * This class encloses a {@code JListPanel<String>} to provide the ability of
 * adding ({@code String}) elements to the list.
 * 
 * @author hlfernandez
 * @see JListPanel
 *
 */
public class JInputList extends JPanel {
	private static final long serialVersionUID = 1L;
	
	protected static final ImageIcon ICON_ADD = new ImageIcon(
		JInputList.class.getResource("icons/add.png"));
	
	private JPanel northPanel;
	private JPanel inputPanel;
	private JXTextField elementTextField;
	private JButton addElementButton;
	
	private boolean listButtons;
	private boolean listFilter;
	private boolean allowRepetitions;

	private JListPanel<String> itemsListPanel;
	private JList<String> itemsList;
	private ExtendedDefaultListModel<String> itemsListModel;

	/**
	 * Creates a new {@code JInputList} instance with {@code JListPanel}
	 * functionalities disabled and without allowing repeated elements.
	 */
	public JInputList() {
		this(false, false, false);
	}
	
	/**
	 * Creates a new {@code JInputList} instance.
	 * 
	 * @param listButtons
	 *            {@code true} if {@code JListPanel} buttons must be shown.
	 * @param listFilter
	 *            {@code true} if {@code JListPanel} filter must be shown.
	 * @param allowRepetitions
	 *            {@code true} if list allows repeated elements.
	 */
	public JInputList(boolean listButtons, boolean listFilter,
		boolean allowRepetitions
	) {
		this.listButtons = listButtons;
		this.listFilter = listFilter;
		this.allowRepetitions = allowRepetitions;
		
		this.initComponent();
	}

	private void initComponent() {
		this.setLayout(new BorderLayout());
		this.add(getNorthPane(), BorderLayout.NORTH);
		this.add(getItemsList(), BorderLayout.CENTER);
	}

	private Component getNorthPane() {
		if (this.northPanel == null) {
			this.northPanel = new JPanel();
			this.northPanel.setLayout(new GridLayout(0, 1));
			this.northPanel.add(getInputPane());
		}
		return this.northPanel;
	}

	private Component getInputPane() {
		if (this.inputPanel == null) {
			this.inputPanel = new JPanel(new BorderLayout());

			elementTextField = new JXTextField("Element", Color.LIGHT_GRAY);
			elementTextField.addActionListener(e -> {
				addElementButton.doClick();
			});
			elementTextField.getDocument().addDocumentListener(
				new DocumentListener() {
					@Override
					public void changedUpdate(DocumentEvent arg0) {
					}
	
					@Override
					public void insertUpdate(DocumentEvent arg0) {
						currentItemChanged();
					}
	
					@Override
					public void removeUpdate(DocumentEvent arg0) {
						currentItemChanged();
					}
				});
			
			addElementButton = 
				ComponentFactory.createButton(new AbstractAction() {
					private static final long serialVersionUID = 1L;
	
					@Override
					public void actionPerformed(ActionEvent e) {
						addElement();
						
					}
				}, false, "", false);
			addElementButton.setIcon(ICON_ADD);
			
			this.inputPanel.add(elementTextField, BorderLayout.CENTER);
			this.inputPanel.add(addElementButton, BorderLayout.EAST);
		}
		return this.inputPanel;
	}
	

	private Component getItemsList() {
		if(this.itemsListPanel == null) {
			this.itemsListModel = new ExtendedDefaultListModel<String>();
			this.itemsList = new JList<String>(this.itemsListModel);
			try {
				this.itemsListPanel = createListPanel();
			} catch (InvalidClassException e) {
				throw new RuntimeException(e);
			}
		}
		return this.itemsListPanel;
	}

	private JListPanel<String> createListPanel() throws InvalidClassException {
		return new JListPanel<String>(itemsList, listButtons, listFilter);
	}

	private void currentItemChanged() {
		this.addElementButton.setEnabled(isValidInput());
	}

	private boolean isValidInput() {
		return 		!this.elementTextField.getText().equals("") 
				&&	checkRepetitions();
	}

	private boolean checkRepetitions() {
		return 		this.allowRepetitions 
				|| 	!listContains(this.elementTextField.getText());
	}

	private boolean listContains(String text) {
		return 	Collections.list(this.itemsListModel.elements()).stream()
				.filter(i -> i.equals(text))
				.findAny().isPresent();
	}

	private void addElement() {
		itemsListModel.addElement(this.elementTextField.getText());
		this.elementTextField.setText("");
		this.itemsList.updateUI();
	}
	
	/**
	 * Adds {@code elements} to the input list.
	 * 
	 * @param elements one or several elements to add.
	 */
	public void addElements(String ...elements) {
		itemsListModel.addElements(Arrays.asList(elements));
		this.itemsList.updateUI();
	}

    /**
	 * Adds a listener to the list that's notified each time a change to the
	 * data model occurs.
	 *
	 * @param l
	 *            the {@code ListDataListener} to be added
	 */
	public void addListDataListener(ListDataListener l) {
		this.itemsListModel.addListDataListener(l);
	}

	/**
	 * Returns a list with the items introduced.
	 * 
	 * @return a list with the items introduced.
	 */
	public List<String> getInputItems() {
		return Collections.list(this.itemsListModel.elements());
	}
	
	/**
	 * Returns the enclosed {@code JListPanel<String>}.
	 * 
	 * @return the enclosed {@code JListPanel<String>}.
	 */
	public JListPanel<String> getListPanel() {
		return itemsListPanel;
	}
}