package com.example.frequenciafederalprofessor

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.example.frequenciafederalprofessor.databinding.ActivityCadeirasBinding

class CadeirasActivity : AppCompatActivity() {

    lateinit var binding: ActivityCadeirasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCadeirasBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val webView = binding.webview
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.loadUrl("https://sigs.ufrpe.br/sigaa/verTelaLogin.do")

        binding.addTurma.setOnClickListener {
            webView.loadUrl("https://netuh.github.io/aed")
        }
        binding.removeTurma.setOnClickListener {
            webView.loadUrl("https://www.google.com.br")
        }

        binding.buttonVoltar.setOnClickListener {// Voltar
            finish()
        }
    }
}