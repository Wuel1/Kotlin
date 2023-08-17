package com.example.frequenciafederalprofessor.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.frequenciafederalprofessor.databinding.ActivityMainBinding
import com.example.frequenciafederalprofessor.db.DBHelper
import com.example.frequenciafederalprofessor.models.PasswordHasher
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {

        binding =  ActivityMainBinding.inflate(layoutInflater)
        val database =  FirebaseDatabase.getInstance()
        dbRef = database.getReference("PROFESSOR") // Atribuir à propriedade da classe
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonEntrar.setOnClickListener {
            try {
                conferir()
            }catch (e: Exception){
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
        binding.buttonRegistrar.setOnClickListener {
            startActivity(Intent(this, RegistrarActivity::class.java))
        }
    }

    private fun conferir() {
        val databaseHelper = DBHelper(this)
        val username = binding.username.text.toString()
        val password = binding.password.text.toString()

        dbRef.child(username).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val senhaHashNoBanco = snapshot.child(username).getValue(String::class.java)
                    val hashedSenha = PasswordHasher.hashPassword(password)// Hash da senha fornecida
                    if(hashedSenha == senhaHashNoBanco){
                        startActivity(Intent(this@MainActivity, ProfessorActivity::class.java))
                    }else{
                        Toast.makeText(applicationContext, "Usuário não encontrado, tente novamente.", Toast.LENGTH_SHORT).show()
                    }
                    Toast.makeText(applicationContext, "${senhaHashNoBanco}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "FIREBASE FALSE.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Lida com o erro de leitura no banco de dados
                Toast.makeText(applicationContext, "FIREBASE ERROR.", Toast.LENGTH_SHORT).show()

            }
        })



//        if (username.isEmpty() || password.isEmpty()) {
//            Toast.makeText(applicationContext, "Dados incompletos", Toast.LENGTH_SHORT).show()
//        } else {
//            if (databaseHelper.checkProfessor(username, password)) {
//                Toast.makeText(applicationContext, "Professor Confirmado!", Toast.LENGTH_SHORT).show()
//                startActivity(Intent(this, ProfessorActivity::class.java))
//            } else {
//                Toast.makeText(applicationContext, "Usuário não encontrado, tente novamente.", Toast.LENGTH_SHORT).show()
//            }
//        }

    }
}