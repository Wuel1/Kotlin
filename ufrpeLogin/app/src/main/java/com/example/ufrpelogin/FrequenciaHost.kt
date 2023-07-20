package com.example.ufrpelogin

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.ufrpelogin.R
import com.example.ufrpelogin.TimerHelper
import com.example.ufrpelogin.databinding.ActivityFrequenciaHostBinding

class FrequenciaHost : AppCompatActivity(), TimerHelper.TimerCallback {

    lateinit var binding: ActivityFrequenciaHostBinding
    lateinit var bluetoothAdapter: BluetoothAdapter
    lateinit var timer: TimerHelper
    var isTimerRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFrequenciaHostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter

        // Conferindo se pode se conseguir acesso ao Bluetooth
        conferir(bluetoothManager, bluetoothAdapter)

        binding.buttonVoltar.setOnClickListener {
            finish()
        }

        binding.AtivaBluetooth.setOnClickListener {
            if (!bluetoothAdapter.isEnabled) {
                binding.bluetoothImagem.setImageResource(R.drawable.baseline_bluetooth_connected_24_white)
                startActivity(Intent(Settings.ACTION_BLUETOOTH_SETTINGS))
            } else {
                Toast.makeText(this, "Bluetooth já está ligado", Toast.LENGTH_SHORT).show()
            }
        }

        binding.DesativaBluetooth.setOnClickListener {
            if (bluetoothAdapter.isEnabled) {
                startActivity(Intent(Settings.ACTION_BLUETOOTH_SETTINGS))
                binding.bluetoothImagem.setImageResource(R.drawable.baseline_bluetooth_connected_24)
            } else {
                Toast.makeText(this, "Bluetooth já está desligado", Toast.LENGTH_SHORT).show()
            }
        }

        binding.PareadosBluetooth.setOnClickListener {
            if(!bluetoothAdapter.isEnabled){
                Toast.makeText(this, "Por favor, ligue o Bluetooth para começar a chamada", Toast.LENGTH_SHORT).show()
            } else if (!isTimerRunning) {
                startTimer()
                listaPareados(bluetoothAdapter)
            } else{
                Toast.makeText(this, "Frequencia já está em execução", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startTimer() {
        timer = TimerHelper(5 * 60 * 1000, 1000, this)
        timer.start()
        isTimerRunning = true
    }

    private fun conferir(bluetoothManager: BluetoothManager, bluetoothAdapter: BluetoothAdapter) {
        try {
            if (bluetoothAdapter.isEnabled) {
                binding.bluetoothImagem.setImageResource(R.drawable.baseline_bluetooth_connected_24_white)
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Erro ao identificar Bluetooth", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    private fun listaPareados(bluetoothAdapter: BluetoothAdapter) {
        binding.listaPareados.setText("\n")
        val pareados = if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        } else {
            bluetoothAdapter.bondedDevices
        }

        var qt = 0

        val pairingTimeout = 5 * 60 * 1000
        val startTime = System.currentTimeMillis()

        for (i in pareados) {
            if (System.currentTimeMillis() - startTime > pairingTimeout) {
                Toast.makeText(this, "Tempo limite de pareamento atingido.", Toast.LENGTH_SHORT).show()
                break
            }
            qt += 1
            val nomeDispositivo = i.name
            val enderecoDispositivo = i.address
            binding.listaPareados.append("${qt} - ${nomeDispositivo}\n")
        }
    }

    override fun onTick(millisUntilFinished: Long) {
        val minutes = millisUntilFinished / 60000
        val seconds = (millisUntilFinished % 60000) / 1000
        val timeLeft = String.format("%02d:%02d", minutes, seconds)
        binding.Timer.text = timeLeft
    }

    override fun onFinish() {
        binding.Timer.text = "Tempo acabou!"
        isTimerRunning = false
    }
}
