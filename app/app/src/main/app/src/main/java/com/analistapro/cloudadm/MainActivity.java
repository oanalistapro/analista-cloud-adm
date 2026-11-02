
package com.analistapro.cloudadm;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebResourceRequest;

public class MainActivity extends Activity {

    private WebView web;

    private static final String APP_URL =
        "https://oanalistapro.github.io/analista-cloud-adm/";

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        web = new WebView(this);
        setContentView(web);

        WebSettings st = web.getSettings();
        st.setJavaScriptEnabled(true);
        st.setDomStorageEnabled(true);
        st.setAllowFileAccess(false);
        st.setAllowContentAccess(false);
        st.setMixedContentMode(
            WebSettings.MIXED_CONTENT_NEVER_ALLOW
        );

        web.setWebChromeClient(new WebChromeClient());

        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(
                WebView view,
                WebResourceRequest request
            ) {
                Uri uri = request.getUrl();

                if ("https".equals(uri.getScheme())
                    && "oanalistapro.github.io".equalsIgnoreCase(uri.getHost())) {
                    return false;
                }

                if ("https".equals(uri.getScheme())
                    || "http".equals(uri.getScheme())) {
                    startActivity(new Intent(
                        Intent.ACTION_VIEW, uri
                    ));
                }
                return true;
            }
        });

        web.loadUrl(APP_URL);
    }

    @Override
    public void onBackPressed() {
        if (web != null && web.canGoBack()) {
            web.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (web != null) {
            web.destroy();
        }
        super.onDestroy();
    }
}
