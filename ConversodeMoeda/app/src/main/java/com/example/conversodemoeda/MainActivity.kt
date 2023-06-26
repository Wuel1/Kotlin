package com.example.conversodemoeda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.conversodemoeda.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonConverter.setOnClickListener{
            if(binding.editTexto.text.toString().isEmpty()){
                binding.textResultado.setText("Nenhum Valor Inserido")
            }else{
                val real = binding.editTexto.text.toString().toDouble()
                val dolar = real * 4.75
                val resultado = String.format("%.2f", dolar)
                binding.textResultado.setText("${resultado} $")
            }
        }
    }
}