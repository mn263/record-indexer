package front_end.client.gui.gui_panels.login_view;

import front_end.client.gui.ClientController;
import front_end.client.gui.base_classes.BaseFrame;
import shared.communication.results.ValidateUser_Result;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: matt
 * Date: 11/19/13
 * Time: 8:19 PM
 */
public class LoginFrame extends BaseFrame {

	private LoginPanel loginPanel;
	private LoginListener loginListener = null;
	private ValidateUser_Result result;

	public LoginFrame(ClientController clientController) {
		super(clientController);

		setLocation(clientController.getBatchState().getWindowPosition());
		setResizable(false);

		getContentPane().setSize(LOGIN_WIDTH, LOGIN_HEIGHT);
		setPreferredSize(new Dimension(LOGIN_WIDTH, LOGIN_HEIGHT));

		setTitle("Login to Indexer");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginPanel = new LoginPanel();
		add(loginPanel);

		JButton loginButton = loginPanel.getLoginButton();
		JButton exitButton = loginPanel.getExitButton();

		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				result = getClientController().validateUser(loginPanel.getUserName(), loginPanel.getPassword());
				loginListener.loggedIn(result);
			}
		});

		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		pack();
	}

	public void addLoginListener(LoginListener loginListener) {
		this.loginListener = loginListener;
	}

	public String getUserName() {
		return loginPanel.getUserName();
	}
}
