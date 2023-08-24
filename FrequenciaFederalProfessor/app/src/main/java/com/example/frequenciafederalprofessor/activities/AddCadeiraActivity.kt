package com.example.frequenciafederalprofessor.activities

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.frequenciafederalprofessor.databinding.ActivityAddCadeiraBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
        list()

        binding.buttonVoltar.setOnClickListener {
            finish() // Voltar
        }

        binding.registrar.setOnClickListener {
            registrarCadeira()
        }

    }

    fun registrarCadeira() {
        val cadeira = binding.Cadeira.text.toString()
        val periodo = binding.optionsSpinner.selectedItem.toString()
        val user = FirebaseAuth.getInstance().currentUser
        val username = user?.displayName.toString()
        val data = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        if (!cadeira.isEmpty() && !periodo.isEmpty()) {
            dbRef.child(username).child(periodo).child(cadeira).child(data).setValue(true)
                .addOnCompleteListener {
                    Toast.makeText(this@AddCadeiraActivity, "Sucesso", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener { erro ->
                    Toast.makeText(this@AddCadeiraActivity, "Erro - ${erro}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this@AddCadeiraActivity, "Alguns campos precisam ser preenchidos", Toast.LENGTH_SHORT).show()
        }
    }

    fun listagem(callback: (Array<String>) -> Unit) {
        val disciplinasList = mutableListOf<String>()
        val user = FirebaseAuth.getInstance().currentUser
        val username = user?.displayName.toString()
        val professorRef = dbRef.child(username)
        professorRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (disciplinaSnapshot in dataSnapshot.children) {
                    val disciplinaNome = disciplinaSnapshot.key.toString()
                    disciplinasList.add(disciplinaNome!!)
                }
                callback(disciplinasList.toTypedArray())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Aqui você pode tratar erros que possam ocorrer ao ler os dados
                Toast.makeText(this@AddCadeiraActivity, "${databaseError.message}", Toast.LENGTH_SHORT).show()
                callback(emptyArray())
            }
        })
    }

    fun list(){
        try {
            listagem { disciplinasArray ->
                val adapter = ArrayAdapter(this,
                    R.layout.simple_list_item_1, disciplinasArray)
                binding.optionsSpinner.adapter = adapter
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }
    }

}
