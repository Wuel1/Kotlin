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
            if(!binding.entradaC.text.toString().isEmpty()){
                val temperatura = binding.entradaC.text.toString().toDouble()
                val conversao = String.format("%.2f" , (temperatura * 1.8) + 32)
                binding.resultado.setText("A temperatura em Fahrenheit é: ${conversao}Fº")
            }else{
                binding.resultado.setText("Nenhum valor inserido")
            }
        }
        binding.buttonK.setOnClickListener{
            if(!binding.entradaC.text.toString().isEmpty()){
                val temperatura = binding.entradaC.text.toString().toDouble()
                val conversao = temperatura + 273.5
                binding.resultado.setText("A temperatura em Kelvin é: ${conversao}Kº")
            }else{
                binding.resultado.setText("Nenhum valor inserido")
            }
        }
    }
}