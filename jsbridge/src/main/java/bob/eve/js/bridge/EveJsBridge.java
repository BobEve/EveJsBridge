package bob.eve.js.bridge;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import bob.eve.js.bridge.annotation.JsAction;
import bob.eve.js.bridge.handler.IEveHandler;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bob on 17/12/28.
 */

public class EveJsBridge {
	private static List<IEveHandler> sHandler = new ArrayList<>();
	private WebView mWebView;
	public String JS_BRIDGE = "_eveBridge";

	@SuppressLint( { "JavascriptInterface", "AddJavascriptInterface", "SetJavaScriptEnabled" })
	public void setWebView(WebView webView) {
		if (webView == null) {
			throw new IllegalArgumentException("WebView must be not null.");
		}
		this.mWebView = webView;
		mWebView.getSettings()
						.setJavaScriptEnabled(true);
		mWebView.addJavascriptInterface(this, JS_BRIDGE);
	}

	public static void registerJsActionHandler(IEveHandler handler) {
		sHandler.add(handler);
	}

	public static void unRegisterJsActionHandler(IEveHandler handler) {
		sHandler.remove(handler);
	}

	@JavascriptInterface
	public void jump(String params) {
		jsAction("jump?" + params);
	}

	@JavascriptInterface
	public void jsAction(String uri) {
		final EveJsActionParser parser = EveJsActionParser.ofUri(uri);
		if (parser == null) {
			return;
		}

		for (final IEveHandler handler : sHandler) {
			Method[] methods = handler.getClass()
																.getMethods();

			for (final Method method : methods) {
				Annotation[] annotations = method.getAnnotations();

				for (Annotation annotation : annotations) {
					// 匹配JsAction注解
					if (JsAction.class.isInstance(annotation)) {
						String value = ((JsAction) annotation).value();
						String action = parser.getAction();

						// 匹配注解方法是否一致
						if (!TextUtils.isEmpty(action) && action.equals(value)) {
							mWebView.post(new Runnable() {
								@Override
								public void run() {
									try {
										// 反射调用
										method.invoke(handler, mWebView, parser.getParams());
									} catch (IllegalAccessException e) {
										e.printStackTrace();
									} catch (InvocationTargetException e) {
										e.printStackTrace();
									}
								}
							});
						}
					}
				}
			}
		}
	}
}
