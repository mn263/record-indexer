package front_end.client.gui.gui_panels.indexer_view;

import front_end.client.gui.BatchState;
import front_end.client.gui.controllers.ClientController;
import front_end.client.gui.gui_panels.download_batch.DownloadBatchController;
import front_end.client.gui.might_not_use.BaseFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: matt
 * Date: 11/19/13
 * Time: 8:33 PM
 */
public class IndexerFrame extends BaseFrame {
//	The main Indexing Window should be resizable, but the dialog windows should not be.
//	TODO: make sure that all dialog windows are not resizable
//	All dialog windows should be modal (i.e., the user must close the dialog window before being allowed to interact
//	with other parts of the GUI).

	private JMenuItem dnldBatchMenu;
	private JMenuItem logoutMenu;
	private JMenuItem exitMenu;

	public IndexerFrame(ClientController clientController) {
		super(clientController);

		BatchState batchState = clientController.getBatchState();

		Point frameSize = batchState.getWindowDimensions();
		getContentPane().setSize(frameSize.x, frameSize.y);
		setPreferredSize(new Dimension(frameSize.x, frameSize.y));
		setLocation(batchState.getWindowPosition());

		setTitle("Indexer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		FileMenu fileMenu = new FileMenu(menuListener);
		dnldBatchMenu = fileMenu.getDnldBatchMenu();
		logoutMenu = fileMenu.getLogoutMenu();
		exitMenu = fileMenu.getExitMenu();
		menuBar.add(fileMenu);


		pack();
		setMinimumSize(getPreferredSize());
	}

	private ActionListener menuListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == dnldBatchMenu) {
				System.out.println("download batch selected");
//				TODO: open download batch frame (similar to the login frame)
				new DownloadBatchController(getClientController());

			} else if (e.getSource() == logoutMenu) {
				System.out.println("logout selected");
//				TODO: on logout do the following
				//	1. Save the state of the user’s work on disk
				//	2. Close the Indexing Window
				//	3. Display the Login Dialog
			} else if (e.getSource() == exitMenu) {
				System.out.println("exit selected");
//				TODO: do the following two things
				//	1. Save the state of the user’s work on disk
				//	2. Terminate the program
				System.exit(0);
			}
		}
	};
}
