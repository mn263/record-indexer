package front_end.client.gui.batch_state;

/**
 * User: matt
 * Date: 11/20/13
 * Time: 10:59 AM
 */
public interface BatchStateListener {

	void BatchDownloaded();

	void RecordSelectionChanged(int row, int column);

	void ZoomedChanged();

	void highlightToggled();

	void invertImageToggled();

	void RecordValuesChanged();
}
