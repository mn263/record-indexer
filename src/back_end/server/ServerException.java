package back_end.server;

/**
 * Handles any exceptions thrown by the Server class
 * User: matt nielson
 * Date: 10/12/13
 * Time: 9:21 AM
 */
@SuppressWarnings("serial")
public class ServerException extends Exception {

	public ServerException() {
		return;
	}

	public ServerException(String message) {
		super(message);
	}

	public ServerException(Throwable throwable) {
		super(throwable);
	}

	public ServerException(String message, Throwable throwable) {
		super(message, throwable);
	}

}