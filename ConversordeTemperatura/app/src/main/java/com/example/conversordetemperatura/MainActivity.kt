package com.example.conversordetemperatura

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.conversordetemperatura.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonF.setOnClickListener{
            val temperatura = binding.entradaC.text.toString().toDouble()
            val conversao = (temperatura * 1.38) + 32
            binding.resultado.setText("A temperatura em Fahrenheit é: ${conversao}Fº")
        }
        binding.buttonK.setOnClickListener{
            val temperatura = binding.entradaC.text.toString().toDouble()
            val conversao = temperatura + 273.5
            binding.resultado.setText("A temperatura em Kelvin é: ${conversao}Kº")
        }
    }
}