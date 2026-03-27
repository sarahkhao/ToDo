package com.example.todo;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class WebActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        WebView webView = findViewById(R.id.maWebView);
        webView.setWebViewClient(new WebViewClient()); // Pour ouvrir dans l'app et non le navigateur externe
        webView.getSettings().setJavaScriptEnabled(true);

        String url = getIntent().getStringExtra("URL_WEB");
        if (url != null && !url.isEmpty()) {
            // S'assurer que l'url commence par http
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }
            webView.loadUrl(url); // Affiche le lien dans la webview
        }
    }
}