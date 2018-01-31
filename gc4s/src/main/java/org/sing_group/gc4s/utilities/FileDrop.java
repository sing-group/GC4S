/*
 * #%L
 * GC4S components
 * %%
 * Copyright (C) 2014 - 2018 Hugo López-Fernández, Daniel Glez-Peña, Miguel Reboiro-Jato,
 * 			Florentino Fdez-Riverola, Rosalía Laza-Fidalgo, Reyes Pavón-Rial
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
package org.sing_group.gc4s.utilities;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.TooManyListenersException;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;

/**
 * <p>
 * This class makes it easy to drag and drop files from the operating system to
 * a Java program. Any {@code Component} can be dropped onto, but only
 * {@code JComponent}s will indicate the drop event with a changed border.
 * </p>
 *
 * <p>
 * To use this class, construct a new {@code FileDrop} by passing it the target
 * component and a {@code Listener} to receive notification when file(s) have
 * been dropped.
 * </p>
 *
 * <p>
 * You can specify the border that will appear when files are being dragged by
 * calling the constructor with a {@code Border} Only {@code JComponent}s will
 * show any indication with a border.
 * </p>
 *
 * <p>
 * You can turn on some debugging features by passing a <code>PrintStream</code>
 * object (such as {@code System.out}) into the full constructor. A {@code null}
 * value will result in no extra debugging information being output.
 * </p>
 *
 * @author hlfernandez
 */
public class FileDrop {
	private static String ZERO_CHAR_STRING = "" + (char) 0;
	private static Boolean supportsDnD;
	private static Color defaultBorderColor = new Color(0f, 0f, 1f, 0.25f);

	private transient Border normalBorder;
	private transient DropTargetListener dropListener;

	/**
	 * Constructs a {@link FileDrop} with a default light-blue border and, if
	 * {@code c} is a {@link Container}, recursively sets all
	 * elements contained within as drop targets, though only the top level
	 * container will change borders.
	 *
	 * @param c the component on which files will be dropped
	 * @param listener listens for {@code filesDropped}
	 */
	public FileDrop(final Component c, final FileDropListener listener) {
		this(
			null, c,
			BorderFactory.createMatteBorder(2, 2, 2, 2, defaultBorderColor),
			true, listener
		);
	}

	/**
	 * Constructor with a default border and the option to recursively set drop
	 * targets. If your component is a {@code Container}, then each of
	 * its children components will also listen for drops, though only the
	 * parent will change borders.
	 *

	 * @param c the component on which files will be dropped
	 * @param recursive recursively set children as drop targets
	 * @param listener listens for {@code filesDropped}
	 */
	public FileDrop(final Component c, final boolean recursive,
		final FileDropListener listener) {
		this(
			null, c,
			BorderFactory.createMatteBorder(2, 2, 2, 2, defaultBorderColor),
			recursive, listener
		);
	}

	/**
	 * Constructor with a default border and debugging optionally turned on.
	 * With Debugging turned on, more status messages will be displayed to
	 * {@code out}. A common way to use this constructor is with
	 * {@code System.out} or {@code System.err}. A {@code null} value for the
	 * parameter {@code out} will result in no debugging output.
	 *
	 * @param out PrintStream to record debugging info or null for no debugging
	 * @param c the component on which files will be dropped
	 * @param listener listens for {@code filesDropped}
	 */
	public FileDrop(final PrintStream out, final Component c,
		final FileDropListener listener) {
		this(
			out, c,
			BorderFactory.createMatteBorder(2, 2, 2, 2, defaultBorderColor),
			false, listener
		);
	}

	/**
	 * Constructor with a default border, debugging optionally turned on and the
	 * option to recursively set drop targets. If your component is a
	 * {@code Container}, then each of its children components will
	 * also listen for drops, though only the parent will change borders. With
	 * Debugging turned on, more status messages will be displayed to
	 * {@code out}. A common way to use this constructor is with
	 * {@code System.out} or {@code System.err}. A {@code null} value for the
	 * parameter {@code out} will result in no debugging output.
	 *
	 * @param out PrintStream to record debugging info or null for no debugging
	 * @param c the Component on which files will be dropped
	 * @param recursive recursively set children as drop targets
	 * @param listener listens for {@code filesDropped}
	 */
	public FileDrop(final PrintStream out, final Component c,
		final boolean recursive, final FileDropListener listener) {
		this(
			out, c,
			BorderFactory.createMatteBorder(2, 2, 2, 2, defaultBorderColor),
			recursive, listener
		);
	}

    /**
     * Constructor with a specified border.
     *
     * @param c the Component on which files will be dropped
     * @param dragBorder the order to use on {@code JComponent} when dragging
     * 		  occurs
     * @param listener listens for {@code filesDropped}
     */
	public FileDrop(final Component c,
		final Border dragBorder, final FileDropListener listener) {
		this(null, c, dragBorder, false, listener);
	}

