import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.router.RouterNanoHTTPD;
import handlers.Handlers;

import java.io.IOException;
import java.util.Base64;

public class Main extends RouterNanoHTTPD {
	private static String login;
	private static String password;

	public Main(int port) throws IOException {
		super(port);
		start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
		System.out.println("Server running on port " + port);
	}

	public static void main(String[] args) {
		try {
			if (args.length != 3) {
				throw new IllegalAccessException();
			}
			login = args[1];
			password = args[2];
			new Main(Integer.parseInt(args[0]));
		} catch (Exception e) {
			System.err.println("Couldn't start server:\n" + e);
			System.err.println("arguments: port, login, password");
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
		String[] cr = new String(Base64.getDecoder().decode(session.getHeaders().get("Authorization"))).split(":");

		if (cr[0].equals(login) && cr[1].equals(password))
			return super.serve(session);
		else
			return newFixedLengthResponse(Response.Status.UNAUTHORIZED, MIME_PLAINTEXT, "");
	}
}
