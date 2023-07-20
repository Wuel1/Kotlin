package com.example.ufrpelogin

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
        for(dispositivo in pareados){
            if(dispositivo.address.toString() == "00:45:E2:6A:46:3C"){ //Verifica com MAC do Host
                Toast.makeText(this, "Pareamento com Host Confirmado", Toast.LENGTH_SHORT).show()
                binding.status.setText("Frequência Realizada")
                binding.confirmButton.setBackgroundResource(R.drawable.baseline_bluetooth_connected_24_white)
                binding.status.setBackgroundResource(R.drawable.bg_btn_blue)
                return
            }
        }
        Toast.makeText(this, "Pareamento com Host não identificado", Toast.LENGTH_SHORT).show()
    }
}
