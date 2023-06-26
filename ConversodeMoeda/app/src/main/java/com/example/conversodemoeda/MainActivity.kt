package com.example.conversodemoeda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.conversodemoeda.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonDolar.setOnClickListener{
            escrever(0.21)
        }
        binding.buttonEuro.setOnClickListener{
            escrever(0.19)
        }
        binding.buttonPeso.setOnClickListener{
            escrever(52.92)
        }
}

    private fun escrever(x: Double) {
        if(binding.editTexto.text.toString().isEmpty()){
            binding.textResultado.setText("Nenhum Valor Inserido")
            Toast.makeText(applicationContext,"Erro", Toast.LENGTH_SHORT).show()
        }else{
            val real = binding.editTexto.text.toString().toDouble()
            val moeda = real * x
            val resultado = String.format("%.2f", moeda)
            binding.textResultado.setText("${resultado} $")
            Toast.makeText(applicationContext,"Sucesso!", Toast.LENGTH_SHORT).show()
        }
    }
}
