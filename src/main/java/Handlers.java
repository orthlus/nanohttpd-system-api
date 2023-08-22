import system.Response;

import static system.SystemProcess.callProcess;

public class Handlers {
	public static class PrettyHandler extends BasicHandler {
		@Override
		public String getText() {
			return getResult(callProcess("bash /root/wg-stat.sh"));
		}
	}

	public static class RawHandler extends BasicHandler {
		@Override
		public String getText() {
			return getResult(callProcess("bash /root/wg-stat-raw.sh"));
		}
	}

	public static class UsersHandler extends BasicHandler {
		@Override
		public String getText() {
			return getResult(callProcess("bash /root/wg-users.sh"));
		}
	}

	private static String getResult(Response response) {
		if (response.exitCode() == 0)
			return response.stdout();
		else
			throw new InternalErrorResponseException(response.toString());
	}
}
