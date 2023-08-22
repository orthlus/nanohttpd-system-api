package handlers;

public class Handlers {
	public static class PrettyHandler extends BasicHandler {
		@Override
		public String getText() {
			return null;
		}
	}

	public static class RawHandler extends BasicHandler {
		@Override
		public String getText() {
			return null;
		}
	}

	public static class UsersHandler extends BasicHandler {
		@Override
		public String getText() {
			return null;
		}

		@Override
		public String getMimeType() {
			return "application/json";
		}
	}
}
