package com.example.ufrpelogin

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ufrpelogin.databinding.ActivityFrequenciaHostBinding

class FrequenciaHost : AppCompatActivity() {

    lateinit var binding: ActivityFrequenciaHostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityFrequenciaHostBinding.inflate(layoutInflater)
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Inicializando a Variável do Bluetooth
        val bluetoothAdapter = bluetoothManager.adapter

        // Conferindo se pode se conseguir acesso ao Bluetooth
        conferir(bluetoothManager, bluetoothAdapter)

        binding.buttonVoltar.setOnClickListener {
            finish()
        }

        binding.AtivaBluetooth.setOnClickListener {
            if(bluetoothAdapter==null){
                Toast.makeText(applicationContext, "Bluetooth indisponível", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(applicationContext, "Bluetooth Disponível", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun conferir(bluetoothManager: BluetoothManager, bluetoothAdapter: BluetoothAdapter){
        if(bluetoothAdapter==null){
            Toast.makeText(applicationContext, "Bluetooth indisponível", Toast.LENGTH_SHORT).show()
            finish()
        }else{
            Toast.makeText(applicationContext, "Bluetooth Disponível", Toast.LENGTH_SHORT).show()
        }
    }
}