import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.router.RouterNanoHTTPD;

import java.io.IOException;
import java.util.Base64;

import static java.lang.Integer.parseInt;

public class Main extends RouterNanoHTTPD {
	private static String login;
	private static String password;

	public Main(int port) throws IOException {
		super(port);
		addMappings();
		start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
		System.out.println("Server running on port " + port);
	}

	public static void main(String[] args) {
		try {
			if (args.length != 3) {
				throw new IllegalAccessException();
			}
			int port = parseInt(args[0]);
			login = args[1];
			password = args[2];

			new Main(port);
		} catch (Exception e) {
			System.err.println("Arguments: port, login, password");
			System.err.println("Couldn't start server:\n" + e);
		}
	}

	@Override
	public void addMappings() {
		addRoute("/pretty-stat", Handlers.PrettyHandler.class);
		addRoute("/raw-stat", Handlers.RawHandler.class);
		addRoute("/users", Handlers.UsersHandler.class);
	}

	@Override
	public Response serve(IHTTPSession session) {
		String authorization = session.getHeaders().get("authorization");
		if (authorization == null)
			return newFixedLengthResponse(Response.Status.UNAUTHORIZED, MIME_PLAINTEXT, "");

		String header = authorization.replaceAll("^Basic ", "");
		String[] cr = new String(Base64.getDecoder().decode(header)).split(":");

		if (cr[0].equals(login) && cr[1].equals(password))
			try {
				return super.serve(session);
			} catch (InternalErrorResponseException e) {
				return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT, e.getMessage());
			}
		else
			return newFixedLengthResponse(Response.Status.UNAUTHORIZED, MIME_PLAINTEXT, "");
	}
}
