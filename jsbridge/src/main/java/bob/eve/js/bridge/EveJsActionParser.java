package bob.eve.js.bridge;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bob on 17/12/28.
 * parser like jsApi("action?k=v&k1=v1")
 */

public class EveJsActionParser {
	private String action = null;
	private Map<String, String> params = null;

	private EveJsActionParser() {
	}

	static EveJsActionParser ofUri(String uri) {
		if (TextUtils.isEmpty(uri)) {
			return null;
		}

		EveJsActionParser parser = new EveJsActionParser();
		int index = uri.indexOf("?");

		// Parser Action
		if (index == -1) {
			parser.setAction(uri);
			return parser;
		}

		parser.setAction(uri.substring(0, index));

		// Parser Params
		String paramsStr = uri.substring(index + 1, uri.length());
		if (!TextUtils.isEmpty(paramsStr)) {
			String[] paramsArr = paramsStr.split("&");
			Map<String, String> _params = new HashMap<>();

			for (String param : paramsArr) {
				int i = param.indexOf("=");
				if (i == -1) {
					break;
				}
				_params.put(param.substring(0, index), param.substring(index + 1, param.length()));
			}

			parser.setParams(_params);
		}

		return parser;
	}

	public String getAction() {
		return action;
	}

	private void setAction(String action) {
		this.action = action;
	}

	public Map<String, String> getParams() {
		return params;
	}

	private void setParams(Map<String, String> params) {
		this.params = params;
	}
}
