package com.example.frequenciafederalprofessor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.frequenciafederalprofessor.databinding.ActivityProfessorBinding


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
            startActivity(Intent(this, CadeirasActivity::class.java))
        }
        binding.buttonHorarios.setOnClickListener {
            Toast.makeText(applicationContext,"Não Implementado", Toast.LENGTH_SHORT).show()
        }
        binding.frequencia.setOnClickListener {
            startActivity(Intent(this, FrequenciaHost::class.java))
        }
        binding.button4.setOnClickListener {
            Toast.makeText(applicationContext,"Não Implementado", Toast.LENGTH_SHORT).show()
        }
    }
}