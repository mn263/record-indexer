package front_end.client.gui.might_not_use;

import javax.swing.*;

@SuppressWarnings({"serial", "rawtypes", "unchecked"})
public class SettingsPanel {

	private JTextField _hostTextField;
	private JTextField _portTextField;
	private JTextField _userTextField;
	private JTextField _passwordTextField;

	public SettingsPanel() {
//		super();
//
//		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
//
//		add(Box.createRigidArea(DOUBLE_HSPACE));
//		add(new JLabel("HOST:"));
//		add(Box.createRigidArea(SINGLE_HSPACE));
//
//		_hostTextField = new JTextField(7);
//		_hostTextField.setMinimumSize(_hostTextField.getPreferredSize());
//		add(_hostTextField);
//		add(Box.createRigidArea(TRIPLE_HSPACE));
//
//		add(new JLabel("PORT:"));
//		add(Box.createRigidArea(SINGLE_HSPACE));
//
//		_portTextField = new JTextField(10);
//		_portTextField.setMinimumSize(_portTextField.getPreferredSize());
//		add(_portTextField);
//		add(Box.createRigidArea(TRIPLE_HSPACE));
//
//		add(new JLabel("USER:"));
//		add(Box.createRigidArea(SINGLE_HSPACE));

		_userTextField = new JTextField(10);
		_userTextField.setMinimumSize(_userTextField.getPreferredSize());
//		add(_userTextField);
//		add(Box.createRigidArea(TRIPLE_HSPACE));
//
//		add(new JLabel("PASSWORD:"));
//		add(Box.createRigidArea(SINGLE_HSPACE));
//
//		_passwordTextField = new JTextField(10);
//		_passwordTextField.setMinimumSize(_passwordTextField.getPreferredSize());
//		add(_passwordTextField);
//		add(Box.createRigidArea(TRIPLE_HSPACE));
//
//
//		JButton _loginButton = new JButton("Login");
//		add(_loginButton);
//		add(Box.createRigidArea(DOUBLE_HSPACE));
//
//		setMaximumSize(getPreferredSize());
//

//		_loginButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
////				getController().login_view();
//			}
//		});
	}

	public void setHost(String value) {
		_hostTextField.setText(value);
	}

	public String getHost() {
		return _hostTextField.getText();
	}

	public void setPort(String value) {
		_portTextField.setText(value);
	}

	public String getPort() {
		return _portTextField.getText();
	}

	public void setUser(String value) {
		_userTextField.setText(value);
	}

	public String getUser() {
		return _userTextField.getText();
	}

	public void setPassword(String value) {
		_passwordTextField.setText(value);
	}

	public String getPassword() {
		return _passwordTextField.getText();
	}


//	public void setOperation(ServerOp value) {
//		projectsComboBox.setSelectedItem(value);
//	}

	public ServerOp validateUser() {
		return ServerOp.VALIDATE_USER;
	}

}

//		TODO: use this for the projects and fields lists (to select one)
//		projectsComboBox = new JComboBox();
//		projectsComboBox.addItem(ServerOp.VALIDATE_USER);
//		projectsComboBox.addItem(ServerOp.GET_PROJECTS);
//		projectsComboBox.addItem(ServerOp.GET_SAMPLE_IMAGE);
//		projectsComboBox.addItem(ServerOp.DOWNLOAD_BATCH);
//		projectsComboBox.addItem(ServerOp.SUBMIT_BATCH);
//		projectsComboBox.addItem(ServerOp.GET_FIELDS);
//		projectsComboBox.addItem(ServerOp.SEARCH);
//		projectsComboBox.setSelectedItem(ServerOp.VALIDATE_USER);
//		projectsComboBox.setMinimumSize(projectsComboBox.getPreferredSize());
//		add(projectsComboBox);
//		add(Box.createRigidArea(TRIPLE_HSPACE));
//
//		projectsComboBox.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				getController().operationSelected();
//			}
//		});
