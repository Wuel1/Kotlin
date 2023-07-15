package com.example.ufrpelogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
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

        binding.confirmButton.setOnClickListener { // Botão para confirmar o login
            binding.status.setText("Frequência Realizada")
            binding.confirmButton.setBackgroundResource(R.drawable.baseline_bluetooth_connected_24_white)
            binding.status.setBackgroundResource(R.drawable.bg_btn_blue)
        }

    }
}
