package com.example.ufrpelogin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ufrpelogin.databinding.ActivityAlunoHorarioBinding
import com.example.ufrpelogin.db.DBHelper

class Horario : AppCompatActivity(){
    private lateinit var binding: ActivityAlunoHorarioBinding
    private lateinit var dbHelper: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlunoHorarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbHelper = DBHelper(this)

        binding.buttonCadastrar.setOnClickListener {
            novoHorairo()
        }
    }
    private fun novoHorairo(){
        val materia = binding.editTextmateria.text.toString()
        val horario = binding.editTextHorRio.text.toString()
        val professor = binding.editTextprofessor.text.toString()

        if (materia.isNotEmpty() && horario.isNotEmpty() && professor.isNotEmpty()) {
            val novohorário = dbHelper.alunosInsert(materia, horario, professor)
            if (novohorário != -1L) {
                Toast.makeText(this, "Horário Cadastrado !", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Erro no cadastro do novo horário.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show()
        }
    }


    }

