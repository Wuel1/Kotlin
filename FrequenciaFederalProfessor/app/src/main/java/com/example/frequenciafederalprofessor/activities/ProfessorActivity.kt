package com.example.frequenciafederalprofessor.activities

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.frequenciafederalprofessor.databinding.ActivityProfessorBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ProfessorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfessorBinding
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProfessorBinding.inflate(layoutInflater)
        val database =  FirebaseDatabase.getInstance()
        dbRef = database.getReference("FREQUENCIA")
        val user = FirebaseAuth.getInstance().currentUser
        val username = user?.displayName.toString()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        conectaPeriodo()

        binding.nomeProfessor.setText("${username}\nDocente de Computação\nUFRPE | UABJ")

        binding.buttonVoltar.setOnClickListener {
            finish()
        }
        binding.buttonCadeiras.setOnClickListener {
            startActivity(Intent(this, CadeirasActivity::class.java))
        }
        binding.buttonHorarios.setOnClickListener {
            Toast.makeText(applicationContext,"Não Implementado", Toast.LENGTH_SHORT).show()
        }
        binding.frequencia.setOnClickListener {
            startActivity(Intent(this, FrequenciaHost::class.java))
        }
        binding.button4.setOnClickListener {
            Toast.makeText(applicationContext,"Não Implementado", Toast.LENGTH_SHORT).show()
        }
    }

//    private fun conectaPeriodo() {
//        try {
//            list()
//            binding.periodoLetivo.onItemSelectedListener =
//                object : AdapterView.OnItemSelectedListener {
//                    override fun onItemSelected(
//                        parent: AdapterView<*>?,
//                        view: View?,
//                        position: Int,
//                        id: Long
//                    ) {
//                        val selectedItem = binding.periodoLetivo.selectedItem.toString()
//                        Toast.makeText(
//                            applicationContext,
//                            "Selecionado: $selectedItem",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//
//                    override fun onNothingSelected(parent: AdapterView<*>?) {
//                        Toast.makeText(
//                            applicationContext,
//                            "Error, nenhum período identificado",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//        } catch (e: Exception) {
//            Toast.makeText(this, "${e}", Toast.LENGTH_SHORT).show()
//        }
//    }

//    fun listagem(callback: (Array<String>) -> Unit) {
//        val user = FirebaseAuth.getInstance().currentUser
//        val username = user?.displayName.toString()
//        val periodosList = mutableListOf<String>()
//
//        val professorRef = dbRef.child(username)
//        professorRef.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for (disciplinaSnapshot in dataSnapshot.children) {
//                    val disciplinaNome = disciplinaSnapshot.key.toString()
//                    periodosList.add(disciplinaNome!!)
//                }
//                callback(periodosList.toTypedArray())
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                Toast.makeText(this@ProfessorActivity, "${databaseError.message}", Toast.LENGTH_SHORT).show()
//                callback(emptyArray())
//            }
//        })
//    }
//
//    fun list(){
//        try {
//            listagem { disciplinasArray ->
//                val adapter = ArrayAdapter(this,
//                    R.layout.simple_list_item_1, disciplinasArray)
//                binding.periodoLetivo.adapter = adapter
//            }
//        } catch (e: Exception) {
//            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
//        }
//    }
}