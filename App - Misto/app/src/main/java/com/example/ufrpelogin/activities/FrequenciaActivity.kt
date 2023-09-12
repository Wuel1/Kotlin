package com.example.ufrpelogin

import com.example.ufrpelogin.db.DBHelper
import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.ufrpelogin.databinding.ActivityFrequenciaAlunoBinding
import java.text.SimpleDateFormat
import java.util.Date

class FrequenciaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFrequenciaAlunoBinding
    private lateinit var bluetoothAdapter: BluetoothAdapter

    private val REQUEST_BLUETOOTH_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFrequenciaAlunoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter

        binding.buttonVoltar.setOnClickListener {
            finish()
        }

        binding.buttonToggleBluetooth.setOnClickListener {
            try {
                toggleBluetooth()

            } catch (e:Exception){
                Toast.makeText(this, "Permita o acesso ao blueettoth",Toast.LENGTH_SHORT).show()
            }

        }

        binding.confirmButton.setOnClickListener {
            confirmarHost()
        }
    }

    private fun toggleBluetooth() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.BLUETOOTH), REQUEST_BLUETOOTH_PERMISSION)
        } else {
            if (!bluetoothAdapter.isEnabled) {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, REQUEST_BLUETOOTH_PERMISSION)
            } else {
                bluetoothAdapter.disable()
                binding.status.text = "Bluetooth desligado"
                binding.status.setBackgroundResource(R.drawable.bg_btn_red)
                binding.confirmButton.setBackgroundResource(R.drawable.baseline_bluetooth_connected_24)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_BLUETOOTH_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                toggleBluetooth()
            }
        }
    }

    private fun confirmarHost() {
        val pareados = listaPareados()
        val dbHelper = DBHelper(this)
        for (dispositivo in pareados) {
            if (dbHelper.isProfessorMac(dispositivo.address.toString())) {
                Toast.makeText(this, "Pareamento com Host Confirmado", Toast.LENGTH_SHORT).show()
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.BLUETOOTH_CONNECT
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                dbHelper.inserirFrequencia(obterNomeTabelaFrequencia(), dispositivo.name, dispositivo.address)
                binding.status.text = "Frequência Realizada"
                binding.confirmButton.setBackgroundResource(R.drawable.baseline_bluetooth_connected_24_white)
                binding.status.setBackgroundResource(R.drawable.bg_btn_blue)
                return
            }
        }
        Toast.makeText(this, "Pareamento com Host não identificado", Toast.LENGTH_SHORT).show()
    }

    private fun listaPareados(): Set<BluetoothDevice> {
        return if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED) {
            bluetoothAdapter.bondedDevices
        } else {
            emptySet()
        }
    }

    private fun obterNomeTabelaFrequencia(): String {
        val formatoData = SimpleDateFormat("yyyyMMdd_HHmmss")
        val dataAtual = Date()
        return "frequencia_${formatoData.format(dataAtual)}"
    }
}
