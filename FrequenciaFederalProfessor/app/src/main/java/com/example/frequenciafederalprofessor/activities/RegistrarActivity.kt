package com.example.frequenciafederalprofessor.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.frequenciafederalprofessor.models.ExportProfessorModel
import com.example.frequenciafederalprofessor.databinding.ActivityRegistrarBinding
import com.example.frequenciafederalprofessor.models.PasswordHasher
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistrarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrarBinding
    private lateinit var dbRef: DatabaseReference
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegistrarBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonVoltar.setOnClickListener {
            finish()
        }

        binding.registrar.setOnClickListener {
            registrarFirebase()
        }
    }

    private fun registrarTabela() {
        val database = FirebaseDatabase.getInstance()
        val dbRef = database.getReference("PROFESSOR")
        val email = binding.email.text.toString()
        val username = binding.username.text.toString()
        val password = binding.password.text.toString()
        val confirmPassword = binding.confirmPassword.text.toString()

        if (!username.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()) {
            if (password == confirmPassword) {
                val hashedSenha = PasswordHasher.hashPasswordWithSalt(password, "16")
                //val exportar = ExportProfessorModel(hashedSenha)
                dbRef.child(username).setValue(hashedSenha)
                    .addOnCompleteListener {
                        Toast.makeText(this, "Registrado com Sucesso", Toast.LENGTH_SHORT).show()
                        binding.username.text.clear()
                        binding.password.text.clear()
                        binding.confirmPassword.text.clear()
                    }.addOnFailureListener { erro ->
                        Toast.makeText(this, "Erro - ${erro}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(
                    this,
                    "As senhas não são iguais, por favor, digite novamente",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(this, "Alguns campos estão vázios", Toast.LENGTH_SHORT).show()
        }
    }

    private fun registrarFirebase() {
        val email = binding.email.text.toString()
        val username = binding.username.text.toString()
        val password = binding.password.text.toString()
        val confirmPassword = binding.confirmPassword.text.toString()

        if (!username.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()) {
            if(caracteres(password) >= 6){
                if (password == confirmPassword) {
                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                        Toast.makeText(this, "Registrado com Sucesso", Toast.LENGTH_SHORT).show()
                        limparCampos()
                    }.addOnFailureListener { error ->
                        Toast.makeText(this, "Erro - ${error}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this,"As senhas não são iguais, por favor, digite novamente", Toast.LENGTH_SHORT).show()
                }
            } else{
                Toast.makeText(this,"A senha deve ter 6 ou mais caracteres", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Alguns campos estão vázios", Toast.LENGTH_SHORT).show()
        }
    }

    private fun limparCampos() {
        binding.email.text.clear()
        binding.username.text.clear()
        binding.password.text.clear()
        binding.confirmPassword.text.clear()
    }

    private fun caracteres(text: String): Int {
        var contador = 0
        for (i in text){
            contador++
        }
        return contador
    }
}