	/**
	 * Constructor with a specified border and the option to recursively set
	 * drop targets. If your component is a {@code Container}, then
	 * each of its children components will also listen for drops, though only
	 * the parent will change borders.
	 *
	 * @param c the Component on which files will be dropped.
	 * @param dragBorder the Border to use on {@code JComponent} when dragging
	 *        occurs
	 * @param recursive recursively set children as drop targets
	 * @param listener listens for {@code filesDropped}.
	 */
	public FileDrop(final Component c,
		final Border dragBorder, final boolean recursive,
		final FileDropListener listener
	) {
		this(null, c, dragBorder, recursive, listener);
	}

    /**
     * Constructor with a specified border and debugging optionally turned on.
     * With Debugging turned on, more status messages will be displayed to
     * {@code out}. A common way to use this constructor is with
     * {@code System.out} or {@code System.err}. A {@code null} value for
     * the parameter {@code out} will result in no debugging output.
     *
     * @param out PrintStream to record debugging info or null for no debugging
     * @param c the Component on which files will be dropped
     * @param dragBorder the Border to use on {@code JComponent} when dragging
     *        occurs
     * @param listener Listens for {@code filesDropped}
     */
	public FileDrop(final PrintStream out, final Component c,
		final Border dragBorder,
		final FileDropListener listener
	) {
		this(out, c, dragBorder, false, listener);
	}

    /**
     * Full constructor with a specified border and debugging optionally turned on.
     * With Debugging turned on, more status messages will be displayed to
     * {@code out}. A common way to use this constructor is with
     * {@code System.out} or {@code System.err}. A {@code null} value for
     * the parameter {@code out} will result in no debugging output.
     *
     * @param out PrintStream to record debugging info or null for no debugging
     * @param c the component on which files will be dropped
     * @param dragBorder the border to use on {@code JComponent} when dragging
     *        occurs
     * @param recursive the recursively set children as drop targets
     * @param listener Listens for {@code filesDropped}
     */
	public FileDrop(final PrintStream out, final Component c,
		final Border dragBorder, final boolean recursive,
		final FileDropListener listener
	) {
		if (supportsDnD()) {
			dropListener = new DropTargetListener() {
				public void dragEnter(DropTargetDragEvent evt) {
					log(out, "FileDrop: dragEnter event.");

					if (isDragOk(out, evt)) {
						if (c instanceof JComponent) {
							JComponent jc = (JComponent) c;
							normalBorder = jc.getBorder();
							log(out, "FileDrop: normal border saved.");
							jc.setBorder(dragBorder);
							log(out, "FileDrop: drag border set.");
						}

						evt.acceptDrag(DnDConstants.ACTION_COPY);
						log(out, "FileDrop: event accepted.");
					} else {
						evt.rejectDrag();
						log(out, "FileDrop: event rejected.");
					}
				}

				public void dragOver(DropTargetDragEvent evt) {
				}

				public void drop(DropTargetDropEvent evt) {
					log(out, "FileDrop: drop event.");
					try {
						Transferable tr = evt.getTransferable();

						if (tr.isDataFlavorSupported(
								DataFlavor.javaFileListFlavor)) {

							evt.acceptDrop(DnDConstants.ACTION_COPY);
							log(out, "FileDrop: file list accepted.");

							List<?> fileList = (List<?>) tr.getTransferData(
									DataFlavor.javaFileListFlavor);

							File[] filesTemp = new File[fileList.size()];
							fileList.toArray(filesTemp);
							final File[] files = filesTemp;

							if (listener != null)
								listener.filesDropped(files);

							evt.getDropTargetContext().dropComplete(true);
							log(out, "FileDrop: drop complete.");
						} else {

							DataFlavor[] flavors = tr.getTransferDataFlavors();
							boolean handled = false;
							for (int zz = 0; zz < flavors.length; zz++) {
								if (flavors[zz].isRepresentationClassReader()) {

									evt.acceptDrop(DnDConstants.ACTION_COPY);
									log(out, "FileDrop: reader accepted.");

									Reader reader = flavors[zz]
											.getReaderForText(tr);

									BufferedReader br = new BufferedReader(
											reader);

									if (listener != null)
										listener.filesDropped(
												createFileArray(br, out));

									evt.getDropTargetContext()
											.dropComplete(true);
									log(out, "FileDrop: drop complete.");
									handled = true;
									break;
								}
							}
							if (!handled) {
								log(out, "FileDrop: not a file list or reader - abort.");
								evt.rejectDrop();
							}

						}
					} catch (IOException io) {
						log(out, "FileDrop: IOException - abort:");
						io.printStackTrace(out);
						evt.rejectDrop();
					} catch (UnsupportedFlavorException ufe) {
						log(out, "FileDrop: UnsupportedFlavorException - abort:");
						ufe.printStackTrace(out);
						evt.rejectDrop();
					} finally {

						if (c instanceof JComponent) {
							JComponent jc = (JComponent) c;
							jc.setBorder(normalBorder);
							log(out, "FileDrop: normal border restored.");
						}
					}
				}

				public void dragExit(DropTargetEvent evt) {
					log(out, "FileDrop: dragExit event.");

					if (c instanceof JComponent) {
						JComponent jc = (JComponent) c;
						jc.setBorder(normalBorder);
						log(out, "FileDrop: normal border restored.");
					}
				}

				public void dropActionChanged(DropTargetDragEvent evt) {
					log(out, "FileDrop: dropActionChanged event.");

					if (isDragOk(out, evt)) {
						evt.acceptDrag(DnDConstants.ACTION_COPY);
						log(out, "FileDrop: event accepted.");
					} else {
						evt.rejectDrag();
						log(out, "FileDrop: event rejected.");
					}
				}
			};

			makeDropTarget(out, c, recursive);
		} else {
			log(out, "FileDrop: Drag and drop is not supported with this JVM");
		}
	}

