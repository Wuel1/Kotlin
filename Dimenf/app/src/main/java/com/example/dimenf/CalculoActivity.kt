package com.example.dimenf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.dimenf.databinding.ActivityCalculoBinding
import java.lang.Exception

class CalculoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalculoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCalculoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonVoltar.setOnClickListener {
            finish()
        }

        binding.buttonCalcular.setOnClickListener {
            try {
                calcularQuadro()
            }catch (e: Exception){
                Toast.makeText(this,"${e}",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun calcularQuadro() {
        var pcm = binding.inputPcm.text.toString().toInt()
        var pci = binding.inputPci.text.toString().toInt()
        var pcad = binding.inputPcad.text.toString().toInt()
        var pcsi = binding.inputPcsi.text.toString().toInt()
        var pcit = binding.inputPcit.text.toString().toInt()
        var ds = binding.inputDs.text.toString().toDouble()
        var jst = binding.inputJst.text.toString().toInt()
        var ist = binding.inputIst.text.toString().toDouble() // Corrected this line

        var km = (ds / jst) * ist
        var the = ((pcm * 4) + (pci * 6) + (pcad * 10) + (pcsi * 10) + (pcit * 18))
        var qp = (km * the).toInt()
        var resultado = "O número de enfermeiros para configuração é:\n ${qp}"

        binding.resultado.text = resultado // Corrected this line
    }
}