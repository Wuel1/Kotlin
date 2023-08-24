package com.example.frequenciafederalprofessor.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.frequenciafederalprofessor.databinding.ActivityMainBinding
import com.example.frequenciafederalprofessor.db.DBHelper
import com.example.frequenciafederalprofessor.models.PasswordHasher
import com.example.frequenciafederalprofessor.models.PasswordHasher.verifyPassword
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dbRef: DatabaseReference
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {

        binding =  ActivityMainBinding.inflate(layoutInflater)
        val database =  FirebaseDatabase.getInstance()
        dbRef = database.getReference("PROFESSOR") // Atribuir à propriedade da classe
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonEntrar.setOnClickListener {
            try {
                conferirFirebase()
            }catch (e: Exception){
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
        binding.buttonRegistrar.setOnClickListener {
            startActivity(Intent(this, RegistrarActivity::class.java))
        }
    }

    private fun conferirTabela() {
        val username = binding.username.text.toString()
        val password = binding.password.text.toString()


        dbRef.child(username).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val senhaHashNoBanco = snapshot.getValue(String::class.java).toString()
                    val HashDigitado = PasswordHasher.hashPasswordWithSalt(password,"16")
                    if (senhaHashNoBanco == HashDigitado) {
                        startActivity(Intent(this@MainActivity, ProfessorActivity::class.java))
                    } else {
                        Toast.makeText(applicationContext, "Senha incorreta. Tente novamente.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(applicationContext, "Usuário não encontrado. Tente novamente.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Lida com o erro de leitura no banco de dados
                Toast.makeText(applicationContext, "FIREBASE ERROR.", Toast.LENGTH_SHORT).show()

            }
        })
    }

    private fun conferirFirebase() {
        val email = binding.username.text.toString()
        val password = binding.password.text.toString()

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{ sign ->
            if(sign.isSuccessful){
                startActivity(Intent(this@MainActivity, ProfessorActivity::class.java))
                limparCampos()
            }
        }.addOnFailureListener{ error ->
            val mensagemError = when(error){
                is FirebaseNetworkException -> "Sem conexão com a internet"
                is FirebaseAuthInvalidCredentialsException -> "Senha incorreta. Tente novamente"
                else -> "Usuário não encontrado. Tente novamente"
            }
            Toast.makeText(applicationContext, "${mensagemError}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun limparCampos() {
        binding.username.text.clear()
        binding.password.text.clear()
    }


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