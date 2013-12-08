package front_end.client.gui.gui_panels.indexer_view.menu_bar;

import front_end.client.gui.ClientController;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * User: matt
 * Date: 11/19/13
 * Time: 8:29 PM
 */
public class FileMenu extends JMenu {

	private JMenuItem dnldBatchMenu;
	private JMenuItem logoutMenu;
	private JMenuItem exitMenu;

	public FileMenu(ClientController clientController) {
		super("File");
		setMnemonic('f');

		dnldBatchMenu = new JMenuItem("Download Batch", KeyEvent.VK_D);
		if (clientController.getBatchState().hasDownloadedBatch()) {
			dnldBatchMenu.setEnabled(false);
		}
		add(dnldBatchMenu);

		logoutMenu = new JMenuItem("Logout", KeyEvent.VK_L);
		add(logoutMenu);

		exitMenu = new JMenuItem("Exit", KeyEvent.VK_X);
		add(exitMenu);
	}

	public JMenuItem getDnldBatchMenu() {
		return dnldBatchMenu;
	}

	public JMenuItem getLogoutMenu() {
		return logoutMenu;
	}

	public JMenuItem getExitMenu() {
		return exitMenu;
	}
}
