package com.status.ezobookstask.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.status.ezobookstask.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({

            val p = Intent(this, DashBoardActivity::class.java)
            startActivity(p)
            finish()

        }, 3000)
    }
}