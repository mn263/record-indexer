package front_end.client.gui_searcher.controllers;

import front_end.client.gui_searcher.views.gui_panels.login.LoginFrame;
import shared.communication.params.ValidateUser_Params;
import shared.communication.results.ValidateUser_Result;

import java.util.List;

/**
 * User: matt
 * Date: 11/19/13
 * Time: 11:52 PM
 */
public class LoginController extends BaseController {

	public LoginController(LoginFrame frame) {
		super(frame);
	}

	public void setLoginFrame(LoginFrame frame) {
		super.setFrame(frame);
	}


	public void login() {
		LoginFrame frame = (LoginFrame) getFrame();
		List<String> paramValues = frame.getLoginValues();

		validateUser(paramValues.get(0), paramValues.get(1));
//		getProjects(paramValues[0], paramValues[1]);

//		String projects = getFrame().getProjectsPanel();
//		String[] projectList = projects.split("\n");
//		for (String project : projectList) {
//			if (project.matches(isNumberRegex) && !project.isEmpty()) {
//		getFields(paramValues[0], paramValues[1], "");
//			}
//		}
	}

	private void validateUser(String userName, String password) {
		ValidateUser_Result result = getClientClass().validateUser(new ValidateUser_Params(userName, password));
//        if (result == null) {
//        	_frame.setResponse("FAILED\n");
//        }else if (result.isUser()) {
//            _frame.setResponse("TRUE\n" + result.getUserNameFirst() + "\n" + result.getUserNameLast() + "\n" + result.getRecordCount() + "\n");
//        } else {
//            _frame.setResponse("FALSE\n");
//        }
	}
}
