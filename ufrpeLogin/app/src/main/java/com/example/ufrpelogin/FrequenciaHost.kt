package com.example.ufrpelogin

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
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
        binding.PareadosBluetooth.setOnClickListener {
            listaPareados(bluetoothAdapter)
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
    private fun listaPareados(bluetoothAdapter: BluetoothAdapter) {
        binding.listaPareados.setText("")
        val pareados = if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        } else {
            bluetoothAdapter.bondedDevices
        }

        var qt = 0 // Inicia a variável para contagem

        val pairingTimeout = 5 * 60 * 1000 // Tempo limite de pareamento em milissegundos
        val startTime = System.currentTimeMillis() // Tempo de início do pareamento

        for (i in pareados) { // Percorre todos os pareados
            if (System.currentTimeMillis() - startTime > pairingTimeout) {
                Toast.makeText(this, "Tempo limite de pareamento atingido.", Toast.LENGTH_SHORT).show()
                break
            }
            qt += 1 // Contagem de pareados
            val nomeDispositivo = i.name // Pega o nome do dispositivo
            val enderecoDispositivo = i.address // Pega o endereço do dispositivo
            binding.listaPareados.append("${qt} - ${nomeDispositivo}\n") // Mostra na tela a lista.
        }
    }

}