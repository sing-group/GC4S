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
package org.sing_group.gc4s.visualization;

import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.sing_group.gc4s.utilities.StringMetrics;

/**
 * This component draws a color key map between two colors.
 * 
 * @author hlfernandez
 *
 */
public class ColorKeyLegend extends Canvas {
	private static final long serialVersionUID = 1L;
	
	private static final int WIDTH 	= 300;
	private static final int HEIGHT = 100;
	private static final Dimension SIZE = new Dimension(WIDTH, HEIGHT);
	
	private static final int LEGEND_WIDTH 	= 250;
	private static final int LEGEND_HEIGHT 	= 65;
	
	private Color lowColor;
	private Color highColor;
	private double low;
	private double high;

	private DecimalFormat decimalFormat = new DecimalFormat("0.0"); 

	/**
	 * Creates a new {@code ColorKeyPanel} between the colors and the range
	 * specified.
	 * 
	 * @param lowColor the low color of the gradient.
	 * @param highColor the high color of the gradient.
	 * @param lowValue the low value of the range.
	 * @param highValue the high value of the range.
	 */
	public ColorKeyLegend(Color lowColor, Color highColor, double lowValue,
		double highValue
	) {
		this.lowColor = lowColor;
		this.highColor = highColor;
		this.low = lowValue;
		this.high = highValue;
		
		this.init();
	}

	private void init() {
		this.setMinimumSize(SIZE);
		this.setPreferredSize(SIZE);
		this.setSize(SIZE);
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setFont(getFont());
		
		g2.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);

		g2.setPaint(Color.gray);
		int x = (WIDTH - LEGEND_WIDTH) / 2;
		int y = 0;

		GradientPaint gradient = new GradientPaint(
			x, y, this.lowColor, LEGEND_WIDTH, y, this.highColor);
		g2.setPaint(gradient);
		
		g2.fill(new RoundRectangle2D.Double(
			x, y, LEGEND_WIDTH, LEGEND_HEIGHT, 0, 0));
		g2.setPaint(Color.black);
		
		g2.drawLine(x, LEGEND_HEIGHT + 2, x, LEGEND_HEIGHT + 12);
		g2.drawLine(x + LEGEND_WIDTH / 2, LEGEND_HEIGHT + 2, 
			x + LEGEND_WIDTH / 2, LEGEND_HEIGHT + 12);
		g2.drawLine(x + LEGEND_WIDTH - 1, LEGEND_HEIGHT + 2, 
			x + LEGEND_WIDTH - 1, LEGEND_HEIGHT + 12);
		
		StringMetrics s = new StringMetrics(g2, g2.getFont());
		
		String min = getLowValue();
		double height = s.getHeight(min);
		double width = s.getWidth(min);
		g2.drawString(min, (float) (x - (width / 2)), 
			(float) (LEGEND_HEIGHT + 12 + 2 + height));
		
		String middle = getMiddle();
		height = s.getHeight(middle);
		width = s.getWidth(middle);
		g2.drawString(middle, (float) (x + LEGEND_WIDTH / 2 - width / 2), 
			(float) (LEGEND_HEIGHT + 12 + 2 + height));
		
		String max = getHighValue();
		height = s.getHeight(max);
		width = s.getWidth(max);
		
		g2.drawString(max, (float) (x + LEGEND_WIDTH - 1 - (width / 2)), 
			(float) (LEGEND_HEIGHT + 12 + 2 + height));
	}
	
	private String getMiddle(){
		return format((low + high) / 2);
	}

	private String getLowValue() {
		return format(this.low);
	}
	
	private String getHighValue() {
		return format(this.high);
	}
	
	private String format(double d) {
		return getDecimalFormatter().format(d);
	}

	private NumberFormat getDecimalFormatter() {
		return decimalFormat;
	}

	/**
	 * Sets the low color of the gradient.
	 * 
	 * @param color the low color of the gradient.
	 */
	public void setLowColor(Color color) {
		this.lowColor = color;	
		this.repaint();
	}
	
	/**
	 * Sets the low high of the gradient.
	 * 
	 * @param color the high color of the gradient.
	 */
	public void setHighColor(Color color) {
		this.highColor = color;
		this.repaint();
	}

	/**
	 * Sets the low value of the gradient.
	 * 
	 * @param low the low value of the gradient.
	 */
	public void setLowValue(double low) {
		this.low = low;
		this.repaint();
	}
	
	/**
	 * Sets the high value of the gradient.
	 * 
	 * @param high the low value of the gradient.
	 */
	public void setHighValue(double high) {
		this.high = high;
		this.repaint();
	}

	/**
	 * Sets the decimal format to format gradient values.
	 * 
	 * @param decimalFormat the decimal format to format gradient values.
	 */
	public void setDecimalFormat(DecimalFormat decimalFormat) {
		this.decimalFormat = decimalFormat;
		this.repaint();
	}
	
	@Override
	public void setFont(Font f) {
		super.setFont(f);
		this.repaint();
	}
}