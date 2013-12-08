package front_end.client.gui.gui_panels.indexer_view.image_section;

import front_end.client.gui.ClientController;
import front_end.client.gui.batch_state.BatchState;
import shared.communication.results.DownloadBatch_Result;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.net.URL;
import java.util.List;


@SuppressWarnings("serial")
public class DrawingComponent extends JComponent {

	private static Image NULL_IMAGE = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);

	protected ClientController clientController;
	protected BatchState batchState;
	private DragableRect shape;
	private DragableImage image;
	private Point2D lastPoint;

	public DrawingComponent(final ClientController clientController) {
		this.clientController = clientController;
		this.batchState = clientController.getBatchState();
		shape = null;

		this.setBackground(Color.GRAY);
		int AREA_WIDTH = 1000;
		int AREA_HEIGHT = 400;
		this.setPreferredSize(new Dimension(AREA_WIDTH, AREA_HEIGHT));
		this.setMinimumSize(new Dimension(100, 100));
		this.setMaximumSize(new Dimension(AREA_WIDTH, AREA_HEIGHT));

		MouseAdapter mouseAdapter = new MouseAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				int dx = e.getX() - (int) lastPoint.getX();
				int dy = e.getY() - (int) lastPoint.getY();
				adjustShapePositions(dx, dy);
				lastPoint = new Point2D.Double(e.getX(), e.getY());
				DrawingComponent.this.repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				lastPoint = new Point2D.Double(e.getX(), e.getY());
				if (clientController.getBatchState().hasDownloadedBatch()) {
					image.contains((Graphics2D) getGraphics(), e.getX(), e.getY());
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				lastPoint = null;
			}

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				int scrollAmount;
				if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
					scrollAmount = e.getUnitsToScroll();
				} else {
					scrollAmount = e.getWheelRotation();
				}

				BatchState.Zoom zoom = batchState.getZoomLevel();
				if (scrollAmount > 0) {
					zoomIn(zoom);
				} else {
					zoomOut(zoom);
				}
			}
		};
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
		this.addMouseWheelListener(mouseAdapter);

		if (batchState.hasDownloadedBatch()) {
			changeImage();
		} else {
			image = new DragableImage(NULL_IMAGE, -300, -300, clientController);
		}
	}

	private void zoomIn(BatchState.Zoom zoom) {
		switch (zoom) {
			case QUARTER:
				break;
			case HALF:
				batchState.setZoomLevel(BatchState.Zoom.QUARTER);
				break;
			case THREE_QUARTER:
				batchState.setZoomLevel(BatchState.Zoom.HALF);
				break;
			case ONE:
				batchState.setZoomLevel(BatchState.Zoom.THREE_QUARTER);
				break;
			case ONE_AND_QUARTER:
				batchState.setZoomLevel(BatchState.Zoom.ONE);
				break;
			case ONE_AND_HALF:
				batchState.setZoomLevel(BatchState.Zoom.ONE_AND_QUARTER);
				break;
			case ONE_AND_THREE_QUARTER:
				batchState.setZoomLevel(BatchState.Zoom.ONE_AND_HALF);
				break;
			case TWO:
				batchState.setZoomLevel(BatchState.Zoom.ONE_AND_THREE_QUARTER);
				break;
			default:
				System.out.println("The zoom level was an invalid one");
				break;
		}
	}

	private void zoomOut(BatchState.Zoom zoom) {
		switch (zoom) {
			case QUARTER:
				batchState.setZoomLevel(BatchState.Zoom.HALF);
				break;
			case HALF:
				batchState.setZoomLevel(BatchState.Zoom.THREE_QUARTER);
				break;
			case THREE_QUARTER:
				batchState.setZoomLevel(BatchState.Zoom.ONE);
				break;
			case ONE:
				batchState.setZoomLevel(BatchState.Zoom.ONE_AND_QUARTER);
				break;
			case ONE_AND_QUARTER:
				batchState.setZoomLevel(BatchState.Zoom.ONE_AND_HALF);
				break;
			case ONE_AND_HALF:
				batchState.setZoomLevel(BatchState.Zoom.ONE_AND_THREE_QUARTER);
				break;
			case ONE_AND_THREE_QUARTER:
				batchState.setZoomLevel(BatchState.Zoom.TWO);
				break;
			case TWO:
				break;
			default:
				System.out.println("The zoom level was an invalid one");
				break;
		}
	}

	public void changeImage() {
		Image batchImage = null;
		BufferedImage bufferedImage = null;
		try {
			URL imageURL = new URL(clientController.getBatchState().getDownloadBatchResult().getImageURL());
			bufferedImage = ImageIO.read(imageURL);
			int width = bufferedImage.getWidth();
			batchImage = bufferedImage.getScaledInstance(width, -1, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assert batchImage != null;
		if (batchState.isInverted()) {
//			changeImage();
//			BufferedImage img2 = bufferedImage;
//			if(invert) {
			batchImage = new RescaleOp(-1.0f, 255.0f, null).filter(bufferedImage, null);
//			}
		}

		image = new DragableImage(batchImage, -300, -300, clientController);
		this.repaint();
	}

	private void createShape(Point topLeftCorner) {
		DownloadBatch_Result result = batchState.getDownloadBatchResult();
		double firstYCoord = result.getFirstYCoord();
		double recordHeight = result.getRecordHeight();
		int selectedCellRow = batchState.getSelectedRow();
		int selectedCellColumn = batchState.getSelectedColumn();
		double cellYCoord = topLeftCorner.getY() + firstYCoord + (recordHeight * selectedCellRow);
		DownloadBatch_Result.FieldInformation field = result.getFieldInformationList().get(selectedCellColumn);
		double xCoord = field.setXCoord();
		double shapeWidth = field.getPixelWidth();
		double cellXCoord = topLeftCorner.getX() + xCoord;
		shape = new DragableRect(new Rectangle2D.Double(cellXCoord, cellYCoord, shapeWidth, recordHeight));
	}

	public void updateZoom() {
		this.repaint();
	}

	private void adjustShapePositions(double dx, double dy) {
		image.adjustPosition(dx, dy);
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		drawBackground(g2);
		drawShapes(g2);
	}

	private void drawBackground(Graphics2D g2) {
		g2.setColor(getBackground());
		g2.fillRect(0, 0, getWidth(), getHeight());
	}

	private void drawShapes(Graphics2D g2) {
		image.draw(g2);
		if (batchState.hasDownloadedBatch() && batchState.isHighlighted()) {
			createShape(image.topLeftCorner);
			shape.draw(g2);
		}
	}

	public void updateRecordSelected() {
		repaint();
	}

	public void invertImage() {
		changeImage();
		repaint();
	}
}


interface DragableShape {
	boolean contains(Graphics2D g2, double x, double y);

	void adjustPosition(double dx, double dy);

	void draw(Graphics2D g2);
}


class DragableRect implements DragableShape {

	private Rectangle2D rect;

	public DragableRect(Rectangle2D rect) {
		this.rect = rect;
	}

	@Override
	public boolean contains(Graphics2D g2, double x, double y) {
		return rect.contains(x, y);
	}

	@Override
	public void adjustPosition(double dx, double dy) {
		rect.setRect(rect.getX() + dx, rect.getY() + dy, rect.getWidth(), rect.getHeight());
	}

	@Override
	public void draw(Graphics2D g2) {
		Color blue = new Color(66, 158, 255, 212);
		g2.setColor(blue);
		g2.fill(rect);
	}
}

class DragableImage implements DragableShape {

	private Image image;
	public Point topLeftCorner;
	private int imageWidth;
	private int imageHeight;
	private ClientController clientController;

	public DragableImage(Image image, int x, int y, ClientController clientController) {
		this.topLeftCorner = new Point(x, y);
		this.clientController = clientController;
		this.image = image;
		this.imageWidth = image.getWidth(null);
		this.imageHeight = image.getHeight(null);
	}

	@Override
	public boolean contains(Graphics2D g2, double x, double y) {
		int selectedRow = getSelectedRow(y);
		int selectedColumn = getSelectedColumn(x);
		if (selectedRow < 0 || selectedColumn < 0) {
			return false;
		} else {
			clientController.getBatchState().changeSelectedCell(selectedRow, selectedColumn);
			return true;
		}
	}

	private int getSelectedRow(double y) {
		double convertedY;
		double zoom = getZoomScale();
		convertedY = y - topLeftCorner.getY() * zoom - 200;
		convertedY = convertedY / zoom;
		BatchState batchState = clientController.getBatchState();
		DownloadBatch_Result result = batchState.getDownloadBatchResult();
		double firstYCoord = result.getFirstYCoord();
		int rowCount = result.getRecordCount();
		double recordHeight = result.getRecordHeight();
		convertedY = convertedY - firstYCoord;
		for (int row = 0; row < rowCount; row++) {
			convertedY = convertedY - recordHeight;
			if (-recordHeight < convertedY && convertedY < 0) {
				return row;
			}
		}
		return -1;
	}

	private int getSelectedColumn(double x) {
		double convertedX;
		double zoom = getZoomScale();
		convertedX = x - topLeftCorner.getX() * zoom - 500;
		convertedX = convertedX / zoom;
		BatchState batchState = clientController.getBatchState();
		DownloadBatch_Result result = batchState.getDownloadBatchResult();
		List<DownloadBatch_Result.FieldInformation> fieldInfoList = result.getFieldInformationList();
		int columnNumber = 0;
		for (DownloadBatch_Result.FieldInformation field : fieldInfoList) {
			if (convertedX > field.setXCoord() && convertedX < (field.setXCoord() + field.getPixelWidth())) {
				return columnNumber;
			}
			columnNumber++;
		}
		return -1;
	}

	@Override
	public void adjustPosition(double dx, double dy) {
		dx = dx / getZoomScale();
		dy = dy / getZoomScale();
		topLeftCorner = new Point((int) topLeftCorner.getX() + (int) dx, (int) topLeftCorner.getY() + (int) dy);
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.translate(1000 / 2, 400 / 2);
		g2.scale(getZoomScale(), getZoomScale());
		g2.drawImage(image, (int) topLeftCorner.getX(), (int) topLeftCorner.getY(), imageWidth, imageHeight, null);
	}

	private double getZoomScale() {
		BatchState.Zoom zoom = clientController.getBatchState().getZoomLevel();
		switch (zoom) {
			case QUARTER:
				return 0.25;
			case HALF:
				return 0.5;
			case THREE_QUARTER:
				return 0.75;
			case ONE:
				return 1.0;
			case ONE_AND_QUARTER:
				return 1.25;
			case ONE_AND_HALF:
				return 1.5;
			case ONE_AND_THREE_QUARTER:
				return 1.75;
			case TWO:
				return 2.0;
			default:
				System.out.println("The zoom level was an invalid one");
				break;
		}
		return 1.0;
	}
}