package com.example.jjikmuk.ui

import androidx.core.content.ContextCompat.startActivity
import android.app.Activity
import android.content.Intent
import android.graphics.Typeface

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import android.util.Log
import android.widget.TextView


import com.example.jjikmuk.R
import com.kakao.sdk.common.util.Utility
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity: AppCompatActivity() {
    lateinit var textView: TextView
    val SPLASH_VIEW_TIME: Long = 3000L //2초간 스플래시 화면을 보여줌 (ms)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // 1. TextView 참조
        textView = findViewById(R.id.textViewSpannable)

        // 2. String 문자열 데이터 취득
        val textData: String = textView.text.toString()

        // 3. SpannableStringBuilder 타입으로 변환
        val builder = SpannableStringBuilder(textData)

        // 4-1. index=0 에 해당하는 문자열(0)에 볼드체적용
        val boldSpan = StyleSpan(Typeface.BOLD)
        builder.setSpan(boldSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)

        val boldSpan2 = StyleSpan(Typeface.BOLD)
        builder.setSpan(boldSpan2, 4, 5, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)


        // 5. TextView에 적용
        textView.text = builder


        /** HashKey확인 */
        val keyHash = Utility.getKeyHash(this)
        Log.d("taag",keyHash)

        Handler().postDelayed(Runnable { //delay를 위한
            startActivity(Intent(this@SplashActivity, ViewActivity::class.java))
            finish()
        }, SPLASH_VIEW_TIME)
    }
}