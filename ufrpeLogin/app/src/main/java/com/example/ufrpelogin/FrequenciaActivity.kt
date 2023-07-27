package com.example.ufrpelogin

import com.example.ufrpelogin.db.DBHelper
import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.ufrpelogin.databinding.ActivityFrequenciaAlunoBinding
import java.text.SimpleDateFormat
import java.util.Date

class FrequenciaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFrequenciaAlunoBinding
    lateinit var bluetoothAdapter: BluetoothAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding =  ActivityFrequenciaAlunoBinding.inflate(layoutInflater)
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonVoltar.setOnClickListener {
            finish()
        }

        binding.confirmButton.setOnClickListener { // Botão para confirmar o login
            confirmarHost(bluetoothAdapter)
        }

    }

    fun listaPareados(bluetoothAdapter: BluetoothAdapter): Set<BluetoothDevice> {
        val pareados = if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return emptySet()
        } else {
            bluetoothAdapter.bondedDevices
        }
        return pareados
    }

    private fun confirmarHost(bluetoothAdapter: BluetoothAdapter){
        val pareados = (listaPareados(bluetoothAdapter))
        val dbHelper = DBHelper(this)
        for(dispositivo in pareados){
            if(dbHelper.isProfessorMac(dispositivo.address.toString())){ //Verifica com MAC do Host
                Toast.makeText(this, "Pareamento com Host Confirmado", Toast.LENGTH_SHORT).show()
                dbHelper.inserirFrequencia(obterNomeTabelaFrequencia(), dispositivo.name, dispositivo.address)
                binding.status.setText("Frequência Realizada")
                binding.confirmButton.setBackgroundResource(R.drawable.baseline_bluetooth_connected_24_white)
                binding.status.setBackgroundResource(R.drawable.bg_btn_blue)
                return
            }
        }
        Toast.makeText(this, "Pareamento com Host não identificado", Toast.LENGTH_SHORT).show()
    }

    fun obterNomeTabelaFrequencia(): String {
        val formatoData = SimpleDateFormat("yyyyMMdd_HHmmss") // Define o formato desejado da data
        val dataAtual = Date()
        val dataFormatada = formatoData.format(dataAtual)
        return "frequencia_$dataFormatada"
    }
}
