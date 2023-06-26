package com.example.telalogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.telalogin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?){
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonEntrar.setOnClickListener{
            conferir(binding.username.text.toString().trim(), binding.senha.text.toString().trim())
        }
    }

    private fun conferir(username: String, senha: String) {
        if(username.equals("wandson.emanuel") && senha.equals("eandson2002")){
            Toast.makeText(applicationContext,"Login Confirmado",Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(applicationContext,"Login inválido",Toast.LENGTH_LONG).show()
        }
        binding.username.setText("")
        binding.senha.setText("")
    }
}