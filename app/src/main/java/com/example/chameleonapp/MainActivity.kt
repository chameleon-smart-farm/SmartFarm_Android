package com.example.chameleonapp

import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private lateinit var web : WebView
    private var lastTimeBackPressed: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.android_main)

        web = findViewById(R.id.android_main_view)
        val url : String = "http://localhost:8077/"

        web.webChromeClient = WebChromeClient()     //크롬으로
        web.webViewClient = WebViewClient()         //현재 창에서

        val webSettings = web.settings
        webSettings.javaScriptEnabled = true        //자바스크립트 활성화
        webSettings.domStorageEnabled = true        //쿠키같은 local 저장소 활성화
        web.loadUrl(url)
    }

    /**
     * 앱을 종료했을 때
     */
    override fun onDestroy() {
        super.onDestroy()

        // WebView 종료
        web.destroy()
        web.clearCache(true)
        web.clearHistory()

    }

    /**
     * 뒤로가기 버튼을 눌렀을 때
     */
    override fun onBackPressed() {

//        Toast.makeText(this,"뒤로가기 버튼 클릭", Toast.LENGTH_SHORT).show();

        // 뒤로 갈 수 있다면 뒤로
        if(web.canGoBack()){
            web.goBack()
        }else{

            // 연속으로 두 번 클릭시 종료
            if(System.currentTimeMillis() - lastTimeBackPressed < 3000 ){
                super.onBackPressed()
                return;
            }

            // 마지막에 누른 시간을 다시 기억
            lastTimeBackPressed = System.currentTimeMillis();
            Toast.makeText(this,"뒤로가기 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();

        }
    }
}