package front_end.client.gui.gui_panels.login_view;

import shared.communication.results.ValidateUser_Result;

/**
 * User: matt
 * Date: 11/22/13
 * Time: 6:17 PM
 */
public interface LoginListener {

	void loggedIn(ValidateUser_Result loginResult);
}
