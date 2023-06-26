package com.example.telalogin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
        if(username.equals("") && senha.equals("")){
            Toast.makeText(applicationContext,"Login Confirmado",Toast.LENGTH_LONG).show()
            startActivity(Intent(this, ProfileActivity::class.java))
        }else{
            Toast.makeText(applicationContext,"Login inválido",Toast.LENGTH_LONG).show()
        }
        binding.username.setText("")
        binding.senha.setText("")
    }
}