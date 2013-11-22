package front_end.client.gui_searcher;

/**
 * User: matt
 * Date: 11/20/13
 * Time: 10:15 AM
 */
public class BatchState {

//	When a user logs out or exits the program, the current state of their work should be saved to disk. The next time
// 	the user logs in, the state of their work should be restored from disk. Program state should be saved on a per-user
// 	basis, since over time there may be multiple users that use the program on the same machine. In order to prevent
// 	users from interfering with each other, each user’s state should be saved separately.
//	The following information should be saved at logout/exit and restored at login:
//	Batch State
//	1. Batch image
//	1. Record field values
//	Window State
//	1. Position of the Indexing Window on the desktop
//	1. Width and height of the Indexing Window
//	1. Positions of the horizontal and vertical split panel dividers
//	Image State
//	1.  Zoom level
//	Scroll position
//	1. Highlights visible setting
//	1. Image inverted setting


//	FROM TA
//	this class needs a user, current window settings, data model, list of batchstatelisteners, the host, and the port


	private static BatchState instance = null;

	public static BatchState inst() {
		if (instance == null) {
			instance = new BatchState();
		}
		return instance;
	}

	private String host = null;
	private String port = null;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
}
