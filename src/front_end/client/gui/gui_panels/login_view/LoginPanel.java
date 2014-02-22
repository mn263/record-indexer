package front_end.client.gui.gui_panels.login_view;

import javax.swing.*;
import java.awt.*;

/**
 * User: matt
 * Date: 11/19/13
 * Time: 10:50 PM
 */
public class LoginPanel extends JPanel {

	private JTextField userNameTextField;
	private JPasswordField passwordTextField;
	private JButton loginButton;
	private JButton exitButton;

	public LoginPanel() {
		super();

		// create buttons
		JLabel userNameLabel = new JLabel("Username:");
		userNameTextField = new JTextField(35);
		JLabel passwordLabel = new JLabel("Password:");
		passwordTextField = new JPasswordField(35);
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
