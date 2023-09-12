package com.example.ufrpelogin

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ufrpelogin.databinding.ActivityAlunoCadastrarBinding
import com.example.ufrpelogin.db.DBHelper
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase

class Cadastrar : AppCompatActivity() {
    private lateinit var binding: ActivityAlunoCadastrarBinding
    lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var dbHelper: DBHelper
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter
        binding = ActivityAlunoCadastrarBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.buttonVoltar.setOnClickListener {
            finish()
        }

        binding.registrar.setOnClickListener {
            try {
              registrarFirebase()
            }catch (e: Exception){
              Toast.makeText(this, "${e}",Toast.LENGTH_SHORT).show()
              }
        }
    }

    private fun registrarFirebase() {
        val database = FirebaseDatabase.getInstance()
        val dbRef = database.getReference("ALUNO")
        val dbRef2 = database.getReference("FREQUENCIA")
        val email = binding.email.text.toString()
        val username = binding.username.text.toString()
        val password = binding.password.text.toString()
        val confirmPassword = binding.confirmPassword.text.toString()

        if (!username.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()) {
            if(caracteres(password) >= 6){
                if (password == confirmPassword) {
                    if(bluetoothAdapter.isEnabled){
                        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{ cadastro ->
                            if(cadastro.isSuccessful){
                                Toast.makeText(this, "Registrado com Sucesso", Toast.LENGTH_SHORT).show()
                                val bluetoothName = bluetoothAdapter.name.toString()
                                //val bluetoothMac = bluetoothAdapter.address.toString()
                                dbRef.child(bluetoothName).setValue(username)
                                //dbRef.child(bluetoothName).child(bluetoothMac)
                                val user = auth.currentUser
                                val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(username).build()
                                user?.updateProfile(profileUpdates)
                                limparCampos()
                            }
                        }.addOnFailureListener { error ->
                            val mensagemDeError = when(error){
                                is FirebaseAuthWeakPasswordException -> "Digite uma senha de no minimo 6 Caracteres"
                                is FirebaseAuthInvalidCredentialsException -> "Digite um Email válido"
                                is FirebaseAuthUserCollisionException -> "Email já cadastrado"
                                is FirebaseNetworkException -> "Sem conexão com a internet"
                                else -> "Error ao cadastrar o Usuário"
                            }
                            Toast.makeText(this, "${mensagemDeError}", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(this,"Por favor, ligue o bluetooth e tente novamente", Toast.LENGTH_SHORT).show()
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

    private fun conferir(bluetoothManager: BluetoothManager, bluetoothAdapter: BluetoothAdapter) {
        try {
            if (!bluetoothAdapter.isEnabled) {
                Toast.makeText(this,"Por favor, ligue o bluetooth",Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Erro ao identificar Bluetooth", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }
    }
