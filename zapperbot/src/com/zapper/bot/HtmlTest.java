package com.zapper.bot;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.*;
import android.widget.Button;
import android.widget.Toast;


public class HtmlTest extends Activity {

    private WebView myWebView = null;
    private String text = "Android";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.html);

        myWebView = (WebView) findViewById(R.id.myWebView);

        // �õ��������ԵĶ���
        WebSettings webSettings = myWebView.getSettings();

        // ʹ��ʹ��JavaScript
        webSettings.setJavaScriptEnabled(true);

        // ֧�����ģ�����ҳ����������ʾ����
        webSettings.setDefaultTextEncodingName("GBK");

        // ������WebView�д���ҳ��������Ĭ�������
        myWebView.setWebViewClient(new WebViewClient());

        // ��������������JS�����еİ�ť����ʾ�����ǰ���ȥȴ�������Ի���
        myWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsAlert(WebView view, String url, String message,JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

        });

        // ��JavaScript����Android������
        // �Ƚ��������࣬��Ҫ���õ�Android����д���������public����
        // ���������WebView�����е�JavaScript����
        // ��һ��������һ���������룬��JS�������������������������󣬿��Ե�����������һЩ����
        myWebView.addJavascriptInterface(new WebAppInterface(this),"myInterfaceName");

        // ����ҳ�棺����html��Դ�ļ�
        myWebView.loadUrl("file:///android_asset/demo.html");
    }

    /**
     * �Զ����Android�����JavaScript����֮���������
     */
    public class WebAppInterface {
        Context mContext;

        public WebAppInterface(Context c) {
            mContext = c;
        }

        // ���target ���ڵ���API 17������Ҫ��������ע��
        // @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_LONG).show();
        }

        public void changeView() {
            myWebView.loadUrl("http://www.zapperbot.com");
        }
    }

    /**
     * ������һ��Android��ť���º����JS�еĴ���
     * @param view
     */
    public void alert(View view){
        myWebView.loadUrl("javascript:myFunction('"+text+"')");
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}