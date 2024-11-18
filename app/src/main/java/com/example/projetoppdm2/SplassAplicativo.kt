package com.example.projetoppdm2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.projetoppdm2.databinding.ActivitySplassAplicativoBinding

class SplassAplicativo : AppCompatActivity() {
    private lateinit var binding: ActivitySplassAplicativoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySplassAplicativoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Handler(Looper.getMainLooper()).postDelayed({
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        },3000)
    }
}