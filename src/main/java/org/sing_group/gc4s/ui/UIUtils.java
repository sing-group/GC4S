package org.sing_group.gc4s.ui;

import java.awt.Color;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JComponent;

/**
 * Provides functionalities to deal with UI components.
 * 
 * @author hlfernandez
 *
 */
public class UIUtils {
	
	/**
	 * Applies {@code consumer} to {@code aComponent} and all its
	 * {@code JComponent}s.
	 * 
	 * @param aComponent
	 *            a {@code JComponent}.
	 * @param consumer
	 *            the function to apply.
	 */
	public static void applyRecursive(JComponent aComponent,
		Consumer<JComponent> consumer
	) {
		consumer.accept(aComponent);
		getJComponents(aComponent).forEach(c2 -> applyRecursive(c2, consumer));
	}

	/**
	 * Sets the background color to {@code aComponent} and all its
	 * {@code JComponent}s.
	 * 
	 * @param aComponent
	 *            a {@code JComponent}.
	 * @param bg
	 *            a {@code Color}.
	 */
	public static void setBackgroundColorRecursive(JComponent aComponent,
		Color bg
	) {
		applyRecursive(aComponent, c -> c.setBackground(bg));
	}

	/**
	 * Sets the opacity to {@code aComponent} and all its {@code JComponent}s.
	 * 
	 * @param aComponent
	 *            a {@code JComponent}.
	 * @param opaque
	 *            a boolean indicating the opacity.
	 */
	public static void setOpaqueRecursive(JComponent aComponent, boolean opaque) {
		applyRecursive(aComponent, c -> c.setOpaque(opaque));
	}

	/**
	 * Returns a {@code List} of the {@code JComponent}s inside
	 * {@code aComponent}.
	 * 
	 * @param aComponent
	 *            a {@code JComponent}.
	 * @return a {@code List} of the {@code JComponent}s inside
	 *         {@code aComponent}.
	 */
	public static List<JComponent> getJComponents(JComponent aComponent) {
		return 	Stream.of(aComponent.getComponents())
				.filter(c -> c instanceof JComponent)
				.map(c -> (JComponent) c)
				.collect(Collectors.toList());
	}
}
