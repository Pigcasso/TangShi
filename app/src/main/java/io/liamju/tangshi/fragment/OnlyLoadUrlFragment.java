package io.liamju.tangshi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;

import io.liamju.tangshi.AppConstants;

/**
 * @author LiamJu
 * @version 1.0
 * @since 15/12/9
 */
public class OnlyLoadUrlFragment extends WebViewFragment {

    private String loadUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle values = getArguments();
        if (values != null) {
            loadUrl = values.getString(AppConstants.KEY_LOAD_URL);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WebView webView = getWebView();
        if (webView == null) return;

        webView.loadUrl(loadUrl);
    }
}
