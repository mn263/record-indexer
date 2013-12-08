package front_end.client.gui.gui_panels.indexer_view;

import front_end.client.gui.ClientController;
import front_end.client.gui.base_classes.BaseFrame;
import front_end.client.gui.batch_state.BatchState;
import front_end.client.gui.gui_panels.indexer_view.bottom_left.BottomLeftPanel;
import front_end.client.gui.gui_panels.indexer_view.bottom_right.BottomRightPanel;
import front_end.client.gui.gui_panels.indexer_view.image_section.ImagePanel;
import front_end.client.gui.gui_panels.indexer_view.menu_bar.FileMenu;
import front_end.client.gui.gui_panels.indexer_view.tool_bar.ToolBarPanel;
import front_end.client.gui.gui_panels.menu.download_batch.DownloadBatchController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * User: matt
 * Date: 11/19/13
 * Time: 8:33 PM
 */
public class IndexerFrame extends BaseFrame {

	private ToolBarPanel toolBarPanel;
	private JMenuItem dnldBatchMenu;
	private JMenuItem exitMenu;
	private JSplitPane splitHorizontalPane;
	private JSplitPane splitVerticalPane;
	private ImagePanel imagePanel;
	private BottomLeftPanel bottomLeftPanel;
	private BottomRightPanel bottomRightPanel;

	public IndexerFrame(ClientController clientController, ActionListener restartListener) {
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

		//add drop-down menu
		FileMenu fileMenu = addDropDownMenu(restartListener);
		menuBar.add(fileMenu);

		//add menu(tool)-bar
		toolBarPanel = new ToolBarPanel(clientController);
		add(toolBarPanel, BorderLayout.NORTH);

		//add imagePanel
		imagePanel = new ImagePanel(clientController);

		//add bottom left and bottom right frames
		addHorizontalDivider();
		addVerticalDivider(imagePanel);

		pack();
		setMinimumSize(new Dimension(200, 100));
		addComponentListener(windowLocationListener);
	}

	private void addHorizontalDivider() {
		bottomLeftPanel = new BottomLeftPanel(getClientController());
		bottomRightPanel = new BottomRightPanel(getClientController());
		splitHorizontalPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, bottomLeftPanel, bottomRightPanel);
		splitHorizontalPane.setDividerLocation(getClientController().getBatchState().getHorizDivPosit());
		splitHorizontalPane.addPropertyChangeListener(horizontalSplitListener);
	}

	private void addVerticalDivider(ImagePanel imagePanel) {
		splitVerticalPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, imagePanel, splitHorizontalPane);
		splitVerticalPane.setDividerLocation(getClientController().getBatchState().getVertDivPosit());
		add(splitVerticalPane, BorderLayout.CENTER);
		splitVerticalPane.addPropertyChangeListener(verticalSplitListener);
	}

	private FileMenu addDropDownMenu(ActionListener restartListener) {
		FileMenu fileMenu = new FileMenu(getClientController());
		dnldBatchMenu = fileMenu.getDnldBatchMenu();
		JMenuItem logoutMenu = fileMenu.getLogoutMenu();
		logoutMenu.addActionListener(restartListener);
		exitMenu = fileMenu.getExitMenu();
		ActionListener menuListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == dnldBatchMenu) {
					new DownloadBatchController(getClientController());
				} else if (e.getSource() == exitMenu) {
					getClientController().saveBatchState();
					System.exit(0);
				}
			}
		};
		dnldBatchMenu.addActionListener(menuListener);
		exitMenu.addActionListener(menuListener);
		return fileMenu;
	}

	PropertyChangeListener horizontalSplitListener = new PropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			getClientController().getBatchState().setHorizDivPosit(splitHorizontalPane.getDividerLocation());
		}
	};

	PropertyChangeListener verticalSplitListener = new PropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			getClientController().getBatchState().setVertDivPosit(splitVerticalPane.getDividerLocation());
		}
	};

	ComponentListener windowLocationListener = new ComponentListener() {
		@Override
		public void componentResized(ComponentEvent e) {
			getClientController().getBatchState().setWindowDimensions(new Point(getWidth(), getHeight()));
		}

		@Override
		public void componentMoved(ComponentEvent e) {
			getClientController().getBatchState().setWindowPosition(getLocation());
		}

		public void componentShown(ComponentEvent e) {
		}

		public void componentHidden(ComponentEvent e) {
		}
	};

	public void updateForNewBatch() {
		imagePanel.reloadImage();
		bottomLeftPanel.reloadTabs();
		//TODO: update the image in the bottom-right panel
	}

	public void updateForRecordSelectionChange(int row, int column) {
		imagePanel.updateRecordSelected();
		bottomLeftPanel.updateTabs(row, column);
	}

	public void updateZoom() {
		imagePanel.updateZoom();
		//TODO: update the image in the bottom-right panel
	}

	public ToolBarPanel getToolBarPanel() {
		return toolBarPanel;
	}


	public void highlightToggled() {
		imagePanel.toggleHighlight();

	}

	public void invertImageToggled() {
		imagePanel.invertImage();
	}
}
