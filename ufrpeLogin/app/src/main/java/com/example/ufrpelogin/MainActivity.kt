package com.example.ufrpelogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ufrpelogin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding =  ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonEntrar.setOnClickListener {
            conferir()
        }
    }
    private fun conferir(){
        if(!binding.buttonAluno.isChecked && !binding.buttonProfessor.isChecked){ // Nenhuma das caixas estão marcadas
            Toast.makeText(applicationContext, "Por favor, selecione o tipo do usuário", Toast.LENGTH_SHORT).show()
        }else if(binding.buttonAluno.isChecked){ //Caixa de Aluno marcada
            Toast.makeText(applicationContext, "Aluno Confirmado!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, AlunoActivity::class.java))
        }else if(binding.buttonProfessor.isChecked){ //Caixa de Professor marcada
            Toast.makeText(applicationContext, "Professor Confirmado!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ProfessorActivity::class.java))
        }
    }
}