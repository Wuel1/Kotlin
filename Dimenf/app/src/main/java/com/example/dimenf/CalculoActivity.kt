package com.example.dimenf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dimenf.databinding.ActivityCalculoBinding

class CalculoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalculoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCalculoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonVoltar.setOnClickListener {
            finish()
        }
    }
}