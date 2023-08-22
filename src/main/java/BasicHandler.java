import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.router.RouterNanoHTTPD;

import static fi.iki.elonen.NanoHTTPD.MIME_PLAINTEXT;

abstract class BasicHandler extends RouterNanoHTTPD.DefaultHandler {
	@Override
	public String getMimeType() {
		return MIME_PLAINTEXT;
	}

	@Override
	public NanoHTTPD.Response.IStatus getStatus() {
		return NanoHTTPD.Response.Status.OK;
	}
}
