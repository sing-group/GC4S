/*
 * #%L
 * GC4S genome browser
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
package org.sing_group.gc4s.genomebrowser;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

/**
 * A panel to draw dragging rectangles.
 * 
 * @author hlfernandez
 *
 */
public class DraggingPane extends JPanel {
	private static final long serialVersionUID = 1L;

	private int xStart = 0;
	private int yStart = 0;
	private int xStop = 0;
	private int yStop = 0;

	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Rectangle2D rectangle;
		if (xStart < xStop) {
			if (yStart < yStop) {
				rectangle = new Rectangle2D.Double(xStart, yStart,
					xStop - xStart, yStop - yStart);
			} else {
				rectangle = new Rectangle2D.Double(xStart, yStop,
					xStop - xStart, yStart - yStop);
			}
		} else {
			if (yStart < yStop) {
				rectangle = new Rectangle2D.Double(xStop, yStart,
					xStart - xStop, yStop - yStart);
			} else {
				rectangle = new Rectangle2D.Double(xStop, yStop, xStart - xStop,
					yStart - yStop);
			}
		}
		g2.setColor(Color.GRAY);
		g2.draw(rectangle);
	}

	public void reset() {
		xStart = 0;
		xStop = 0;
		yStart = 0;
		yStop = 0;
	}

	public int getxStart() {
		return xStart;
	}

	public void setxStart(int xStart) {
		this.xStart = xStart;
	}

	public int getyStart() {
		return yStart;
	}

	public void setyStart(int yStart) {
		this.yStart = yStart;
	}

	public int getxStop() {
		return xStop;
	}

	public void setxStop(int xStop) {
		this.xStop = xStop;
	}

	public int getyStop() {
		return yStop;
	}

	public void setyStop(int yStop) {
		this.yStop = yStop;
	}
}
