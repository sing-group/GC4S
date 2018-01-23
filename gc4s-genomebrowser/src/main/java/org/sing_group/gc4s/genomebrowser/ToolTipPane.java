package org.sing_group.gc4s.genomebrowser;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.swing.JPanel;

import org.sing_group.gc4s.genomebrowser.grid.GridInfo;

/**
 * A panel to display {@code GridInfo} as tooltips.
 * 
 * @author hlfernandez
 *
 */
public class ToolTipPane extends JPanel{
	private static final long serialVersionUID = 1L;

	private GridInfo gridInfo;
	private boolean wait = false;
	private GenomeBrowser genomeBrower;

	private int Y_OFFSET = 24;
	private int Y_OFFSET_STRING = 15;

	/**
	 * Creates a new {@code ToolTipPane} for displaying tooltips at the 
	 * specified {@code GenomeBrowser}.
	 * 
	 * @param genomeBrowser a {@code GenomeBrowser} instance
	 */
	public ToolTipPane(GenomeBrowser genomeBrowser) {
		super();
		this.genomeBrower = genomeBrowser;
	}

	/**
	 * Sets the current {@code GridInfo}.
	 *  
	 * @param gridInfo a {@code GridInfo} object
	 */
	public void setCuadriculaInfo(GridInfo gridInfo) {
		this.gridInfo = gridInfo;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		Font f = new Font("Courier", Font.PLAIN, 11);
		g2.setFont(f);
		if (gridInfo != null) {
			double xPos;
			double yPos;
			Color customColor = gridInfo.getData().getToolTipColor();
			if (gridInfo.getType() == GridInfo.FILENAME) {

				try {
					FontMetrics fm = g2.getFontMetrics();
					g2.setColor(Color.black);
					xPos = gridInfo.getCoordinates().getX();
					yPos = gridInfo.getCoordinates().getY();
					Rectangle2D rectangle = new Rectangle2D.Double(xPos, yPos,
						30 + fm
							.stringWidth(gridInfo.getData().toString()),
						Y_OFFSET);
					g2.draw(rectangle);
					g2.setColor(customColor);
					Area a = new Area(rectangle);
					g2.fill(a);
					g2.setColor(Color.black);
					g2.drawString(gridInfo.getData().toString(),
						(int) gridInfo.getCoordinates().getX() + 15,
						(int) gridInfo.getCoordinates().getY()
							+ Y_OFFSET_STRING);

				} catch (NullPointerException ex) {
					 ex.printStackTrace();
				}
			}
			else if (gridInfo.getType() == GridInfo.TRACKINFO) {
				FontMetrics fm = g2.getFontMetrics();
				g2.setColor(Color.black);
				xPos = gridInfo.getCoordinates().getX();
				yPos = gridInfo.getCoordinates().getY();
				Rectangle2D rectangle = new Rectangle2D.Double(xPos, yPos,
					30 + fm.stringWidth(gridInfo.getData().toString()),
					Y_OFFSET);
				g2.draw(rectangle);
				g2.setColor(customColor);
				Area a = new Area(rectangle);
				g2.fill(a);
				g2.setColor(Color.black);
				g2.drawString(gridInfo.getData().toString(),
					(int) gridInfo.getCoordinates().getX() + 15,
					(int) gridInfo.getCoordinates().getY()
						+ Y_OFFSET_STRING);
			} else if (gridInfo.getType() == GridInfo.GENOMEBROWSERINFO) {
				FontMetrics fm = g2.getFontMetrics();
				g2.setColor(Color.black);
				int lineWidth = fm
					.stringWidth(gridInfo.getData().toString());
				Rectangle2D rectangle = new Rectangle2D.Double(
					gridInfo.getCoordinates().getX() - lineWidth,
					gridInfo.getCoordinates().getY(), 30 + lineWidth,
					Y_OFFSET);
				g2.draw(rectangle);
				g2.setColor(customColor);
				Area a = new Area(rectangle);
				g2.fill(a);
				g2.setColor(Color.black);
				g2.drawString(gridInfo.getData().toString(),
					(int) gridInfo.getCoordinates().getX() - lineWidth
						+ 15,
					(int) gridInfo.getCoordinates().getY()
						+ Y_OFFSET_STRING);
			} else if (gridInfo.getType() == GridInfo.INTERVALINFO) {
				FontMetrics fm = g2.getFontMetrics();
				g2.setColor(Color.black);
				int lineWidth = fm
					.stringWidth(gridInfo.getData().toString());
				xPos = gridInfo.getCoordinates().getX() - lineWidth;
				yPos = gridInfo.getCoordinates().getY();
				if (xPos < 0)
					xPos += lineWidth;
				Rectangle2D rectangle = new Rectangle2D.Double(xPos, yPos,
					30 + lineWidth, Y_OFFSET);
				g2.draw(rectangle);
				g2.setColor(customColor);
				Area a = new Area(rectangle);
				g2.fill(a);
				g2.setColor(Color.black);
				g2.drawString(gridInfo.getData().toString(),
					(int) xPos + 15,
					(int) gridInfo.getCoordinates().getY()
						+ Y_OFFSET_STRING);
			} else if (gridInfo.getType() == GridInfo.PILEUPINFO) {
				FontMetrics fm = g2.getFontMetrics();
				g2.setColor(Color.black);
				List<String> columns = gridInfo.getData().getLines();
				double width = getMaxLineWidth(columns, fm);
				xPos = gridInfo.getCoordinates().getX() - 30;
				yPos = gridInfo.getCoordinates().getY()
					- Y_OFFSET_STRING * columns.size() - 5;
				if (xPos + width + 30 > this.genomeBrower.getTracksPanel()
					.getX(975))
					xPos -= width;
				Rectangle2D rectangle = new Rectangle2D.Double(xPos, yPos,
					30 + width, 5 + Y_OFFSET_STRING * columns.size());
				Stroke old = g2.getStroke();
				g2.setStroke(new BasicStroke(0.5f));
				g2.setColor(Color.BLACK);
				g2.draw(rectangle);
				g2.setStroke(old);
				g2.setColor(customColor);
				Area a = new Area(rectangle);
				g2.fill(a);
				g2.setColor(Color.black);
				int line = 1;
				for (String column : columns) {
					g2.drawString(column, (int) xPos + 5,
						(int) yPos + Y_OFFSET_STRING * line);
					line++;
				}
			}
		}

		if (wait) {
			g2.setColor(Color.WHITE);
			Rectangle2D r2 = new Rectangle2D.Float(0.0f, 0.0f, this.getWidth(),
				this.getHeight());
			g2.fill(r2);
			int inicio = genomeBrower.getScroll().getVerticalScrollBar()
				.getValue();
			f = new Font("Arial", Font.BOLD, 26);
			g2.setFont(f);
			FontMetrics fm = g2.getFontMetrics();
			g2.setColor(Color.GRAY);
			g2.drawString("Rendering...",
				(this.getWidth() / 2) - (fm.stringWidth("Rendering...") / 2),
				inicio + 100);
			f = new Font("Arial", Font.BOLD + Font.ITALIC, 16);
			g2.setFont(f);
			fm = g2.getFontMetrics();
			String msg = new String(
				"Note: if you are in a wide range, this operation may take a few minutes");
			g2.drawString(msg,
				(this.getWidth() / 2) - (fm.stringWidth(msg) / 2),
				inicio + 120);

		}
	}

	private double getMaxLineWidth(List<String> columns, FontMetrics fm) {
		double toret = 0;
		for (String line : columns) {
			double width = fm.stringWidth(line);
			if (width > toret)
				toret = width;
		}
		return toret;
	}

	/**
	 * Shows the wait message.
	 */
	public void enableWait() {
		this.wait = true;
		this.repaint();
	}

	/**
	 * Hides the wait message.
	 */
	public void disableWait() {
		this.wait = false;
		this.repaint();
	}
}