	private static boolean supportsDnD() {
		if (supportsDnD == null) {
			boolean support = false;
			try {
				Class.forName("java.awt.dnd.DnDConstants");
				support = true;
			} catch (Exception e) {
				support = false;
			}
			supportsDnD = new Boolean(support);
		}
		return supportsDnD.booleanValue();
	}

	private static File[] createFileArray(BufferedReader bReader,
			PrintStream out) {
		try {
			List<File> list = new ArrayList<File>();
			String line = null;
			while ((line = bReader.readLine()) != null) {
				try {

					if (ZERO_CHAR_STRING.equals(line))
						continue;

					File file = new File(new URI(line));
					list.add(file);
				} catch (Exception ex) {
					log(out, "Error with " + line + ": " + ex.getMessage());
				}
			}

			return (File[]) list.toArray(new File[list.size()]);
		} catch (IOException ex) {
			log(out, "FileDrop: IOException");
		}
		return new File[0];
	}

	private void makeDropTarget(final PrintStream out, final Component c,
		boolean recursive) {

		final DropTarget dt = new DropTarget();
		try {
			dt.addDropTargetListener(dropListener);
		} catch (TooManyListenersException e) {
			e.printStackTrace();
			log(out, "FileDrop: Drop will not work due to previous error. Do you have another listener attached?");
		}

		c.addHierarchyListener(new HierarchyListener() {
			public void hierarchyChanged(HierarchyEvent evt) {
				log(out, "FileDrop: Hierarchy changed.");
				Component parent = c.getParent();
				if (parent == null) {
					c.setDropTarget(null);
					log(out, "FileDrop: Drop target cleared from component.");
				} else {
					new DropTarget(c, dropListener);
					log(out, "FileDrop: Drop target added to component.");
				}
			}
		});
		if (c.getParent() != null)
			new DropTarget(c, dropListener);

		if (recursive && (c instanceof Container)) {

			Container cont = (Container) c;

			Component[] comps = cont.getComponents();

			for (int i = 0; i < comps.length; i++)
				makeDropTarget(out, comps[i], recursive);
		}
	}

	private boolean isDragOk(final PrintStream out,
		final DropTargetDragEvent evt) {
		boolean ok = false;

		DataFlavor[] flavors = evt.getCurrentDataFlavors();

		int i = 0;
		while (!ok && i < flavors.length) {

			final DataFlavor curFlavor = flavors[i];
			if (curFlavor.equals(DataFlavor.javaFileListFlavor)
					|| curFlavor.isRepresentationClassReader()) {
				ok = true;
			}

			i++;
		}

		if (out != null) {
			if (flavors.length == 0)
				log(out, "FileDrop: no data flavors.");
			for (i = 0; i < flavors.length; i++)
				log(out, flavors[i].toString());
		}

		return ok;
	}

	private static void log(PrintStream out, String message) {
		if (out != null) {
			out.println(message);
		}
	}

    /**
     * Removes the drag-and-drop hooks from the component and optionally
     * from the all children. You should call this if you add and remove
     * components after you've set up the drag-and-drop.
     * This will recursively unregister all components contained within
     * {@code c} if {@code c} is a {@link Container}.
     *
     * @param c the component to unregister as a drop target
     * @return {@code true} if remove succeeds
     */
	public static boolean remove(Component c) {
		return remove(null, c, true);
	}

    /**
     * Removes the drag-and-drop hooks from the component and optionally
     * from the all children. You should call this if you add and remove
     * components after you've set up the drag-and-drop.
     *
     * @param out pptional {@link PrintStream} for logging drag and drop messages
     * @param c the component to unregister
     * @param recursive recursively unregister components within a container
     * @return {@code true} if remove succeeds
     */
	public static boolean remove(PrintStream out, Component c,
		boolean recursive
	) {
		if (supportsDnD()) {
			log(out, "FileDrop: Removing drag-and-drop hooks.");
			c.setDropTarget(null);
			if (recursive && (c instanceof Container)) {
				Component[] comps = ((Container) c).getComponents();
				for (int i = 0; i < comps.length; i++)
					remove(out, comps[i], recursive);
				return true;
			} else
				return false;
		} else
			return false;
	}
}
