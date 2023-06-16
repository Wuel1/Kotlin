package com.example.primeiroapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.primeiroapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonOla.setOnClickListener {
            val nome = binding.olaArea.text.toString()
            //binding.textArea.text = "Olá! " + nome
            //binding.textArea.text = "Olá, ${nome}"
            binding.textArea.setText("Olá, ${nome}")
        }
    }
}