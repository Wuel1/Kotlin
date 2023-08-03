package com.example.frequenciafederalprofessor

import com.example.frequenciafederalprofessor.db.DBHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.frequenciafederalprofessor.databinding.ActivityListagemBinding
import java.text.SimpleDateFormat
import java.util.Date

class ListagemActivity : AppCompatActivity() {

    lateinit var binding: ActivityListagemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityListagemBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.NomeTabela.setText(obterNomeTabela())

        binding.buttonVoltar.setOnClickListener {// Voltar
            finish()
        }
        try {
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, lista())
            binding.listView.adapter = adapter
        } catch (e: Exception) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }
    }

    fun lista(): Array<String> {
        val dbHelper = DBHelper(this)
        val usernamesList = dbHelper.listarUsernames()
        return usernamesList.toTypedArray()
    }

    fun obterNomeTabela(): String {
        val formatoData = SimpleDateFormat("dd/MM/yyyy | HH:mm:ss") // Define o formato desejado da data
        val dataAtual = Date()
        val dataFormatada = formatoData.format(dataAtual)
        return "$dataFormatada"
    }
    fun obterNomeTabelaFrequencia(): String {
        val formatoData = SimpleDateFormat("yyyyMMdd_HHmmss") // Define o formato desejado da data
        val dataAtual = Date()
        val dataFormatada = formatoData.format(dataAtual)
        return "frequencia_$dataFormatada"
    }
}