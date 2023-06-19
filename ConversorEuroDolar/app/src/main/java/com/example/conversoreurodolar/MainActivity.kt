package com.example.conversoreurodolar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.conversoreurodolar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonConverter.setOnClickListener {
            var euro = binding.entradaEuro.text.toString().toDouble()
            val dolar = String.format("%.2f", euro * 0.70)
            //binding.resultado.text = dolar.toString()
            binding.resultado.setText("R$${dolar}")
        }
    }
}