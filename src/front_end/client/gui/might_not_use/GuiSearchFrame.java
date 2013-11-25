package front_end.client.gui.might_not_use;

import front_end.client.gui.controllers.BaseController;
import front_end.client.gui.gui_panels.indexer_view.ImagePanel;
import front_end.client.gui.gui_panels.indexer_view.ImagesPanel;

import java.net.URL;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GuiSearchFrame {

	private BaseController _controller;
	private SettingsPanel _settingsPanel;
	private TextPanel _projectsPanel;
	private TextPanel _fieldsPanel;
	private ParamPanel _paramPanel;
	private ImagesPanel _imagesPanel;
	private ImagePanel _imagePanel;

	public GuiSearchFrame() {
//		super("localhost", "8467545454545454545545454545454");

//		setTitle("GUI Searcher");
//
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
//
//		add(Box.createRigidArea(DOUBLE_VSPACE));
//		_settingsPanel = new SettingsPanel();
////		add(_settingsPanel);
//
//
//		_projectsPanel = new TextPanel("Projects");
//
//		_fieldsPanel = new TextPanel("Fields");
//
////		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
////				_projectsPanel, _fieldsPanel);
////		splitPane.setResizeWeight(0.35);
////		splitPane.setAlignmentX(Component.CENTER_ALIGNMENT);
////		add(splitPane);
//
//		add(Box.createRigidArea(DOUBLE_VSPACE));
//
//		add(Box.createRigidArea(DOUBLE_VSPACE));
//
//		_paramPanel = new ParamPanel();
////		add(_paramPanel);
//
//		add(Box.createRigidArea(DOUBLE_VSPACE));
//
//		add(Box.createRigidArea(DOUBLE_VSPACE));
//
//		add(Box.createRigidArea(DOUBLE_VSPACE));
//
//		_imagesPanel = new ImagesPanel();
////		add(_imagesPanel);
//
//
//		add(Box.createRigidArea(DOUBLE_VSPACE));
//
//		add(Box.createRigidArea(DOUBLE_VSPACE));
//
//		_imagePanel = new ImagePanel();
//
//		add(Box.createRigidArea(DOUBLE_VSPACE));
//
//		add(Box.createRigidArea(DOUBLE_VSPACE));
//
//
//		addWindowListener(new WindowAdapter() {
//			@Override
//			public void windowClosed(WindowEvent arg0) {
//				// TODO Auto-generated method stub
//			}
//		});
//
//		pack();
//
//		setMinimumSize(getPreferredSize());
	}

	public BaseController getController() {
		return _controller;
	}

	public void setController(BaseController value) {
		_controller = value;
//		_settingsPanel.setController(value);
//		_paramPanel.setController(value);
//		_projectsPanel.setController(value);
//		_fieldsPanel.setController(value);
//		_imagesPanel.setController(value);
//		_imagePanel.setController(value);
	}

	public void showImage(URL imageURL) {
		_imagePanel.changeImage(imageURL);
//		add(_imagePanel);
	}

	// BaseFrame methods
	//

	public void setHost(String value) {
		_settingsPanel.setHost(value);
	}

	public String getHost() {
		return _settingsPanel.getHost();
	}

	public void setPort(String value) {
		_settingsPanel.setPort(value);
	}

	public String getPort() {
		return _settingsPanel.getPort();
	}

	public void setOperation(ServerOp value) {
//		_settingsPanel.setOperation(value);
	}

	public ServerOp getOperation() {
		return _settingsPanel.validateUser();
	}

	public void setParameterNames(String[] value) {
		_paramPanel.setParameterNames(value);
	}

	public String[] getParameterNames() {
		return _paramPanel.getParameterNames();
	}

	public void setParameterValues(String[] value) {
		_paramPanel.setParameterValues(value);
	}

	public String[] getParameterValues() {
		return _paramPanel.getParameterValues();
	}


	public void setRequest(String value) {
		_projectsPanel.setText(value);
	}

	public String getRequest() {
		return _projectsPanel.getText();
	}

	public void setProjectsPanel(String value) {
		_projectsPanel.setText(value);
	}

	public String getProjectsPanel() {
		return _projectsPanel.getText();
	}

	public void setFieldsPanel(String value) {
		_fieldsPanel.setText(value);
	}

	public String getFieldsPanel() {
		return _fieldsPanel.getText();
	}

	public void setImagesPanel(ArrayList<String> values) {
		_imagesPanel.setImages(values);
	}

	public void clearImagesPanel() {
		_imagePanel.clearImage();
		_imagesPanel.clearImages();
	}

	public String getImagesPanel() {
		return _imagesPanel.toString();
	}
}

