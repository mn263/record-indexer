package front_end.client.gui.gui_panels.indexer_view.tool_bar;

import front_end.client.gui.ClientController;
import front_end.client.gui.base_classes.BasePanel;
import front_end.client.gui.batch_state.BatchState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: matt
 * Date: 11/19/13
 * Time: 8:21 PM
 */
public class ToolBarPanel extends BasePanel {

	private JButton zoomInButton;
	private JButton zoomOutButton;
	private JButton invertButton;
	private JButton toggleButton;
	private JButton saveButton;
	private JButton submitButton;


	public ToolBarPanel(ClientController clientController) {
		super(clientController);

		setLayout(new FlowLayout(FlowLayout.LEFT));

		//create buttons
		zoomInButton = new JButton("Zoom In");
		zoomOutButton = new JButton("Zoom Out");
		invertButton = new JButton("Invert Image");
		toggleButton = new JButton("Toggle Highlights");
		saveButton = new JButton("Save");
		submitButton = new JButton("Submit");

		//add them to the panel
		add(zoomInButton);
		add(zoomOutButton);
		add(invertButton);
		add(toggleButton);
		add(saveButton);
		add(submitButton);
		if (!clientController.getBatchState().hasDownloadedBatch()) {
			zoomInButton.setEnabled(false);
			zoomOutButton.setEnabled(false);
			invertButton.setEnabled(false);
			toggleButton.setEnabled(false);
			saveButton.setEnabled(false);
			submitButton.setEnabled(false);
		}
		//create button listeners
		zoomInButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeZoomIn();
			}
		});

		zoomOutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeZoomOut();
			}
		});

		invertButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BatchState bs = getClientController().getBatchState();
				bs.setInverted(!bs.isInverted());
			}
		});

		toggleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BatchState bs = getClientController().getBatchState();
				bs.setHighlighted(!bs.isHighlighted());
			}
		});

		saveButton.addActionListener(saveButtonListener);

		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getClientController().submitBatch();
				getClientController().getBatchState().reset();
				getClientController().saveBatchState();
			}
		});
	}

	private void changeZoomIn() {
		BatchState.Zoom zoom = getClientController().getBatchState().getZoomLevel();
		switch (zoom) {
			case QUARTER:
				getClientController().getBatchState().setZoomLevel(BatchState.Zoom.HALF);
				break;
			case HALF:
				getClientController().getBatchState().setZoomLevel(BatchState.Zoom.THREE_QUARTER);
				break;
			case THREE_QUARTER:
				getClientController().getBatchState().setZoomLevel(BatchState.Zoom.ONE);
				break;
			case ONE:
				getClientController().getBatchState().setZoomLevel(BatchState.Zoom.ONE_AND_QUARTER);
				break;
			case ONE_AND_QUARTER:
				getClientController().getBatchState().setZoomLevel(BatchState.Zoom.ONE_AND_HALF);
				break;
			case ONE_AND_HALF:
				getClientController().getBatchState().setZoomLevel(BatchState.Zoom.ONE_AND_THREE_QUARTER);
				break;
			case ONE_AND_THREE_QUARTER:
				getClientController().getBatchState().setZoomLevel(BatchState.Zoom.TWO);
				break;
			case TWO:
				getClientController().getBatchState().setZoomLevel(BatchState.Zoom.TWO);
				break;
			default:
				System.out.println("The zoom level was an invalid one");
				break;
		}
	}

	private void changeZoomOut() {
		BatchState.Zoom zoom = getClientController().getBatchState().getZoomLevel();
		switch (zoom) {
			case QUARTER:
				getClientController().getBatchState().setZoomLevel(BatchState.Zoom.QUARTER);
				break;
			case HALF:
				getClientController().getBatchState().setZoomLevel(BatchState.Zoom.QUARTER);
				break;
			case THREE_QUARTER:
				getClientController().getBatchState().setZoomLevel(BatchState.Zoom.HALF);
				break;
			case ONE:
				getClientController().getBatchState().setZoomLevel(BatchState.Zoom.THREE_QUARTER);
				break;
			case ONE_AND_QUARTER:
				getClientController().getBatchState().setZoomLevel(BatchState.Zoom.ONE);
				break;
			case ONE_AND_HALF:
				getClientController().getBatchState().setZoomLevel(BatchState.Zoom.ONE_AND_QUARTER);
				break;
			case ONE_AND_THREE_QUARTER:
				getClientController().getBatchState().setZoomLevel(BatchState.Zoom.ONE_AND_HALF);
				break;
			case TWO:
				getClientController().getBatchState().setZoomLevel(BatchState.Zoom.ONE_AND_THREE_QUARTER);
				break;
			default:
				System.out.println("The zoom level was an invalid one");
				break;
		}
	}

	ActionListener saveButtonListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			getClientController().saveBatchState();
		}
	};

	public void updateToolBar() {
		if (getClientController().getBatchState().hasDownloadedBatch()) {
			zoomInButton.setEnabled(true);
			zoomOutButton.setEnabled(true);
			invertButton.setEnabled(true);
			toggleButton.setEnabled(true);
			saveButton.setEnabled(true);
			submitButton.setEnabled(true);
		} else {
			zoomInButton.setEnabled(false);
			zoomOutButton.setEnabled(false);
			invertButton.setEnabled(false);
			toggleButton.setEnabled(false);
			saveButton.setEnabled(false);
			submitButton.setEnabled(false);
		}
	}
}

