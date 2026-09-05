package com.kabo.shahidtv;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.JsResult;
import android.webkit.ConsoleMessage;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.pm.PackageManager;

public class MainActivity extends Activity {

    private static final String SHAHID_URL = "https://shahid.mbc.net/";
    private WebView webView;
    private TextView status;
    private View customView;
    private WebChromeClient.CustomViewCallback customViewCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManagerFlags.FLAG_FULLSCREEN,
                WindowManagerFlags.FLAG_FULLSCREEN);
        setContentView(com.kabo.shahidtv.R.layout.activity_main);

        webView = (WebView) findViewById(com.kabo.shahidtv.R.id.webview);
        status = (TextView) findViewById(com.kabo.shahidtv.R.id.status);

        configureWebView();
        webView.loadUrl(SHAHID_URL);
        webView.requestFocus(View.FOCUS_DOWN);
    }

    // Make the page see a conventional desktop-browser environment at the
    // JavaScript layer as well as at the HTTP User-Agent layer. This is only a
    // compatibility test; it does not alter DRM, licenses, or content protection.
    private void injectDesktopBrowserIdentity(WebView view) {
        String script = "javascript:(function(){" +
                "try{" +
                "Object.defineProperty(Navigator.prototype,'platform',{get:function(){return 'Win32';},configurable:true});" +
                "Object.defineProperty(Navigator.prototype,'vendor',{get:function(){return 'Google Inc.';},configurable:true});" +
                "Object.defineProperty(Navigator.prototype,'maxTouchPoints',{get:function(){return 0;},configurable:true});" +
                "Object.defineProperty(Navigator.prototype,'appVersion',{get:function(){return '5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36';},configurable:true});" +
                "Object.defineProperty(navigator,'userAgent',{get:function(){return 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36';},configurable:true});" +
                "Object.defineProperty(navigator,'platform',{get:function(){return 'Win32';},configurable:true});" +
                "Object.defineProperty(navigator,'vendor',{get:function(){return 'Google Inc.';},configurable:true});" +
                "Object.defineProperty(navigator,'maxTouchPoints',{get:function(){return 0;},configurable:true});" +
                "window.chrome=window.chrome||{runtime:{}};" +
                "}catch(e){}" +
                "})();";
        view.loadUrl(script);
    }

    private void configureWebView() {
        WebSettings s = webView.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setDatabaseEnabled(true);
        s.setJavaScriptCanOpenWindowsAutomatically(true);
        s.setSupportMultipleWindows(false);
        s.setBuiltInZoomControls(false);
        s.setDisplayZoomControls(false);
        s.setLoadWithOverviewMode(true);
        s.setUseWideViewPort(true);
        s.setLoadsImagesAutomatically(true);
        s.setBlockNetworkImage(false);
        s.setMediaPlaybackRequiresUserGesture(false);
        s.setDefaultTextEncodingName("UTF-8");
        s.setCacheMode(WebSettings.LOAD_DEFAULT);

        CookieManager.getInstance().setAcceptCookie(true);

        // V5: Desktop identity compatibility test.
        // HTTP layer: identify as ordinary desktop Chrome on Windows.
        // JS layer: also expose desktop-style navigator values after each page load.
        // This does not alter DRM, licenses, or content-protection mechanisms.
        String desktopUA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36";
        s.setUserAgentString(desktopUA);

        // Settings used by the KitKat Chrome-style browser for older HTML5 sites.
        s.setAllowFileAccess(true);
        s.setAllowContentAccess(true);
        s.setAppCacheEnabled(true);
        s.setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());

        webView.setBackgroundColor(Color.BLACK);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // Apply the desktop identity to the page JavaScript environment too.
                injectDesktopBrowserIdentity(view);
                showStatus("Desktop Chrome mode | UA: Windows Chrome");
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                showStatus("WebView error: " + description);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                if (customView != null) {
                    callback.onCustomViewHidden();
                    return;
                }
                customView = view;
                customViewCallback = callback;
                ViewGroup decor = (ViewGroup) getWindow().getDecorView();
                decor.addView(customView, new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                webView.setVisibility(View.GONE);
            }

            @Override
            public void onHideCustomView() {
                if (customView == null) return;
                ViewGroup decor = (ViewGroup) getWindow().getDecorView();
                decor.removeView(customView);
                customView = null;
                webView.setVisibility(View.VISIBLE);
                if (customViewCallback != null) {
                    customViewCallback.onCustomViewHidden();
                    customViewCallback = null;
                }
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                showStatus(message);
                result.cancel();
                return true;
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage cm) {
                showStatus("JS: " + cm.message());
                return true;
            }
        });
    }

    private void showStatus(String msg) {
        if (status == null) return;
        status.setText(msg);
        status.setVisibility(View.VISIBLE);
        status.postDelayed(new Runnable() {
            @Override public void run() {
                if (status != null) status.setVisibility(View.GONE);
            }
        }, 4500);
    }

    private void diagnostics() {
        String webviewPackage = "unknown";
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                PackageManager pm = getPackageManager();
                android.content.pm.PackageInfo p =
                        pm.getPackageInfo("com.google.android.webview", 0);
                webviewPackage = p.versionName;
            } catch (Exception ignored) {}
        }

        String msg =
                "Android: " + Build.VERSION.RELEASE +
                "\nAPI: " + Build.VERSION.SDK_INT +
                "\nModel: " + Build.MODEL +
                "\nBoard: " + Build.BOARD +
                "\nHardware: " + Build.HARDWARE +
                "\nCPU ABI: " + Build.CPU_ABI +
                "\nDesktop UA:\n" + webView.getSettings().getUserAgentString() +
                "\n\nJS identity is injected as Windows/Chrome after page load.";

        status.setText(msg);
        status.setVisibility(View.VISIBLE);
        Toast.makeText(this, "تشخيص الجهاز جاهز أسفل الشاشة", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // MENU = 82 on Android TV remotes.
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            diagnostics();
            return true;
        }

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (customView != null) {
                if (customViewCallback != null) {
                    customViewCallback.onCustomViewHidden();
                    customViewCallback = null;
                }
                ViewGroup decor = (ViewGroup) getWindow().getDecorView();
                if (customView.getParent() == decor) {
                    decor.removeView(customView);
                }
                customView = null;
                webView.setVisibility(View.VISIBLE);
                return true;
            }
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.stopLoading();
            webView.destroy();
        }
        super.onDestroy();
    }

    // Avoid a compile dependency on a newer constant holder.
    private static final class WindowManagerFlags {
        static final int FLAG_FULLSCREEN = WindowManagerLayoutParams.FLAG_FULLSCREEN;
    }

    private static final class WindowManagerLayoutParams {
        static final int FLAG_FULLSCREEN = 0x00000400;
    }
}
