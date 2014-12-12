package es.uvigo.ei.sing.hlfernandez.text;


import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 * A {@link JLimitedTextField} is an extension of {@link JTextField} that allows
 * specifying the maximum extension of the document ({@code Integer.MAX_VALUE}
 * by default).
 * 
 * @author hlfernandez
 * @see JTextField
 *
 */
public class JLimitedTextField extends JTextField {
	private static final long serialVersionUID = 1L;
	private int limit = Integer.MAX_VALUE;

	/**
	 * Constructs a new instance of {@code JLimitedTextField}.
	 * 
	 * @param limit
	 *            the document limit.
	 */
	public JLimitedTextField(int limit) {
		super();
		this.limit = limit;
	}

	/**
	 * Constructs a new instance of {@code JLimitedTextField}.
	 * 
	 * @param string
	 *            the initial text.
	 */
	public JLimitedTextField(String string) {
		super();
		insertString(0, string);
	}

	/**
	 * Constructs a new instance of {@code JLimitedTextField}.
	 * 
	 * @param string
	 *            the initial text.
	 * @param limit
	 *            the document limit.
	 */
	public JLimitedTextField(String string, int limit) {
		super();
		this.limit = limit;
		insertString(0, string);
	}
	
	/**
	 * Constructs a new instance of {@code JLimitedTextField}.
	 * 
	 * @param string
	 *            the initial text.
	 * @param limit
	 *            the document limit.
	 * @param cols
	 *            the number of columns.
	 */
	public JLimitedTextField(String string, int limit, int cols) {
		super(cols);
		this.limit = limit;
		insertString(0, string);
	}

	private void insertString(int offset, String s) {
		try {
			getDocument().insertString(offset, s, null);
		} catch (BadLocationException e) {
		}
	}

	@Override
	protected Document createDefaultModel() {
		return new LimitDocument();
	}

	private class LimitDocument extends PlainDocument {
		private static final long serialVersionUID = 1L;

		@Override
		public void insertString(int offset, String str, AttributeSet attr)
				throws BadLocationException {
			if (str == null)
				return;

			if ((getLength() + str.length()) <= limit) {
				super.insertString(offset, str, attr);
			}
		}

	}
}