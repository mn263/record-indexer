package front_end.client.gui.batch_state;

import org.apache.commons.io.IOUtils;
import shared.communication.results.DownloadBatch_Result;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;

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
		this.knownDataSetsList.clear();
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
	public Point imageTopLeftCorner = new Point(-300, -300);
	private int horizDivPosit = 442;
	private int vertDivPosit = 328;
	private Zoom zoomLevel = Zoom.ONE;
	private boolean isHighlighted = true;
	private boolean isInverted = false;
	private DownloadBatch_Result result;
	private int selectedColumn = 0;
	private int selectedRow = 0;
	transient private BatchStateListener batchStateListener;
	private String[][] recordValues;
	private ArrayList<HashSet<String>> knownDataSetsList = new ArrayList<>();




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

	public DownloadBatch_Result getDownloadBatchResult() {
		return result;
	}

	public void setDownloadBatchResult(DownloadBatch_Result result, String hostAndPort) {
		this.result = result;
		this.recordValues = new String[result.getFieldCount()][result.getRecordCount()];
		knownDataSetsList.clear();

		for (DownloadBatch_Result.FieldInformation fieldInfo : result.getFieldInformationList()) {
			String knownDataPath = fieldInfo.getKnownDataPath();
			try {
				if (knownDataPath == null) {
					knownDataSetsList.add(new HashSet<String>());
				} else {
					getKnownData(knownDataPath, hostAndPort);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		this.hasDownloadedBatch = true;
		batchStateListener.BatchDownloaded();
	}

	private void getKnownData(String knownDataURL, String hostAndPort) throws IOException {
		knownDataURL = hostAndPort + knownDataURL;
		URL url = new URL(knownDataURL);
		String knownData = IOUtils.toString(url);
		String[] dataArray = knownData.split(",");
		HashSet<String> knownDataSet = new HashSet<>();
		for (String value : dataArray) {
			value = value.toUpperCase();
			knownDataSet.add(value);
		}
		knownDataSetsList.add(knownDataSet);
	}

	public int getSelectedColumn() {
		return selectedColumn;
	}

	public int getSelectedRow() {
		return selectedRow;
	}

	public ArrayList<HashSet<String>> getKnownDataSetList() {
		return knownDataSetsList;
	}
}