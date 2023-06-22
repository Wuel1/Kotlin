package com.example.doiselementos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.doiselementos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.Button.setOnClickListener{
            if(binding.text1Area.text.toString().isEmpty() or
               binding.text2Area.text.toString().isEmpty() or
               binding.textDataArea.text.toString().isEmpty()){
                binding.resultado.setText("Alguns do campo de entrada está vázio")
                Toast.makeText(applicationContext, "Campo Vázio", Toast.LENGTH_SHORT).show()
            }else{
                val nome = binding.text1Area.text.toString().trim()
                val sobrenome =  binding.text2Area.text.toString().trim()
                val data = binding.textDataArea.text.toString().trim()

                val resultado: String = "${nome} ${sobrenome} \n" +
                                        "${data}"

                binding.resultado.setText(resultado)
                Toast.makeText(applicationContext, "Sucesso", Toast.LENGTH_SHORT).show()
            }
        }
    }
}