package front_end.client.gui.gui_panels.indexer_view.bottom_right;

import front_end.client.gui.ClientController;
import front_end.client.gui.base_classes.BaseController;

import javax.swing.*;

/**
 * User: matt
 * Date: 12/7/13
 * Time: 8:00 PM
 */
public class FieldHelpController extends BaseController {

	private FieldHelp fieldHelp;

	public FieldHelpController(ClientController clientController) {
		super(clientController);

		// create a JEditorPane
		fieldHelp = new FieldHelp(clientController);
		JScrollPane scrollPane = new JScrollPane(fieldHelp);
	}
}
