package com.example.submissiongithub.Splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.submissiongithub.R
import com.example.submissiongithub.ui.MainActivity

class SplashScreenActivity : AppCompatActivity() {

    private val splashTimeOut: Long = 2000 // Durasi tampilan splash screen dalam milidetik (2 detik)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        Handler().postDelayed({
            // pindah ke activity utama
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            // Tutup splash scrwwn
            finish()
        }, splashTimeOut)
    }
}
