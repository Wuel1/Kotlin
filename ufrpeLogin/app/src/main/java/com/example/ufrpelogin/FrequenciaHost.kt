package com.example.ufrpelogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ufrpelogin.databinding.ActivityFrequenciaHostBinding

class FrequenciaHost : AppCompatActivity() {

    lateinit var binding: ActivityFrequenciaHostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityFrequenciaHostBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonVoltar.setOnClickListener {
            finish()
        }

        

    }
}