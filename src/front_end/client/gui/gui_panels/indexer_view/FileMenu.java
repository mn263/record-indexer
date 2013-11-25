package front_end.client.gui.gui_panels.indexer_view;

import javax.swing.*;
import java.awt.event.ActionListener;
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

	public FileMenu(ActionListener menuListener) {
		super("File");
		setMnemonic('f');

//		TODO: only allow "Download Batch" if they aren't currently working on a batch
		dnldBatchMenu = new JMenuItem("Download Batch", KeyEvent.VK_D);
		dnldBatchMenu.addActionListener(menuListener);
		add(dnldBatchMenu);

		logoutMenu = new JMenuItem("Logout", KeyEvent.VK_L);
		logoutMenu.addActionListener(menuListener);
		add(logoutMenu);

		exitMenu = new JMenuItem("Exit", KeyEvent.VK_X);
		exitMenu.addActionListener(menuListener);
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
