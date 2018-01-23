package org.sing_group.gc4s.genomebrowser;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import org.sing_group.gc4s.genomebrowser.painter.Painter;
import org.sing_group.gc4s.ui.icons.Icons;

/**
 * A dialog that allows users to sort tracks.
 * 
 * @author hlfernandez
 *
 */
public class SortTracksDialog extends JDialog{
	private static final long serialVersionUID = 1L;

	private JPanel contents, buttonsPane, sortPane;
	private JButton accept, cancel;
	
	public static final int STATUS_ACCEPT = 1;
	public static final int STATUS_CANCEL = 2;

	private int status;

	private static final ImageIcon IMAGE_ACCEPT = Icons.ICON_OK_24;
	private static final ImageIcon IMAGE_CANCEL = Icons.ICON_CANCEL_24;
	private static final ImageIcon ARROW_DOWN = Icons.ICON_ARROW_DOWN_16;
	private static final ImageIcon ARROW_UP = Icons.ICON_ARROW_UP_16;
	
	private GenomeBrowser genomeBrowser;
	private JPanel buttons;
	private LinkedList<File> files;
	private LinkedList<Painter> painters;
	
	private JButton downButton, upButton;
	private JList<String> tracksJList;
	
	/**
	 * Creates a new {@code SortTracksDialog} for sorting tracks at the 
	 * specified {@code GenomeBrowser}.
	 * 
	 * @param genomeBrowser a {@code GenomeBrowser} instance
	 */
	public SortTracksDialog(GenomeBrowser genomeBrowser){
		this.genomeBrowser = genomeBrowser;	
		initialize();
		this.setModal(true);		
		this.setResizable(false);
		this.setTitle("Tracks options");
		this.pack();
		this.setLocationRelativeTo(null);
	}
	
	private void initialize(){
		this.copyLists();
		contents = new JPanel();
		BoxLayout contentsLayout = new BoxLayout(contents,BoxLayout.Y_AXIS);
		contents.setLayout(contentsLayout);
		BorderLayout mainLayout = new BorderLayout();
		this.setLayout(mainLayout);
		this.add(new JPanel(), BorderLayout.NORTH);
		this.add(new JPanel(), BorderLayout.EAST);
		this.add(new JPanel(), BorderLayout.WEST);
		this.add(contents, BorderLayout.CENTER);
		
		createSortPane();
		contents.add(sortPane);
		
		createButtonsPane();
		contents.add(buttonsPane);
	}
	
	private void copyLists() {
		this.files = new LinkedList<File>();
		this.painters = new LinkedList<Painter>();
		for (File f : genomeBrowser.getFiles()) this.files.addLast(f);
		for (Painter p : genomeBrowser.getPainters()) this.painters.addLast(p);
	}

	private void copyNewLists() {
		genomeBrowser.getFiles().clear();
		genomeBrowser.getPainters().clear();
		for (File f : this.files)
			genomeBrowser.getFiles().add(f);
		for (Painter p : this.painters)
			genomeBrowser.getPainters().add(p);
	}

	private void createSortPane(){
		sortPane = new JPanel();
		sortPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Tracks order"));
		sortPane.setLayout(new BoxLayout(sortPane,BoxLayout.X_AXIS));

		String	tracksData[] = new String[this.files.size()];
		int i = 0;
		for (File f : this.files) {
			tracksData[i++] = f.getName();
		}
		tracksJList = new JList<>(tracksData);
		tracksJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tracksJList.setSelectedIndex(0);

		sortPane.add(tracksJList);
		
		buttons = new JPanel();
		downButton = new JButton();
		downButton.setIcon(ARROW_DOWN);
		downButton.setAlignmentY(CENTER_ALIGNMENT);
		upButton = new JButton();
		upButton.setIcon(ARROW_UP);
		upButton.setAlignmentY(CENTER_ALIGNMENT);
		buttons.setLayout(new BoxLayout(buttons,BoxLayout.Y_AXIS));
		buttons.add(downButton);
		buttons.add(upButton);
		
		ArrowsListener aL = new ArrowsListener();
		downButton.addActionListener(aL);
		upButton.addActionListener(aL);
		
		sortPane.add(buttons);
	}
	
	private void createButtonsPane(){
		buttonsPane = new JPanel();
		buttonsPane.setLayout(new GridLayout(1,2));
		accept = new JButton("Accept",SortTracksDialog.IMAGE_ACCEPT);
		accept.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				accept();
			}
		});
		cancel = new JButton("Cancel",SortTracksDialog.IMAGE_CANCEL);
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SortTracksDialog.this.status = SortTracksDialog.STATUS_CANCEL;
				SortTracksDialog.this.dispose();
			}
		});
		buttonsPane.add(accept);
		buttonsPane.add(cancel);
	}
	
	private void accept(){
		 this.status = SortTracksDialog.STATUS_ACCEPT;
		 this.copyNewLists();
		 this.dispose();
	}
	
	private class ArrowsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			int selectedIndex = 0;
			if (SortTracksDialog.this.tracksJList.isSelectionEmpty()) {
				return;
			} else {
				selectedIndex = SortTracksDialog.this.tracksJList
					.getSelectedIndex();
			}

			if (arg0.getSource().equals(SortTracksDialog.this.downButton)){
				if (selectedIndex >= 0 && selectedIndex < SortTracksDialog.this.files.size()-1){
					SortTracksDialog.this.changePositions(selectedIndex,selectedIndex+1);
					String	tracksData[] = new String[genomeBrowser.getFiles().size()];

					int i = 0;
					for (File f : SortTracksDialog.this.files){
						tracksData[i++] = f.getName();
					}
					DefaultListModel<String> model = new DefaultListModel<>();
					model.setSize(tracksData.length);
					for (i = 0; i< tracksData.length; i++){
						model.setElementAt(tracksData[i], i);
					}
					tracksJList.setModel(model);
					tracksJList.setSelectedIndex(selectedIndex+1);
					SortTracksDialog.this.repaint();
				}

			} else 	if (arg0.getSource().equals(SortTracksDialog.this.upButton)){
				if (selectedIndex > 0 && selectedIndex < SortTracksDialog.this.files.size()){
					SortTracksDialog.this.changePositions(selectedIndex-1,selectedIndex);
					String	tracksData[] = new String[SortTracksDialog.this.files.size()];
					int i = 0;
					for (File f : SortTracksDialog.this.files){
						tracksData[i++] = f.getName();
					}
					DefaultListModel<String> model = new DefaultListModel<>();
					model.setSize(tracksData.length);
					for (i = 0; i< tracksData.length; i++){
						model.setElementAt(tracksData[i], i);
					}
					tracksJList.setModel(model);
					tracksJList.setSelectedIndex(selectedIndex-1);
					SortTracksDialog.this.repaint();
				}
			}
		}
		
	}

	private void changePositions(int position1, int position2) {
		if (position1 < 0 || position1 >= this.files.size() || position2 < 0
			|| position2 >= this.files.size())
			return;

		File file1 = this.files.get(position1);
		File file2 = this.files.get(position2);
		this.files.set(position1, file2);
		this.files.set(position2, file1);
		Painter painter1 = this.painters.get(position1);
		Painter painter2 = this.painters.get(position2);
		this.painters.set(position1, painter2);
		this.painters.set(position2, painter1);
	}

	public int getStatus() {
		return this.status;
	}
}
