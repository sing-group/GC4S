package es.uvigo.ei.sing.hlfernandez.utilities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * Provides functionalities to deal with {@link ImageIO}.
 * 
 * @author hlfernandez
 *
 */
public class ImageIOUtils {
	
	/**
	 * <p>
	 * Exports the given {@code component} as a image into {@code file}.
	 * </p>
	 * 
	 * <p>The image format is specified by {@code format}, which must be one
	 * of the available formats in the system (they can be obtained with
	 * {@link ImageIO#getWriterFormatNames()}.
	 * 
	 * @param component the component to save.
	 * @param format the image format.
	 * @param file the file to save the image.
	 * @throws IOException if an error occurs while saving the image.
	 */
	public static void toImage(JComponent component, String format, File file)
		throws IOException {

		int w = component.getWidth();
		int h = component.getHeight();

		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = bi.createGraphics();
		component.paint(g2);
		g2.dispose();

		ImageIO.write(bi, format, file);
	}
}
