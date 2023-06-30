package com.example.ufrpelogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ufrpelogin.databinding.ActivityAlunoBinding

class AlunoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlunoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAlunoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonVoltar.setOnClickListener {
            finish()
        }
    }
}