package bob.eve.js.bridge.handler;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import bob.eve.js.bridge.annotation.JsAction;
import bob.eve.js.bridge.annotation.JsHandler;
import java.util.Map;

/**
 * Created by Bob on 17/12/28.
 */

@JsHandler
public class JumpHandler implements IEveHandler {
	private JumpListener listener;

	public JumpHandler(JumpListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("JumpListener must be set.");
		}
		this.listener = listener;
	}

	@JavascriptInterface
	@JsAction("jump")
	public void jump(WebView webView, Map<String, String> params) {
		listener.onJump(webView, params);
	}

	public interface JumpListener {
		void onJump(WebView webView, Map<String, String> params);
	}
}
