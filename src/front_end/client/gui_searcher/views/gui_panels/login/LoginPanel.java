package front_end.client.gui_searcher.views.gui_panels.login;

import front_end.client.gui_searcher.controllers.LoginController;
import front_end.client.gui_searcher.views.gui_panels.main_panel.BasePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: matt
 * Date: 11/19/13
 * Time: 10:50 PM
 */
public class LoginPanel extends BasePanel {

//	When no one is logged in, your program should display the Login Dialog. This includes both when the program
// 	initially starts, and when the user logs out (but doesn’t exit). The Indexing Window should not be visible when no one is logged in.
//	If the user clicks the “Exit”button in the Login Dialog, the program should terminate.
//	If the user clicks the “Login”button, the program should pass the specified username and password to the Server
// 	for validation. If login is successful,
//	Display a Welcome Dialog that welcomes the user by name, and displays the number of records previously indexed
// 	(i.e., submitted) by the user.
//	When the user closes the Welcome Dialog, close the Login Dialog, and display the main Indexing Window. The state
// 	of the Indexing Window should be restored to the same state it was in the last time the user logged out or exited.
// 	(See the next section for details.)
//	If login fails,
//	Display an error message dialog indicating the problem.
//	When the user closes the error message dialog, the Login Dialog should remain visible so the user can try again.

	private JTextField _userNameTextField;
	private JTextField _passwordTextField;
	private JTextField urlField;

	public LoginPanel(Container container) {
		super();

//		add(new JLabel("Username:"));

		_userNameTextField = new JTextField(12);
//		add(_userNameTextField);
//		_userNameTextField.setAlignmentX(LEFT_ALIGNMENT);


//		add(new JLabel("Password:"));
		_passwordTextField = new JTextField(12);
		_passwordTextField.setAlignmentX(LEFT_ALIGNMENT);
//		add(_passwordTextField);


		JButton _loginButton = new JButton("Login");
		_loginButton.setAlignmentX(LEFT_ALIGNMENT);
//		add(_loginButton);

		JButton _exitButton = new JButton("Exit");
//		add(_exitButton);

		setMaximumSize(getPreferredSize());


		container.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		setGbc(gbc, 0, 0, 1, 1);
		add(new JLabel("Username:"), gbc);

		setGbc(gbc, 1, 0, 11, 1);
		setGbcWeight(gbc, 100, 0);
		setGbcFillAnchor(gbc, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);
		add(_userNameTextField, gbc);

		setGbc(gbc, 0, 1, 2, 1);
		add(new JLabel("Password:"), gbc);

		setGbc(gbc, 4, 1, 2, 1);
		add(_passwordTextField, gbc);

		_loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				LoginController loginController = (LoginController) getController();
				loginController.login();
			}
		});

		_exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				TODO: make the exit funciton close the program
//				getController().exit();
			}
		});
	}

	public void setUser(String value) {
		_userNameTextField.setText(value);
	}

	public String getUser() {
		return _userNameTextField.getText();
	}

	public void setPassword(String value) {
		_passwordTextField.setText(value);
	}

	public String getPassword() {
		return _passwordTextField.getText();
	}

}
