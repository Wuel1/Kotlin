package com.example.ufrpelogin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.example.ufrpelogin.databinding.ActivityAlunoCadastrarBinding
import com.example.ufrpelogin.databinding.ActivityAlunoCadeiraBinding


class Cadeira_Aluno : AppCompatActivity() {
    private lateinit var binding: ActivityAlunoCadeiraBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlunoCadeiraBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}

