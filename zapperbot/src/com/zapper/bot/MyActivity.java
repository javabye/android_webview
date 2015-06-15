package com.zapper.bot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyActivity extends Activity {
    private WebView webView;
    private View splashView;
    private long mStartTime;
    private static final int SHOW_TIME_MIN = 3000;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            long loadingTime = System.currentTimeMillis() - mStartTime;
            if(loadingTime < SHOW_TIME_MIN){
                mHandler.postDelayed(splashGone,SHOW_TIME_MIN);
            }else {
                mHandler.post(splashGone);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
    }

    private void initView() {
        mStartTime = System.currentTimeMillis();
        splashView = findViewById(R.id.show_logo);
        webView = (WebView) findViewById(R.id.webView);
        //设置WebView属性，能够执行Javascript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        //加载需要显示的网页
        webView.loadUrl("http://www.zapperbot.com/");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        Message msg = mHandler.obtainMessage();
        msg.obj = "";
        mHandler.sendMessage(msg);
    }

    private Runnable splashGone = new Runnable() {
        @Override
        public void run() {
            Animation anim = AnimationUtils.loadAnimation(MyActivity.this,R.anim.push_out);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    splashView.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            splashView.startAnimation(anim);
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
