package front_end.client.gui.batch_state;

import shared.communication.results.DownloadBatch_Result;

import java.awt.*;

/**
 * User: matt
 * Date: 11/20/13
 * Time: 10:15 AM
 */
public class BatchState {

	public void reset() {
		this.hasDownloadedBatch = false;
		this.result = null;
		this.selectedColumn = 0;
		this.selectedRow = 0;
		this.recordValues = null;
		batchStateListener.BatchDownloaded();

	}

	public enum Zoom {
		QUARTER, HALF, THREE_QUARTER, ONE, ONE_AND_QUARTER, ONE_AND_HALF, ONE_AND_THREE_QUARTER, TWO
	}

	private String userName = "";
	private String password = "";
	private boolean hasDownloadedBatch = false;
	private Point windowPosition = new Point(100, 100);
	private Point windowDimensions = new Point(1000, 650);
	private int horizDivPosit = 442;
	private int vertDivPosit = 328;
	private Zoom zoomLevel = Zoom.ONE;
	private boolean isHighlighted = true;
	private boolean isInverted = false;
	private boolean isTableEntryTab = true;
	private boolean isFileHelpTab = true;
	private DownloadBatch_Result result;
	private int selectedColumn = 0;
	private int selectedRow = 0;
	transient private BatchStateListener batchStateListener;
	private String[][] recordValues;

	public void addBSListener(BatchStateListener bsListener) {
		this.batchStateListener = bsListener;
	}

	public void changeSelectedCell(int row, int column) {
		this.selectedRow = row;
		this.selectedColumn = column;
		batchStateListener.RecordSelectionChanged(row, column);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean hasDownloadedBatch() {
		return hasDownloadedBatch;
	}

	public String[][] getRecordValues() {
		return recordValues;
	}

	public String getRecordValue(int column, int row) {
		return recordValues[column][row];
	}


	public void setRecordValues(String[][] recordValues) {
		this.recordValues = recordValues;
	}

	public void setRecordValueFromTableEntryTable(String value, int column, int row) {
		if (column >= 0) {
			this.recordValues[column][row] = value;
		}
	}

	public void setRecordValue(String value, int column, int row) {
		if (column >= 0) {
			this.recordValues[column][row] = value;
			batchStateListener.RecordValuesChanged();
		}
	}

	public Point getWindowPosition() {
		return windowPosition;
	}

	public void setWindowPosition(Point windowPosition) {
		this.windowPosition = windowPosition;
	}

	public Point getWindowDimensions() {
		return windowDimensions;
	}

	public void setWindowDimensions(Point windowDimensions) {
		this.windowDimensions = windowDimensions;
	}

	public int getHorizDivPosit() {
		return horizDivPosit;
	}

	public void setHorizDivPosit(int horizDivPosit) {
		this.horizDivPosit = horizDivPosit;
	}

	public int getVertDivPosit() {
		return vertDivPosit;
	}

	public void setVertDivPosit(int vertDivPosit) {
		this.vertDivPosit = vertDivPosit;
	}

	public Zoom getZoomLevel() {
		return zoomLevel;
	}

	public void setZoomLevel(Zoom zoomLevel) {
		this.zoomLevel = zoomLevel;
		batchStateListener.ZoomedChanged();
	}

	public boolean isHighlighted() {
		return isHighlighted;
	}

	public void setHighlighted(boolean highlighted) {
		isHighlighted = highlighted;
		batchStateListener.highlightToggled();
	}

	public boolean isInverted() {
		return isInverted;
	}

	public void setInverted(boolean inverted) {
		isInverted = inverted;
		batchStateListener.invertImageToggled();
	}

	public boolean isTableEntryTab() {
		return isTableEntryTab;
	}

	public void setTableEntryTab(boolean tableEntryTab) {
		isTableEntryTab = tableEntryTab;
	}

	public boolean isFormEntryTab() {
		return !isTableEntryTab;
	}

	public void setFormEntryTab(boolean formEntryTab) {
		isTableEntryTab = !formEntryTab;
	}

	public boolean isImageNavigationTab() {
		return !isFileHelpTab;
	}

	public void setImageNavigationTab(boolean imageNavigationTab) {
		isFileHelpTab = !imageNavigationTab;
	}

	public boolean isFileHelpTab() {
		return isFileHelpTab;
	}

	public void setFileHelpTab(boolean fileHelpTab) {
		isFileHelpTab = fileHelpTab;
	}

	public DownloadBatch_Result getDownloadBatchResult() {
		return result;
	}

	public void setDownloadBatchResult(DownloadBatch_Result result) {
		this.result = result;
		this.recordValues = new String[result.getFieldCount()][result.getRecordCount()];

		this.hasDownloadedBatch = true;
		batchStateListener.BatchDownloaded();
	}

	public int getSelectedColumn() {
		return selectedColumn;
	}

	public int getSelectedRow() {
		return selectedRow;
	}
}