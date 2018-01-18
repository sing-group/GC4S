package org.sing_group.gc4s.demo;

import static org.sing_group.gc4s.visualization.VisualizationUtils.showComponent;

import org.sing_group.gc4s.ui.icons.IconsPanel;

/**
 * This demo shows the available icons at {@code Icons}.
 * 
 * @author hlfernandez
 *
 */
public class IconsDemo {

	public static void main(String[] args) {
		showComponent(new IconsPanel(), "GC4S icons");
	}
}