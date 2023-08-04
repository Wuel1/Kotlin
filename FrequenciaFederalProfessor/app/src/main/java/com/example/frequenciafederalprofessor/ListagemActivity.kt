package com.example.frequenciafederalprofessor

import android.Manifest
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
import com.example.frequenciafederalprofessor.databinding.ActivityFrequenciaHostBinding
import com.example.frequenciafederalprofessor.databinding.ActivityListagemBinding
import java.text.SimpleDateFormat
import java.util.Date

class ListagemActivity : AppCompatActivity() {

    lateinit var binding: ActivityListagemBinding
    lateinit var bluetoothAdapter: BluetoothAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter
        binding = ActivityListagemBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.NomeTabela.setText(obterNomeTabela())

        binding.buttonVoltar.setOnClickListener {// Voltar
            finish()
        }
        try {
            val dbHelper = DBHelper(this)
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,dbHelper.verificarAlunosPorMacs(lista(bluetoothAdapter)) )
            binding.listView.adapter = adapter
        } catch (e: Exception) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }
    }



    fun lista(bluetoothAdapter: BluetoothAdapter): Array<String> {
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

    //fun lista2(): Array<String> {
    //    val dbHelper = DBHelper(this)
    //    val usernamesList = dbHelper.listarUsernames()
    //    return usernamesList.toTypedArray()
    //}

    fun obterNomeTabela(): String {
        val formatoData = SimpleDateFormat("dd/MM/yyyy | HH:mm:ss") // Define o formato desejado da data
        val dataAtual = Date()
        val dataFormatada = formatoData.format(dataAtual)
        return "$dataFormatada"
    }
    fun obterNomeTabelaFrequencia(): String {
        val formatoData = SimpleDateFormat("yyyyMMdd_HHmmss") // Define o formato desejado da data
        val dataAtual = Date()
        val dataFormatada = formatoData.format(dataAtual)
        return "frequencia_$dataFormatada"
    }
}