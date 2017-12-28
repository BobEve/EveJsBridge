package bob.eve.js.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.Toast;
import bob.eve.js.bridge.EveJsBridge;
import bob.eve.js.bridge.annotation.JsAction;
import bob.eve.js.bridge.handler.IEveHandler;
import bob.eve.js.bridge.handler.JumpHandler;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IEveHandler {
	private EveJsBridge jsBridge;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initJsAction();

		initWebView();
	}

	private void initWebView() {
		WebView webView = findViewById(R.id.jsWebView);
		jsBridge = new EveJsBridge();
		jsBridge.setWebView(webView);
		webView.loadUrl("file:///android_asset/js.html");
	}

	private void initJsAction() {
		EveJsBridge.registerJsActionHandler(new JumpHandler(new JumpListener()));
		EveJsBridge.registerJsActionHandler(this);
	}

	@JsAction("jump")
	public void onJump(WebView webView, Map<String, String> params) {
		Toast.makeText(this, "On MainActivity onJump", Toast.LENGTH_SHORT)
				 .show();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
