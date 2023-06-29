package com.example.ufrpelogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ufrpelogin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding =  ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonEntrar.setOnClickListener {
            if(conferir(binding.buttonAluno))
        }

    }
}