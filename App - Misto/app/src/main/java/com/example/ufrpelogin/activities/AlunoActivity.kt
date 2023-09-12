package com.example.ufrpelogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ufrpelogin.databinding.ActivityAlunoBinding
import com.google.firebase.auth.FirebaseAuth

class AlunoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlunoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAlunoBinding.inflate(layoutInflater)
        val user = FirebaseAuth.getInstance().currentUser
        val username = user?.displayName.toString()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.nomeAluno.setText("${username}\nDocente de Computação\nUFRPE | UABJ")

        binding.buttonVoltar.setOnClickListener {
            finish()
        }
        binding.buttonCadeiras.setOnClickListener {
            startActivity(Intent(this,Cadeira_Aluno::class.java))
        }
        binding.buttonHorarios.setOnClickListener {
            startActivity(Intent(this,Horario::class.java))
        }
        binding.frequencia.setOnClickListener { // leva para o tela de frequência
            startActivity(Intent(this, FrequenciaActivity::class.java))
        }
        binding.button4.setOnClickListener {
            Toast.makeText(applicationContext,"Não Implementado", Toast.LENGTH_SHORT).show()
        }
    }
}