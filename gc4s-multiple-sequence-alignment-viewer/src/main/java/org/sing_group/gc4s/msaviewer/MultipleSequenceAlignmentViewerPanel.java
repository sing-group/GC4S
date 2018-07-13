/*
 * #%L
 * GC4S multiple sequence alignment viewer
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
package org.sing_group.gc4s.msaviewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import org.sing_group.gc4s.utilities.ImageIOUtils;

/**
 * A panel that displays the aligned sequences.
 *
 * @author hlfernandez
 * @author mrjato
 *
 */
public class MultipleSequenceAlignmentViewerPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final String ALIGNMENT_TEMPLATE = "alignment.template.html";

	private static final MultipleSequenceAlignmentTracksModel PLAIN_MODEL =
		new MultipleSequenceAlignmentTracksModel() {

			@Override
			public String getName() {
				return "";
			}

			@Override
			public List<Track> getUpperTracks() {
				return Collections.emptyList();
			}

			@Override
			public List<Track> getBottomTracks() {
				return Collections.emptyList();
			}
		};


	private HTMLEditorKit editor;
	private final HTMLDocument document;
	private final JEditorPane teDocument;

	private final List<Sequence> sequences;
	private MultipleSequenceAlignmentTracksModel model;
	private SequenceAlignmentRenderer alignmentRenderer;
	private MultipleSequenceAlignmentViewerConfiguration configuration;
	private boolean mouseZoomEnabled = true;

	/**
	 * Creates a new {@code MultipleSequenceAlignmentViewerPanel} with the
	 * specified sequences and default values for configuration, tracks model
	 * and sequence alignment renderer.
	 *
	 * Note that all sequences in the list must have the same length.
	 *
	 * @param alignedSequences a list of {@code Sequence}
	 */
	public MultipleSequenceAlignmentViewerPanel(
		final List<Sequence> alignedSequences
	) {
		this(alignedSequences, PLAIN_MODEL,
			new DefaultSequenceAlignmentRenderer(),
			new MultipleSequenceAlignmentViewerConfiguration());
	}

	/**
	 * Creates a new {@code MultipleSequenceAlignmentViewerPanel} with the
	 * specified sequences and configuration and default values for tracks model
	 * and sequence alignment renderer.
	 *
	 * Note that all sequences in the list must have the same length.
	 *
	 * @param alignedSequences a list of {@code Sequence}
	 * @param configuration the {@code MultipleSequenceAlignmentViewerConfiguration}
	 */
	public MultipleSequenceAlignmentViewerPanel(
		final List<Sequence> alignedSequences,
		final MultipleSequenceAlignmentViewerConfiguration configuration
	) {
		this(alignedSequences, PLAIN_MODEL,
			new DefaultSequenceAlignmentRenderer(), configuration);
	}

	/**
	 * Creates a new {@code MultipleSequenceAlignmentViewerPanel} with the
	 * specified sequences and tracks model and default values for configuration
	 * and sequence alignment renderer.
	 *
	 * Note that all sequences in the list must have the same length.
	 *
	 * @param alignedSequences a list of {@code Sequence}
	 * @param model the {@code MultipleSequenceAlignmentTracksModel}
	 */
	public MultipleSequenceAlignmentViewerPanel(
		final List<Sequence> alignedSequences,
		final MultipleSequenceAlignmentTracksModel model
	) {
		this(alignedSequences, model, new DefaultSequenceAlignmentRenderer(),
			new MultipleSequenceAlignmentViewerConfiguration());
	}

	/**
	 * Creates a new {@code MultipleSequenceAlignmentViewerPanel} with the
	 * specified sequences, tracks model and sequence alignment renderer and
	 * default values for configuration.
	 *
	 * Note that all sequences in the list must have the same length.
	 *
	 * @param alignedSequences a list of {@code Sequence}
	 * @param model the {@code MultipleSequenceAlignmentTracksModel}
	 * @param positionRenderer the {@code SequenceAlignmentRenderer}
	 */
	public MultipleSequenceAlignmentViewerPanel(
		final List<Sequence> alignedSequences,
		final MultipleSequenceAlignmentTracksModel model,
		final SequenceAlignmentRenderer positionRenderer
	) {
		this(alignedSequences, model, positionRenderer,
			new MultipleSequenceAlignmentViewerConfiguration());
	}

	/**
	 * Creates a new {@code MultipleSequenceAlignmentViewerPanel} with the
	 * specified sequences, configuration, tracks model and sequence alignment
	 * renderer.
	 *
	 * Note that all sequences in the list must have the same length.
	 *
	 * @param alignedSequences a list of {@code Sequence}
	 * @param model the {@code MultipleSequenceAlignmentTracksModel}
	 * @param positionRenderer the {@code SequenceAlignmentRenderer}
	 * @param configuration the {@code MultipleSequenceAlignmentViewerConfiguration}
	 */
	public MultipleSequenceAlignmentViewerPanel(
		final List<Sequence> alignedSequences,
		final MultipleSequenceAlignmentTracksModel model,
		final SequenceAlignmentRenderer positionRenderer,
		final MultipleSequenceAlignmentViewerConfiguration configuration
	) {
		super(new BorderLayout());

		this.sequences = requireSameLengthSequences(alignedSequences);
		this.model = model;
		this.configuration = configuration;
		this.alignmentRenderer = positionRenderer;

		this.editor = new HTMLEditorKit();
		this.editor.getStyleSheet().addRule(this.configuration.getRules());
		this.document = (HTMLDocument) editor.createDefaultDocument();

		this.teDocument = new JEditorPane();
		this.teDocument.setContentType("text/html");
		this.teDocument.setEditable(false);
		this.teDocument.setEditorKit(editor);
		this.teDocument.setDocument(this.document);
		this.teDocument.setText(this.modelToHtml());
		this.teDocument.addMouseWheelListener(e -> {
			if (e.isControlDown() && mouseZoomEnabled) {
				if (e.getWheelRotation() < 0) {
					zoom(1);
				} else {
					zoom(-1);
				}
			}
		});

		this.add(new JScrollPane(teDocument), BorderLayout.CENTER);
	}

	/**
	 * Throws an {@code IllegalArgumentException} if not all sequences have
	 * the same length.
	 *
	 * @param sequences a list of sequences
	 *
	 * @return the input list of sequences
	 */
	public static final List<Sequence> requireSameLengthSequences(
		List<Sequence> sequences
	) {
		if (sequences.stream()
			.map(Sequence::getSequence).map(String::length)
			.distinct().count() > 1
		) {
			throw new IllegalArgumentException(
				"All sequences must have the same length");
		}
		return sequences;
	}

	protected String getNoModelsMessage() {
		return "No Models";
	}

	protected void printChar(StringBuilder sb,
		Optional<SequenceBaseRenderingInfo> color, char c
	) {
		if (color.isPresent()) {
			Color background = null, foreground = null;

			background = color.get().getBackground();
			foreground = color.get().getForeground();
			sb.append("<span style=\"");
			if (background != null) {
				sb
					.append("background-color: ")
					.append(toHtmlColor(background))
					.append(";");
			}
			if (foreground != null) {
				sb
				.append("color: ")
				.append(toHtmlColor(foreground))
				.append(";");
			}
			sb
				.append(color.get().isItalic() ? "font-style: italic;" : "")
				.append(color.get().isBold() ? "font-weight: bold;" : "")
				.append("\">")
				.append(c)
				.append("</span>");
		} else {
			sb.append(c);
		}
	}

	private final static String strip(String text, int length) {
		if (text.length() < length) {
			for (int i = text.length(); i < length; i++) {
				text += ' ';
			}
		} else if (text.length() > length) {
			text = text.substring(0, length);
		}

		return text;
	}

	private final static void tab(StringBuilder sb, int tabs) {
		for (int i = 0; i < tabs; i++) {
			sb.append(' ');
		}
	}

	protected synchronized void updateHtml() {
		this.teDocument.setText(this.modelToHtml());
		this.editor.getStyleSheet().addRule(this.configuration.getRules());
	}

	protected synchronized void resetToDefault() {
		this.configuration.reset();
		this.updateHtml();
	}

	protected String modelToHtml() {
		final StringBuilder sb = new StringBuilder();

		try (BufferedReader reader = new BufferedReader(
			new InputStreamReader(MultipleSequenceAlignmentViewerPanel.class
				.getResourceAsStream(ALIGNMENT_TEMPLATE)))
		) {

			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().equals("[SEQUENCES]")) {
					final int seqLength = this.sequences.get(0).getSequence().length();
					final int blockLength = this.configuration.getBlockLength();
					final int blocksPerLine = this.configuration.getBlocksPerLine();
					final int labelTab = this.configuration.getLabelTab();
					final int labelLength = this.configuration.getLabelLength();

					int offset = 0;
					while (offset < seqLength) {
						if (this.configuration.isShowIndexes() && blockLength > 5) {
							sb.append("<span class=\"indexes\">");
							sb.append(strip("Indexes", labelLength + labelTab));

							int localOffset = offset;
							int indexOffset = offset;
							for (int blockIndex = 0; blockIndex < blocksPerLine; blockIndex++) {
								final int limit = Math.min(localOffset + blockLength, seqLength);

								int written = 0;
								for (int i = indexOffset; i < limit; i++) {
									final Integer index = i;

									if ( index == null ||
										(index.intValue() != 1 &&
										index % 5 != 0)
									) {
										sb.append(' ');
										written++;
									} else {
										final String toWrite = index.toString();
										sb.append(toWrite);
										i += toWrite.length() - 1;
										written += toWrite.length();
									}
								}

								sb.append(' ');
								localOffset += blockLength;
								indexOffset += written;
							}

							sb.append("</span>\n");
						}

						if (this.configuration.isShowUpperTracks()) {
							List<Track> upperTracks = model.getUpperTracks();

							appendTracks(sb, model, seqLength, blockLength,
								blocksPerLine, labelTab, labelLength, offset,
								upperTracks);
						}

						for (Sequence sequence : sequences) {
							sb.append(strip(sequence.getHeader(), labelLength));
							tab(sb, labelTab);

							final String seqString = sequence.getSequence();

							int localOffset = offset;
							for (int blockIndex = 0; blockIndex < blocksPerLine; blockIndex++) {
								final int limit = Math.min(localOffset + blockLength, seqLength);

								for (int i = localOffset; i < limit; i++) {
									Optional<SequenceBaseRenderingInfo> positionInfo = alignmentRenderer
										.render(sequence, i);
									printChar(sb, positionInfo,
										seqString.charAt(i));
								}

								if (limit == seqLength)
									break;

								localOffset += blockLength;

								sb.append(' ');
							}

							sb.append('\n');
						}

						if (this.configuration.isShowBottomTracks()) {
							List<Track> bottomTracks = model.getBottomTracks();

							appendTracks(sb, model, seqLength, blockLength,
								blocksPerLine, labelTab, labelLength, offset,
								bottomTracks);
						}

						sb.append('\n');
						offset += blockLength * blocksPerLine;
					}
				} else if (!line.trim().equals("[HEADER]")) {
					sb.append(line).append('\n');
				} else if (line.trim().equals("[HEADER]")) {
					sb.append("<style type=\"text/css\">\n");
					sb.append(this.configuration.getRules());
					sb.append("</style>");
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return sb.toString();
	}

	private void appendTracks(final StringBuilder sb,
		final MultipleSequenceAlignmentTracksModel model, final int seqLength,
		final int blockLength, final int blocksPerLine, final int labelTab,
		final int labelLength, int offset, List<Track> bottomTracks
	) {
		for(Track track : bottomTracks) {
			sb.append("<span class=\"scores\">");
			sb.append(
				strip(track.getName(), labelLength + labelTab));

			int localOffset = offset;
			for (int blockIndex = 0; blockIndex < blocksPerLine; blockIndex++) {
				final int limit = Math.min(
					localOffset + blockLength, seqLength);

				for (int i = localOffset; i < limit; i++) {
					Optional<SequenceBaseRenderingInfo> positionInfo =
						alignmentRenderer.renderTrack(track, i);
					final char trackChar = track.getContent().charAt(i);
					printChar(sb, positionInfo, trackChar);
				}

				if (limit == seqLength)
					break;

				localOffset += blockLength;
				sb.append(' ');
			}

			sb.append('\n');
			sb.append("</span>\n");
		}
	}

	private final static String toHtmlColor(Color color) {
		return Integer.toHexString(color.getRGB()).replaceFirst("ff", "#");
	}

	/**
	 * Sets the new tracks model and alignment renderer.
	 *
	 * @param newModel the new {@code MultipleSequenceAlignmentTracksModel}
	 * @param newAlignmentRenderer the new {@code SequenceAlignmentRenderer}
	 */
	public void setModelAndRenderer(
		MultipleSequenceAlignmentTracksModel newModel,
		SequenceAlignmentRenderer newAlignmentRenderer
	) {
		this.model = newModel;
		this.alignmentRenderer = newAlignmentRenderer;
		this.updateHtml();
	}

	/**
	 * Returns the current {@code MultipleSequenceAlignmentViewerConfiguration}.
	 *
	 * @return the current {@code MultipleSequenceAlignmentViewerConfiguration}
	 */
	public MultipleSequenceAlignmentViewerConfiguration getConfiguration() {
		return this.configuration;
	}

	/**
	 * Sets the new {@code MultipleSequenceAlignmentViewerConfiguration} value.
	 *
	 * @param newConfiguration the new configuration
	 */
	public void setConfiguration(
		MultipleSequenceAlignmentViewerConfiguration newConfiguration) {
		this.configuration = newConfiguration;
		this.updateHtml();
	}

	private void zoom(int fontIncrement) {
		this.configuration
			.setFontSize(this.configuration.getFontSize() + fontIncrement);
		this.updateHtml();
	}

	/**
	 * Sets whether the scroll mouse zoom should be enabled or not.
	 *
	 * @param enabled whether the scroll mouse zoom should be enabled or not
	 */
	public void setMouseZoomEnabled(boolean enabled) {
		this.mouseZoomEnabled = enabled;
	}

	/**
	 * Exports the view as an HTML document to the specified file.
	 *
	 * @param path the file to write the view
	 * @throws IOException if an error occurs while saving the file
	 */
	public void exportToHtml(Path path) throws IOException {
		Files.write(path, this.teDocument.getText().getBytes());
	}

	/**
	 * Exports the view as a PNG image to the specified file.
	 *
	 * @param path the file to write the view
	 * @throws IOException if an error occurs while saving the image
	 */
	public void exportToPng(Path path) throws IOException {
		ImageIOUtils.toImage("png", path.toFile(), this.teDocument);
	}
}
