package front_end.client.gui.gui_panels.indexer_view.bottom_right;

import front_end.client.gui.BasePanel;
import front_end.client.gui.controllers.ClientController;

/**
 * User: matt
 * Date: 11/19/13
 * Time: 8:37 PM
 */
public class ImageNavigation extends BasePanel {

//	The Image Navigation Tab gives the user a birds-eye-view of the batch image, and draws a gray rectangle over the
//	portion of the batch image that is currently visible in the Image Panel. This is most useful when the batch
//	image is only partially visible in the Image Panel, in which case the Image Navigation Tab gives the user a
//	better idea of what part of the batch image they are looking at in the Image Panel.
//	The user can also scroll the Image Panel by dragging the gray rectangle in the Image Navigation Tab. This
//	allows the user to precisely select which part of the batch image they are looking at in the Image Panel.
//	The Image Navigation Tab should always display the entire batch image (scaled as needed to keep it visible
//			within the space available). The aspect-ratio (i.e., width-to-height ratio) of the batch image should
//	always be preserved as the image is scaled.
//	If no batch is currently being indexed, the Image Navigation Tab should be empty.

	public ImageNavigation(ClientController clientController) {
		super(clientController);

	}
}
