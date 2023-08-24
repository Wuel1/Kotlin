package com.example.frequenciafederalprofessor.activities

import android.Manifest
import android.R
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothClass
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import com.example.frequenciafederalprofessor.db.DBHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.frequenciafederalprofessor.databinding.ActivityListagemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ListagemActivity : AppCompatActivity() {

    lateinit var binding: ActivityListagemBinding
    lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter
        binding = ActivityListagemBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        dbRef = FirebaseDatabase.getInstance().getReference("FREQUENCIA")
        binding.NomeTabela.setText(obterNomeTabela())
        atualizar()

        binding.Atualizar.setOnClickListener {
            atualizar()
        }

        binding.buttonVoltar.setOnClickListener {   // Voltar
            finish()
        }

        binding.Exportar.setOnClickListener {
            try {
                exportar()
            } catch (e: Exception) {
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun atualizar() {
        try {
            listPeriodo()
            list()
            val dbHelper = DBHelper(this)
            val adapter = ArrayAdapter(
                this,
                R.layout.simple_list_item_1,
                dbHelper.verificarAlunosPorMacs(listaMac(bluetoothAdapter))
            )
            binding.listView.adapter = adapter
        } catch (e: Exception) {
            Toast.makeText(this, "Error, ${e}", Toast.LENGTH_SHORT).show()
        }
    }


    fun listaMac(bluetoothAdapter: BluetoothAdapter): Array<String> {
        val pareados = if (ActivityCompat.checkSelfPermission(this,Manifest.permission.BLUETOOTH_CONNECT)
                           != PackageManager.PERMISSION_GRANTED){
            bluetoothAdapter.bondedDevices
        } else{
            return emptyArray()
        }
        val usernamesList = mutableListOf<String>()
        for(device in pareados){
            val deviceClass = device.bluetoothClass
            if (deviceClass != null) {
                val majorDeviceClass = deviceClass.majorDeviceClass
                if (majorDeviceClass == BluetoothClass.Device.Major.COMPUTER ||
                    majorDeviceClass == BluetoothClass.Device.Major.PHONE){
                    usernamesList.add(device.address)
                }
            }
        }
        return usernamesList.toTypedArray()
    }


    fun obterNomeTabela(): String {
        val formatoData = SimpleDateFormat("dd/MM/yyyy | HH:mm:ss") // Define o formato desejado da data
        val dataAtual = Date()
        val dataFormatada = formatoData.format(dataAtual)
        return "$dataFormatada"
    }

    private fun exportar() {
        val dbHelper = DBHelper(this)
        val alunos = dbHelper.verificarAlunosPorMacs(listaMac(bluetoothAdapter))

        if (alunos.isEmpty()) {
            Toast.makeText(this, "Frequência Vazia", Toast.LENGTH_SHORT).show()
        } else {
            val database = FirebaseDatabase.getInstance()
            val dbRef = database.getReference("FREQUENCIA")

            val user = FirebaseAuth.getInstance().currentUser
            val username = user?.displayName.toString()
            val anoLetivo = binding.optionsSpinnerPeriodo.selectedItem.toString()
            val disciplina = binding.optionsSpinner.selectedItem.toString()

            val data = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

            val professorRef = dbRef.child(username)
            val anoLetivoRef = professorRef.child(anoLetivo)
            val disciplinaRef = anoLetivoRef.child(disciplina)
            val dataRef = disciplinaRef.child(data)

            val updates = HashMap<String, Any?>() // Crie um mapa para as atualizações

            for (nome in alunos) {
                val alunoId = nome.replace(" ", "_") // Substitua espaços por underscores para usar como ID
                updates["$alunoId"] = true
            }
            dataRef.updateChildren(updates)
                .addOnCompleteListener {
                    Toast.makeText(this, "Frequência Exportada", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { erro ->
                    Toast.makeText(this, "Erro - $erro", Toast.LENGTH_SHORT).show()
                }
        }
    }

    fun listagem(callback: (Array<String>) -> Unit) {
        val disciplinasList = mutableListOf<String>()
        val user = FirebaseAuth.getInstance().currentUser
        val username = user?.displayName.toString()
        val anoLetivo = binding.optionsSpinnerPeriodo.selectedItem.toString()
        val professorRef = dbRef.child(username)
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
                Toast.makeText(this@ListagemActivity, "${databaseError.message}", Toast.LENGTH_SHORT).show()
                callback(emptyArray())
            }
        })
    }

    fun list(){
        try {
            listagem { disciplinasArray ->
                val adapter = ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, disciplinasArray)
                binding.optionsSpinner.adapter = adapter
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }
    }

    fun listagemPeriodo(callback: (Array<String>) -> Unit) {
        val disciplinasList = mutableListOf<String>()
        val user = FirebaseAuth.getInstance().currentUser
        val username = user?.displayName.toString()
        val professorId = username
        val professorRef = dbRef.child(professorId)
        professorRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (disciplinaSnapshot in dataSnapshot.children) {
                    val disciplinaNome = disciplinaSnapshot.key.toString()
                    disciplinasList.add(disciplinaNome!!)
                }
                callback(disciplinasList.toTypedArray())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@ListagemActivity, "${databaseError.message}", Toast.LENGTH_SHORT).show()
                callback(emptyArray())
            }
        })
    }

    fun listPeriodo(){
        try {
            listagemPeriodo { disciplinasArray ->
                val adapter = ArrayAdapter(this,
                    R.layout.simple_list_item_1, disciplinasArray)
                binding.optionsSpinnerPeriodo.adapter = adapter
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }
    }

}