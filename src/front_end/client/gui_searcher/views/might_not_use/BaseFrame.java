package front_end.client.gui_searcher.views.might_not_use;

import javax.swing.*;

public class BaseFrame extends JFrame {

	private String host;
	private String port;

	public BaseFrame(String host, String port) {
		super();
		this.host = host;
		this.port = port;
	}

	void setHost(String host) {
		this.host = host;
	}

	public String getHost() {
		return host;
	}

	void setPort(String port) {
		this.port = port;
	}

	public String getPort() {
		return port;
	}
}

