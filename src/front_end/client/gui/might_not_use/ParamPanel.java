package front_end.client.gui.might_not_use;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ParamPanel {

	private String[] _paramNames;
	private ArrayList<JTextField> _textFields;

	public ParamPanel() {
//		super();

//		setBorder(BorderFactory.createTitledBorder("Parameters"));

//		setLayout(new GridBagLayout());
	}

	public void setParameterNames(String[] value) {
		_paramNames = value;
		_textFields = new ArrayList<>();

//		this.removeAll();

		GridBagConstraints gbc = new GridBagConstraints();

		int row = 0;
		for (String paramName : _paramNames) {
			boolean isLast = (paramName.equals(_paramNames[_paramNames.length - 1]));

			JLabel label = new JLabel(paramName + ":");

			gbc.gridx = 0;
			gbc.gridy = row;
			gbc.weightx = 0;
			gbc.weighty = 0;
			gbc.fill = GridBagConstraints.NONE;
			gbc.insets = new Insets(5, 5, (isLast ? 5 : 0), 0);
//			add(label, gbc);

			JTextField textField = new JTextField(40);
			textField.setMinimumSize(textField.getPreferredSize());
			_textFields.add(textField);

			gbc.gridx = 1;
			gbc.gridy = row;
			gbc.weightx = 0;
			gbc.weighty = 0;
			gbc.fill = GridBagConstraints.NONE;
			gbc.insets = new Insets(5, 5, (isLast ? 5 : 0), 0);
//			add(textField, gbc);

			++row;
		}

//		add(Box.createRigidArea(TRIPLE_HSPACE));
//		JButton _submitButton = new JButton("Submit");
//		add(_submitButton);
//		add(Box.createRigidArea(DOUBLE_HSPACE));
//		setMaximumSize(getPreferredSize());
//		_submitButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
////				getController().search();
//			}
//		});
//
//		Dimension prefSize = getPreferredSize();
//		Dimension maxSize = getMaximumSize();
//		setMaximumSize(new Dimension((int) maxSize.getWidth(), (int) prefSize.getHeight()));
//
//		revalidate();
	}

	public String[] getParameterNames() {
		return _paramNames;
	}

	public void setParameterValues(String[] value) {
		for (int i = 0; i < value.length; ++i) {
			_textFields.get(i).setText(value[i]);
		}
	}

	public String[] getParameterValues() {
		List<String> result = new ArrayList<>();
		for (JTextField _textField : _textFields) {
			result.add(_textField.getText());
		}
		return result.toArray(new String[result.size()]);
	}
}

