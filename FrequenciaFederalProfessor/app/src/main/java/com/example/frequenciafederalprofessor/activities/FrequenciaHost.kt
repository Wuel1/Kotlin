package com.example.frequenciafederalprofessor.activities

import com.example.frequenciafederalprofessor.db.DBHelper
import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothClass
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.frequenciafederalprofessor.R
import com.example.frequenciafederalprofessor.adapters.TimerHelper
import com.example.frequenciafederalprofessor.databinding.ActivityFrequenciaHostBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Date

class FrequenciaHost : AppCompatActivity(), TimerHelper.TimerCallback {

    lateinit var binding: ActivityFrequenciaHostBinding
    lateinit var bluetoothAdapter: BluetoothAdapter
    lateinit var timer: TimerHelper
    private lateinit var dbRef_2: DatabaseReference
    private lateinit var dbRef: DatabaseReference
    var isTimerRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database =  FirebaseDatabase.getInstance()
        dbRef = database.getReference("FREQUENCIA")
        dbRef_2 = database.getReference("ALUNO")
        binding = ActivityFrequenciaHostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter


        // Conferindo se pode se conseguir acesso ao Bluetooth
        conferir(bluetoothManager, bluetoothAdapter)

        binding.nomeDispositivo.setText(bluetoothAdapter.name.toString())

        binding.buttonVoltar.setOnClickListener {// Voltar
            finish()
        }

        binding.AtivaBluetooth.setOnClickListener {// Ativar o Bluetooth manualmente
            if (!bluetoothAdapter.isEnabled) {
                binding.bluetoothImagem.setImageResource(R.drawable.baseline_bluetooth_connected_24_white)
                startActivity(Intent(Settings.ACTION_BLUETOOTH_SETTINGS))
            } else {
                Toast.makeText(this, "Bluetooth já está ligado", Toast.LENGTH_SHORT).show()
            }
        }

        binding.DesativaBluetooth.setOnClickListener {// Desativa o Bluetooth
            if (bluetoothAdapter.isEnabled) {
                startActivity(Intent(Settings.ACTION_BLUETOOTH_SETTINGS))
                binding.bluetoothImagem.setImageResource(R.drawable.baseline_bluetooth_connected_24)
            } else {
                Toast.makeText(this, "Bluetooth já está desligado", Toast.LENGTH_SHORT).show()
            }
        }

        binding.PareadosBluetooth.setOnClickListener { // Inicia o Timer, e verifica se o bluetooth está desligado
            mostrapareados()
        }

        binding.Atualizar.setOnClickListener {
            atualizaTimer()
        }

        binding.listagem.setOnClickListener {
            startActivity(Intent(this, ListagemActivity::class.java))
        }

    }

    private fun atualizaTimer() {
        if (isTimerRunning) {
            listaPareados(bluetoothAdapter)
        } else {
            Toast.makeText(this, "Frequência não está em execução", Toast.LENGTH_SHORT).show()
        }
    }

    private fun mostrapareados() {
        if (!bluetoothAdapter.isEnabled) {
            Toast.makeText(
                this,
                "Por favor, ligue o Bluetooth para começar a chamada",
                Toast.LENGTH_SHORT
            ).show()
        } else if (!isTimerRunning) {
            startTimer()
            val discoverableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE).apply {
                putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
            }
            startActivity(discoverableIntent)
            listaPareados(bluetoothAdapter)
        } else {
            Toast.makeText(this, "Frequencia já está em execução", Toast.LENGTH_SHORT).show()
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
        binding.listaPareados.text = "\n" // Limpa o Campo
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
        runOnUiThread {
            for (device in pareados) {
                val deviceClass = device.bluetoothClass
                if (deviceClass != null) {
                    val majorDeviceClass = deviceClass.majorDeviceClass
                    if (majorDeviceClass == BluetoothClass.Device.Major.COMPUTER ||
                        majorDeviceClass == BluetoothClass.Device.Major.PHONE
                    ) {
                        compara(device.name.toString()) { confirma ->
                            if (confirma) {
                                // O código aqui será executado quando a função compara terminar
                                qt += 1
                                val nomeDispositivo = device.name
                                val enderecoDispositivo = device.address
                                val deviceInfo = "$qt Dispositivos Pareados"
                                binding.listaPareados.text = deviceInfo
                                binding.listaPareados.invalidate()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onTick(millisUntilFinished: Long) {
        val minutes = millisUntilFinished / 60000
        val seconds = (millisUntilFinished % 60000) / 1000
        val timeLeft = String.format("%02d:%02d", minutes, seconds)
        binding.PareadosBluetooth.setText("Frequência em execução")
        binding.Timer.setHintTextColor(16777215)
        binding.Timer.text = timeLeft
    }

    override fun onFinish() {
        binding.PareadosBluetooth.setText("Iniciar Frequência")
        Toast.makeText(this, "Tempo Finalizado", Toast.LENGTH_SHORT).show()
        binding.Timer.text = "Tempo acabou!"
        isTimerRunning = false
    }

    fun compara(id: String, callback: (Boolean) -> Unit) {
        dbRef_2.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var confirma = false
                for(nome in snapshot.children){
                    Toast.makeText(this@FrequenciaHost, "${nome.key.toString()}", Toast.LENGTH_SHORT).show()
                    if(id == nome.key.toString()){
                        confirma = true
                        break  // Opcional: Saia do loop assim que encontrar uma correspondência
                    }
                }
                callback(confirma) // Chame a função de retorno de chamada com o resultado
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@FrequenciaHost, "${error.message}", Toast.LENGTH_SHORT).show()
                callback(false) // Em caso de erro, retorne false
            }
        })
    }

}