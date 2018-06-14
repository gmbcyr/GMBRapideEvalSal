package com.gmb.gmbrapideevalsal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.util.Locale;

public class ShowWebview extends AppCompatActivity {

    WebView mWebView;
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_webview);

        mWebView = (WebView) findViewById(R.id.wb_private_p);

        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        /*if(VIEW_TYPE_ABOUT.equalsIgnoreCase(typeView)){

            //mUrl="file:///android_asset/"+ Locale.getDefault().getLanguage()+ "/en/www/about-us.html";
            mUrl="file:///android_asset/"+ Locale.getDefault().getLanguage()+ "/www/about-us.html";
        }
        else if(VIEW_TYPE_POLICY.equalsIgnoreCase(typeView)){

            //mUrl="file:///android_asset/"+ Locale.getDefault().getLanguage()+ "/www/private-policy.html";
            //mUrl="https://drive.google.com/file/d/0BwAv6XjPNATiZTRYQzhUWHRTX1k/view?usp=drivesdk";
            mUrl="https://github.com/gmbcyr/gmb-first/tree/master/bbm_privacy.txt";
        }
        else if(VIEW_TYPE_TERMS.equalsIgnoreCase(typeView)){

            mUrl="file:///android_asset/"+ Locale.getDefault().getLanguage()+ "/www/terms-of-service.html";
        }*/

        mUrl="file:///android_asset/"+ Locale.getDefault().getLanguage()+ "/www/private-policy.html";

        mWebView.loadUrl(mUrl);
    }
}
