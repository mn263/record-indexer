package front_end.client.gui.gui_panels.indexer_view.image_section;

import front_end.client.gui.ClientController;
import front_end.client.gui.batch_state.BatchState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


@SuppressWarnings("serial")
public class DrawingComponent extends JComponent {

	private static Image NULL_IMAGE = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);

	protected ClientController clientController;
	private ArrayList<DragableRect> shapes;
	private DragableRect selectedCell;
	private DragableImage image;
	private Point2D lastPoint;

	public DrawingComponent(ClientController clientController) {
		this.clientController = clientController;

		shapes = new ArrayList<>();
		selectedCell = null;

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
				for (DragableShape shape : shapes) {
					shape.adjustPosition(dx, dy);
				}
				lastPoint = new Point2D.Double(e.getX(), e.getY());
				DrawingComponent.this.repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				selectedCell = null;
				for (DragableRect shape : shapes) {
					if (shape.contains((Graphics2D) DrawingComponent.this.getGraphics(), e.getX(), e.getY())) {
						selectedCell = shape;
					}
				}
				lastPoint = new Point2D.Double(e.getX(), e.getY());
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				lastPoint = null;
			}

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
//				TODO: to implement this just change the batch's zoom and the rest will be taken care of
//				int scrollAmount;
//				if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
//					scrollAmount = e.getUnitsToScroll();
//				} else {
//					scrollAmount = e.getWheelRotation();
//				}
//
//				int dx = 0;
//				int dy = 0;
//				if (e.isShiftDown()) {
//					dx = scrollAmount;
//				} else {
//					dy = scrollAmount;
//				}
//
//				adjustShapePositions(dx, dy);
			}
		};
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
		this.addMouseWheelListener(mouseAdapter);

		String imageURL = clientController.getBatchState().getImageURL();
		try {
			if (imageURL == null) {
				image = new DragableImage(NULL_IMAGE, -300, -300, clientController);
			} else {
				changeImage(new URL(imageURL));
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void changeImage(URL imageURL) {
		Image batchImage = null;
		try {
			BufferedImage bufferedImage = ImageIO.read(imageURL);
			batchImage = bufferedImage.getScaledInstance(1000, -1, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assert batchImage != null;
		image = new DragableImage(batchImage, -300, -300, clientController);

//		TODO: add all the shapes here
//		shapes.add(new DragableRect(new Rectangle2D.Double(AREA_WIDTH/2 - 400, AREA_HEIGHT/2 - 400, 200, 200), Color.BLUE));
		this.repaint();
	}

	public void updateZoom() {
		this.repaint();
	}

	private void adjustShapePositions(double dx, double dy) {
		image.adjustPosition(dx, dy);
		for (DragableRect shape : shapes) {
			shape.adjustPosition(dx, dy);
		}
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
		for (DragableRect shape : shapes) {
			shape.draw(g2);
		}
	}
}


interface DragableShape {
	boolean contains(Graphics2D g2, double x, double y);

	void adjustPosition(double dx, double dy);

	void draw(Graphics2D g2);
}


class DragableRect implements DragableShape {

	private Rectangle2D rect;
	private Color color;

	public DragableRect(Rectangle2D rect, Color color) {
		this.rect = rect;
		this.color = color;
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
		g2.setColor(color);
		g2.fill(rect);
		// OR g2.fillRect((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight());
	}
}

class DragableImage implements DragableShape {

	private Image image;
	private Point topLeftCorner;
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
		System.out.println("Clicked--> (" + x + ", " + y + ")");
		System.out.println("topleft--> (" + topLeftCorner.getX() + ", " + topLeftCorner.getY() + ")");
		return true;
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

	public double getZoomScale() {
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