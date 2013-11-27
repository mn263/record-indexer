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

	private enum Zoom {
		X_ONE, X_TWO, X_THREE, X_FOUR, X_FIVE, X_SIX
	}

	private String userName = "";
	private String password = "";
	private String imageURL;
	private List<String> recordValues;
	private Point windowPosition;
	private Point windowDimensions;
	private int horizDivPosit;
	private int vertDivPosit;
	private Zoom zoomLevel;
	private double scrollPosition;
	private boolean isHighlighted;
	private boolean isInverted;
	transient private List<BatchStateListener> batchStateListeners = new ArrayList<>();


	public BatchState() {
		this.imageURL = null;
		this.recordValues = new ArrayList<>();
		this.windowPosition = new Point(100, 100);
		this.windowDimensions = new Point(1000, 650);
		this.horizDivPosit = 442;
		this.vertDivPosit = 328;
		this.zoomLevel = Zoom.X_ONE;
		this.scrollPosition = 0.0;
		this.isHighlighted = true;
		this.isInverted = false;
	}

//	public BatchState(String imageURL, List<String> recordValues, Point windowPosition, Point windowDimensions,
//					  int horizDivPosit, int vertDivPosit, Zoom zoomLevel, double scrollPosition, boolean isHighlighted, boolean isInverted) {
//		this.imageURL = imageURL;
//		this.recordValues = recordValues;
//		this.windowPosition = windowPosition;
//		this.windowDimensions = windowDimensions;
//		this.horizDivPosit = horizDivPosit;
//		this.vertDivPosit = vertDivPosit;
//		this.zoomLevel = zoomLevel;
//		this.scrollPosition = scrollPosition;
//		this.isHighlighted = isHighlighted;
//		this.isInverted = isInverted;
//	}

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

	public List<BatchStateListener> getBatchStateListeners() {
		return batchStateListeners;
	}

	public void setBatchStateListeners(List<BatchStateListener> batchStateListeners) {
		this.batchStateListeners = batchStateListeners;
	}
}