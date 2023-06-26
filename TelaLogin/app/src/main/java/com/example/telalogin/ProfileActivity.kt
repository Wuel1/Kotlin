package com.example.telalogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.telalogin.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProfileBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonTemperatura.setOnClickListener {
            startActivity(Intent(this, TemperaturaActivityActivity::class.java))
        }

        binding.buttonMoeda.setOnClickListener {
            startActivity(Intent(this, MoedaActivity::class.java))
        }

        binding.buttonVoltar.setOnClickListener {
            finish()
        }
    }
}