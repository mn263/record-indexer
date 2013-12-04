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

//	The “Zoom In”and “Zoom Out”buttons should adjust the scale (i.e., zoom level) of the image. Zooming should be done
// 	relative to the center point of the currently visible area. That is, as zooming occurs, the point at the center of
// 	the view should remain fixed.
//	The “Invert Image”button should turn image inversion on and off.
//	The “Toggle Highlights”button should turn field highlights on and off.
//	The “Save”button should save the state of the user’s work to disk. (See the section entitled Saving Per-User State for details.)
//	The “Submit”button should submit the current batch to the Server, and put the Indexing Window in an empty state.
// 	When a batch is submitted, all record field values entered by the user should be sent to the Server.
// 	The Server should save the indexed values, and increment the number of records indexed by the user.

	public ToolBarPanel(ClientController clientController) {
		super(clientController);

		setLayout(new FlowLayout(FlowLayout.LEFT));

		//create buttons
		JButton zoomInButton = new JButton("Zoom In");
		JButton zoomOutButton = new JButton("Zoom Out");
		JButton invertButton = new JButton("Invert Image");
		JButton toggleButton = new JButton("Toggle Highlights");
		JButton saveButton = new JButton("Save");
		JButton submitButton = new JButton("Submit");

		//add them to the panel
		add(zoomInButton);
		add(zoomOutButton);
		add(invertButton);
		add(toggleButton);
		add(saveButton);
		add(submitButton);

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
				//TODO: implement invert image
			}
		});

		toggleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO: implement toggle highlights
			}
		});

		saveButton.addActionListener(saveButtonListener);

		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO: implment submit batch
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
}

