package com.example.ufrpelogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ufrpelogin.databinding.ActivityProfessorBinding

class ProfessorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfessorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProfessorBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonVoltar.setOnClickListener {
            finish()
        }
        binding.buttonCadeiras.setOnClickListener {
            Toast.makeText(applicationContext,"Não Implementado", Toast.LENGTH_SHORT).show()
        }
        binding.buttonHorarios.setOnClickListener {
            Toast.makeText(applicationContext,"Não Implementado", Toast.LENGTH_SHORT).show()
        }
        binding.button3.setOnClickListener {
            Toast.makeText(applicationContext,"Não Implementado", Toast.LENGTH_SHORT).show()
        }
        binding.button4.setOnClickListener {
            Toast.makeText(applicationContext,"Não Implementado", Toast.LENGTH_SHORT).show()
        }
    }
}