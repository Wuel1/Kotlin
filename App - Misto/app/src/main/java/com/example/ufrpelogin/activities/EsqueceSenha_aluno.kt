package com.example.ufrpelogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class EsqueceSenha_aluno : AppCompatActivity() {

        private lateinit var Email: EditText
        private lateinit var ConfirmaEmail: EditText

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_senha_aluno)

            Email = findViewById(R.id.Email)
            ConfirmaEmail = findViewById(R.id.ConfirmEmail)

            val buttonConfirm: Button = findViewById(R.id.buttonConfirmaEmail)
            buttonConfirm.setOnClickListener {
                confirmaEmails()
            }
        }

        private fun confirmaEmails() {
            val email = Email.text.toString().trim()
            val confirmEmail = ConfirmaEmail.text.toString().trim()

            if (email.isEmpty() || confirmEmail.isEmpty()) {
                showToast("Digite o e-mail e confirme novamente.")
                return
            }

            if (email == confirmEmail) {
                showToast("E-mails confirmados com sucesso!")
            } else {
                showToast("Os e-mails não coincidem. Por favor, digite novamente.")
            }
        }

        private fun showToast(message: String) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }



