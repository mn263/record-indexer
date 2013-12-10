package front_end.client.gui.gui_panels.login_view;

import javax.swing.*;
import java.awt.*;

/**
 * User: matt
 * Date: 11/19/13
 * Time: 10:50 PM
 */
public class LoginPanel extends JPanel {

//	When no one is logged in, your program should display the Login Dialog. This includes both when the program
// 	initially starts, and when the user logs out (but doesn’t exit). The Indexing Window should not be visible when no one is logged in.
//	If the user clicks the “Exit”button in the Login Dialog, the program should terminate.
//	If the user clicks the “Login”button, the program should pass the specified username and password to the Server
// 	for validation. If login_view is successful,
//	Display a Welcome Dialog that welcomes the user by name, and displays the number of records previously indexed
// 	(i.e., submitted) by the user.
//	When the user closes the Welcome Dialog, close the Login Dialog, and display the main Indexing Window. The state
// 	of the Indexing Window should be restored to the same state it was in the last time the user logged out or exited.
// 	(See the next section for details.)
//	If login_view fails,
//	Display an error message dialog indicating the problem.
//	When the user closes the error message dialog, the Login Dialog should remain visible so the user can try again.

	private JTextField userNameTextField;
	private JPasswordField passwordTextField;
	private JButton loginButton;
	private JButton exitButton;

	public LoginPanel() {
		super();

		// create buttons
		JLabel userNameLabel = new JLabel("Username:");
//		TODO: take out the 'test2' from being preloaded
		userNameTextField = new JTextField("test1");
//		userNameTextField = new JTextField(35);
		JLabel passwordLabel = new JLabel("Password:");
		passwordTextField = new JPasswordField("test1");
//		passwordTextField = new JPasswordField(35);
		loginButton = new JButton("Login");
		exitButton = new JButton("Exit");

		setLayout(new GridLayout(3, 1));

		// rows to  LoginPanel
		JPanel rowOnePanel = new JPanel();
		rowOnePanel.add(userNameLabel);
		rowOnePanel.add(userNameTextField);
		add(rowOnePanel);

		JPanel rowTwoPanel = new JPanel();
		rowTwoPanel.add(passwordLabel);
		rowTwoPanel.add(passwordTextField);
		add(rowTwoPanel);

		JPanel rowThreePanel = new JPanel();
		rowThreePanel.add(loginButton);
		rowThreePanel.add(exitButton);
		add(rowThreePanel);
	}

	public void setUser(String value) {
		userNameTextField.setText(value);
	}

	public String getUserName() {
		return userNameTextField.getText();
	}

	public void setPassword(String value) {
		passwordTextField.setText(value);
	}

	public String getPassword() {
		return String.valueOf(passwordTextField.getPassword());
	}

	public JButton getLoginButton() {
		return loginButton;
	}

	public JButton getExitButton() {
		return exitButton;
	}
}
