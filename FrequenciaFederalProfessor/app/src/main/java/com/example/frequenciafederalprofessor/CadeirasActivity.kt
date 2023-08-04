package com.example.frequenciafederalprofessor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.frequenciafederalprofessor.databinding.ActivityCadeirasBinding

class CadeirasActivity : AppCompatActivity() {

    lateinit var binding: ActivityCadeirasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCadeirasBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonVoltar.setOnClickListener {// Voltar
            finish()
        }
    }
}