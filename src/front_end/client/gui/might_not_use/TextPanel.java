package front_end.client.gui.might_not_use;

import javax.swing.*;

@SuppressWarnings("serial")
public class TextPanel {

	private JTextArea _textArea;

	public TextPanel(String label) {
//		super();

//		setBorder(BorderFactory.createTitledBorder(label));
//
//		setLayout(new BorderLayout());
//
//		_textArea = new JTextArea(10, 60);
//		add(new JScrollPane(_textArea), BorderLayout.CENTER);
	}

	public void setText(String value) {
		_textArea.setText(value);
	}

	public String getText() {
		return _textArea.getText();
	}
}

