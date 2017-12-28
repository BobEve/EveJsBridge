# EveJsBridge
Android和Js交互过程的封装

## Summary 
```java
public class JumpListener implements JumpHandler.JumpListener {
	@Override
	public void onJump(WebView webView, Map<String, String> params) {

	}
}

private void initWebView() {
		EveJsBridge.registerJsActionHandler(new JumpHandler(new JumpListener()));

		WebView webView = findViewById(R.id.jsWebView);
		jsBridge = new EveJsBridge();
		jsBridge.setWebView(webView);
		webView.loadUrl("file:///android_asset/js.html");
}
```
so easy!
这样就设置好了，当人关于监听设置，具体其他的Action事件自定义查看Dome即可

欢迎大家提出意见！

# Licensed
<br />Copyright 2017 BobEve.<br />
<br />Licensed under the Apache License, Version 2.0 (the "License");
<br />you may not use this file except in compliance with the License.
<br />You may obtain a copy of the License at
<br />
<i>http://www.apache.org/licenses/LICENSE-2.0</i>
<br />Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions
and limitations under the License.<br />
