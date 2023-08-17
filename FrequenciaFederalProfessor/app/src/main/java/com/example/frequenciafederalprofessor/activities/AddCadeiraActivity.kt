package com.example.frequenciafederalprofessor.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.frequenciafederalprofessor.databinding.ActivityAddCadeiraBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddCadeiraActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddCadeiraBinding
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddCadeiraBinding.inflate(layoutInflater)
        val database = FirebaseDatabase.getInstance()
        dbRef = database.getReference("FREQUENCIA")
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonVoltar.setOnClickListener {
            finish() // Voltar
        }

        binding.registrar.setOnClickListener {
            registrar()
        }

    }

    fun registrar(){
        //registrar cadeira no banco de dados
        val cadeira = binding.Cadeira.text.toString()
        val periodo = binding.periodo.text.toString()
        val data = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        if(!cadeira.isEmpty() && !periodo.isEmpty()){
            dbRef.child("Wandson").child(periodo).child(cadeira).child(data).setValue(true)
                .addOnCompleteListener{
                    Toast.makeText(this,"Sucesso",Toast.LENGTH_SHORT).show()
                }.addOnFailureListener { erro ->
                    Toast.makeText(this, "Erro - ${erro}", Toast.LENGTH_SHORT).show()
                }
        }else{
            Toast.makeText(this,"Alguns campos precisam ser Preenchidos", Toast.LENGTH_SHORT).show()
        }
    }
}
