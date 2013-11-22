package front_end.client.gui_searcher.views.gui_panels.login;

import front_end.client.gui_searcher.controllers.BaseController;
import front_end.client.gui_searcher.controllers.LoginController;
import front_end.client.gui_searcher.views.might_not_use.BaseFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * User: matt
 * Date: 11/19/13
 * Time: 8:19 PM
 */
public class LoginFrame extends BaseFrame {

	private LoginController _controller;
	private LoginPanel _loginPanel;

	public LoginFrame(String host, String port) {
		super(host, port);

		getContentPane().setSize(800, 800);
		setPreferredSize(new Dimension(800, 800));

		setTitle("Login to Indexer");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_loginPanel = new LoginPanel(this.getContentPane());
		add(_loginPanel);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
		pack();
		setMinimumSize(getPreferredSize());
	}

	public BaseController getController() {
		return _controller;
	}

	public void setController(LoginController loginController) {
		_controller = loginController;
		_loginPanel.setController(loginController);
	}

	public List<String> getLoginValues() {
		ArrayList<String> values = new ArrayList<>();
		values.add(_loginPanel.getUser());
		values.add(_loginPanel.getPassword());
		return values;
	}
}
