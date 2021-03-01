package com.ctt.primeiroapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_usuario.*

class UsuarioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario)

        //Aqui coloco em .get as CHAVES que determinei na activity anterior (main activity):
        val nomeUsuario = intent.extras?.get("NOME")
        val idadeUsuario = intent.extras?.get("IDADE")

        txtDadosUsuario.text = "Oi, $nomeUsuario! Você tem $idadeUsuario anos."

        
    }
}