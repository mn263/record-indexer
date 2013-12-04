package front_end.client.gui.gui_panels.login_view;

import front_end.client.gui.ClientController;
import front_end.client.gui.base_classes.BaseController;
import shared.communication.results.ValidateUser_Result;

import javax.swing.*;

/**
 * User: matt
 * Date: 11/22/13
 * Time: 7:06 PM
 */
public class LoginController extends BaseController {

	private LoginFrame loginFrame;

	public LoginController(ClientController clientController, LoginListener loginListener) {
		super(clientController);
		openLoginFrame(loginListener);
	}

	private void openLoginFrame(LoginListener loginListener) {
		loginFrame = new LoginFrame(clientController);
		loginFrame.addLoginListener(loginListener);
		loginFrame.setVisible(true);
	}

	public boolean isLoggedInHandler(ValidateUser_Result result) {
		if (!result.isUser()) {
			JOptionPane.showMessageDialog(loginFrame.getContentPane(), "Invalid Credentials", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} else {
			String title = "Welcome to Indexer";
			String loginMessage = getLoginMessage(result);
			JOptionPane.showMessageDialog(loginFrame.getContentPane(), loginMessage, title, JOptionPane.INFORMATION_MESSAGE);
			loginFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			loginFrame.dispose();
			return true;
		}
	}

	private String getLoginMessage(ValidateUser_Result loginResult) {
		String userName = loginResult.getUserNameFirst() + " " + loginResult.getUserNameLast() + ".\n";
		return "Welcome " + userName + "You have indexed " + loginResult.getRecordCount() + " records.";
	}
}
