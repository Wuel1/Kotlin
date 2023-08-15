package com.example.frequenciafederalprofessor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.frequenciafederalprofessor.databinding.ActivityMainBinding
import com.example.frequenciafederalprofessor.databinding.ActivityRegistrarBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistrarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrarBinding
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegistrarBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonVoltar.setOnClickListener {
            finish()
        }

        binding.registrar.setOnClickListener {
            val database = FirebaseDatabase.getInstance()
            val dbRef = database.getReference("Frequencia")
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()
            val confirmPassword = binding.confirmPassword.text.toString()

            if(!username.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()){
                if(password == confirmPassword){
                    val exportar = ExportProfessorModel(username,password)
                    //val professorRef = dbRef.child("Professor").setValue(exportar)
                    dbRef.child("Professor").setValue(exportar)
                        .addOnCompleteListener {
                            Toast.makeText(this, "Registrado com Sucesso", Toast.LENGTH_SHORT).show()
                        } .addOnFailureListener { erro ->
                            Toast.makeText(this, "Erro - ${erro}", Toast.LENGTH_SHORT).show()
                        }
                } else{
                    Toast.makeText(this, "As senhas não são iguais, por favor, digite novamente", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Alguns campos estão vázios", Toast.LENGTH_SHORT).show()
            }
        }
    }
}