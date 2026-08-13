package com.kotengine.chameleon.webview

import android.annotation.SuppressLint
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.webkit.WebViewClientCompat
import androidx.webkit.WebViewCompat
import androidx.webkit.WebViewFeature
import com.kotengine.chameleon.data.DeviceProfile
import com.kotengine.chameleon.data.ORIGINAL_DEVICE

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun SpoofWebViewScreen(
    url: String,
    profile: DeviceProfile,
    modifier: Modifier = Modifier,
) {
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true

                if (profile.id != ORIGINAL_DEVICE.id) {
                    settings.userAgentString = profile.userAgent

                    if (WebViewFeature.isFeatureSupported(WebViewFeature.DOCUMENT_START_SCRIPT)) {
                        WebViewCompat.addDocumentStartJavaScript(
                            this,
                            ClientHintsInjector.buildScript(profile),
                            setOf("*"),
                        )
                    }

                    webViewClient = SpoofingWebViewClient()
                } else {
                    webViewClient = WebViewClient()
                }

                loadUrl(url)
            }
        },
        update = { webView ->
            if (webView.url != url) {
                webView.loadUrl(url)
            }
        },
    )
}

private class SpoofingWebViewClient : WebViewClientCompat() {
    override fun shouldInterceptRequest(
        view: WebView,
        request: WebResourceRequest,
    ): WebResourceResponse? = null
}
