package com.example.ufrpelogin

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
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
            if(!bluetoothAdapter.isEnabled){
                binding.bluetoothImagem.setImageResource(R.drawable.baseline_bluetooth_connected_24_white)
                startActivity(Intent(Settings.ACTION_BLUETOOTH_SETTINGS))
            }else{
                Toast.makeText(this, "Bluetooth já está ligado", Toast.LENGTH_SHORT).show()
            }
        }
        binding.DesativaBluetooth.setOnClickListener {
            if(bluetoothAdapter.isEnabled){
                startActivity(Intent(Settings.ACTION_BLUETOOTH_SETTINGS))
                binding.bluetoothImagem.setImageResource(R.drawable.baseline_bluetooth_connected_24)
            }else{
                Toast.makeText(this, "Bluetooth já está desligado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun conferir(bluetoothManager: BluetoothManager, bluetoothAdapter: BluetoothAdapter){
        try {
            if (bluetoothAdapter.isEnabled) { // Verifica se o Bluetooth já está ativado, caso sim, troca a imagem.
                //Toast.makeText(this, "deu bom", Toast.LENGTH_SHORT).show()
                binding.bluetoothImagem.setImageResource(R.drawable.baseline_bluetooth_connected_24_white)
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error ao identificar Bluetooth", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }

    }
}