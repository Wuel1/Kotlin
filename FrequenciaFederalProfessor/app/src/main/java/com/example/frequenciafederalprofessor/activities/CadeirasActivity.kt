package com.example.frequenciafederalprofessor.activities

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.frequenciafederalprofessor.databinding.ActivityCadeirasBinding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class CadeirasActivity : AppCompatActivity() {

    lateinit var binding: ActivityCadeirasBinding
    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCadeirasBinding.inflate(layoutInflater)
        val database =  FirebaseDatabase.getInstance()
        dbRef = database.getReference("FREQUENCIA")
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        list()

        binding.addTurma.setOnClickListener {
            startActivity(Intent(this,AddCadeiraActivity::class.java))
        }

        binding.buttonVoltar.setOnClickListener {
            finish() // Voltar
        }
    }

    fun listagem(callback: (Array<String>) -> Unit) {
        val disciplinasList = mutableListOf<String>()
        Toast.makeText(this@CadeirasActivity, "entrou", Toast.LENGTH_SHORT).show()
        val professorId = "Wandson"
        val anoLetivo = "2022-2"

        val professorRef = dbRef.child(professorId)
        val anoLetivoRef = professorRef.child(anoLetivo)
        anoLetivoRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (disciplinaSnapshot in dataSnapshot.children) {
                    val disciplinaNome = disciplinaSnapshot.key.toString()
                    disciplinasList.add(disciplinaNome!!)
                }
                callback(disciplinasList.toTypedArray())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Aqui você pode tratar erros que possam ocorrer ao ler os dados
                Toast.makeText(this@CadeirasActivity, "${databaseError.message}", Toast.LENGTH_SHORT).show()
                callback(emptyArray())
            }
        })
    }

    fun list(){
        try {
            listagem { disciplinasArray ->
                val adapter = ArrayAdapter(this,R.layout.simple_list_item_1, disciplinasArray)
                binding.listView.adapter = adapter
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }
    }

}