package com.example.user.testadmob;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    InterstitialAd interstitialAd;
    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myWebView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.loadUrl("https://vulphux.wixsite.com/apps");
        myWebView.setWebViewClient(new WebViewClient());

        //b4 publishing change app id in java file,unit id in layout, remove addTestDevice in java file

        /*------------------------Banner Ads------------------------*/
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");//AdMob app ID
        AdView adView =findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().setRequestAgent("android_studio:ad_template").addTestDevice("26...AD").build();
        //on emulator: .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
        //on real device: AdRequest.Builder.addTestDevice("26...AD") on logcat then .addTestDevice("26...AD")
        adView.loadAd(adRequest);

        /*------------------------Interstitial Ads------------------------*/
        interstitialAd =new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.loadAd(new AdRequest.Builder().addTestDevice("26...AD").build());
        //after the ad z complete it will go to 2nd activity
        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
                startActivity(intent);
                interstitialAd.loadAd(new AdRequest.Builder().addTestDevice("26...AD").build());
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(myWebView.canGoBack()) {
            myWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public void page2(View view) {
        if(interstitialAd.isLoaded()){
            interstitialAd.show();
        }else{
            Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
            startActivity(intent);
        }
    }
}