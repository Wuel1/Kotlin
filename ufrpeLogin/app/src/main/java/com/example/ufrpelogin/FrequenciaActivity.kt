package com.example.ufrpelogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ufrpelogin.databinding.ActivityFrequenciaAlunoBinding

class FrequenciaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFrequenciaAlunoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding =  ActivityFrequenciaAlunoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonVoltar.setOnClickListener {
            finish()
        }

        binding.confirmButton.setOnClickListener {
            binding.status.setText("Frequência Realizada")
        }

    }
}