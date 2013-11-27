package front_end.client.gui.batch_state;

import java.awt.*;

/**
 * User: matt
 * Date: 11/20/13
 * Time: 10:59 AM
 */
public interface BatchStateListener {

//	IMPLEMENTATION EXAMPLE
// 	ButtonBar(BatchState) {
//		BS.addListener(this)
//	}

	void ImageURLChanged(String newURL);

	void RecordValueChanged(String recordValue);

	void WindowPositionChanged(Point newPosition);

	void WindowDimensionsChanged(Point newDimensions);

	void DividerPositionsChanged(Point newPositions);

	void ZoomedIn();

	void ZoomedOut();

	void ScrollPositionChanged(double newPosition);

	void HighlightIsToggled();

	void InvertIsToggled();
}
