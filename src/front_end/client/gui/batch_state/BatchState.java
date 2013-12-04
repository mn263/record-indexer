package front_end.client.gui.batch_state;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: matt
 * Date: 11/20/13
 * Time: 10:15 AM
 */
public class BatchState {

	public enum Zoom {
		QUARTER, HALF, THREE_QUARTER, ONE, ONE_AND_QUARTER, ONE_AND_HALF, ONE_AND_THREE_QUARTER, TWO
	}

	private String userName = "";
	private String password = "";
	private String imageURL = null;
	private int currentProject = -1;
	private List<String> recordValues = new ArrayList<>();
	private Point windowPosition = new Point(100, 100);
	private Point windowDimensions = new Point(1000, 650);
	private int horizDivPosit = 442;
	private int vertDivPosit = 328;
	private Zoom zoomLevel = Zoom.ONE;
	private double scrollPosition = 0.0;
	private boolean isHighlighted = true;
	private boolean isInverted = false;
	private boolean isTableEntryTab = true;
	private boolean isFileHelpTab = true;
	transient private List<BatchStateListener> batchStateListeners = new ArrayList<>();

	public void addBSListener(BatchStateListener bsListener) {
		this.batchStateListeners.add(bsListener);
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

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
		for (BatchStateListener bsListener : batchStateListeners) {
			bsListener.ImageURLChanged();
		}
	}

	public void setCurrentProjectId(int currentProject) {
		this.currentProject = currentProject;
	}

	public int getCurrentProjectId() {
		return currentProject;
	}

	public List<String> getRecordValues() {
		return recordValues;
	}

	public void setRecordValues(List<String> recordValues) {
		this.recordValues = recordValues;
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
		for (BatchStateListener bsListener : batchStateListeners) {
			bsListener.ZoomedChanged();
		}
	}

	public double getScrollPosition() {
		return scrollPosition;
	}

	public void setScrollPosition(double scrollPosition) {
		this.scrollPosition = scrollPosition;
	}

	public boolean isHighlighted() {
		return isHighlighted;
	}

	public void setHighlighted(boolean highlighted) {
		isHighlighted = highlighted;
	}

	public boolean isInverted() {
		return isInverted;
	}

	public void setInverted(boolean inverted) {
		isInverted = inverted;
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

	public List<BatchStateListener> getBatchStateListeners() {
		return batchStateListeners;
	}

	public void setBatchStateListeners(List<BatchStateListener> batchStateListeners) {
		this.batchStateListeners = batchStateListeners;
	}
}