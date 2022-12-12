package com.example.meditation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.meditation.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {

    private lateinit var b: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.onbEnter.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        b.onbReg.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}