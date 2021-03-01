package com.ctt.primeiroapp

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.ctt.primeiroapp.model.Usuario

class MainActivity : AppCompatActivity() {

    private val CICLO_VIDA = "CICLO_VIDA"
    private lateinit var botaoCadastrar : Button
    private lateinit var nomeUsuario : EditText
    private lateinit var idadeUsuario : EditText
    private lateinit var fotoUsuario : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // R = res

        Log.e(CICLO_VIDA, "App em OnCreate")

        //Maneiras de referenciar um componente xml na sua Activity:
        //1. findViewById, convencional desde o Java, utilizado nessa Activity e recomendado pra evitar bugs
        //2. synthethic do Kotlin, recurso que importa automaticamente
        // import kotlinx.android.synthetic.main.activity_main.*
        //3. view binding, recurso novo disponivel no android jetpack; porém, não abordaremos.

        botaoCadastrar = findViewById<Button>(R.id.btnCadastrar)
        nomeUsuario = findViewById<EditText>(R.id.edtNomeUsuario)
        idadeUsuario = findViewById<EditText>(R.id.edtIdadeUsuario)
        fotoUsuario = findViewById<ImageView>(R.id.imgUsuario)

        var contador = 0

        //Clique de um botão
        botaoCadastrar.setOnClickListener{      //Quando o sistema "escutar" um clique no botão Cadastrar, exibe a mensagem através do Toast
            val nomeDigitado = nomeUsuario.text.toString() //É um editText, preciso converter pra String, ou Int se for número, etc.
            val idadeDigitada = idadeUsuario.text.toString()

            if (nomeDigitado.isEmpty()) {
                if(idadeDigitada.isEmpty()) {
                    idadeUsuario.error = "Você é um bebezinho?"
                } else {
                    nomeUsuario.error = "Não existe nome vazio, né?"
                }
            } else {
                val usuario = Usuario(++contador, nomeDigitado, idadeDigitada.toInt())      // contador++ soma 1 depois. ++contador = incrementa antes.
                exibirUsuario(usuario)
            }
        }
        fotoUsuario.setOnClickListener {
            abrirCamera()
        }

    }

    //Para recursos externos (tipo câmera, bluetooth, etc), não precisa colocar this e destino. Apenas a constante da ação (ACTION...)
    fun abrirCamera(){
        val CAMERA_REQUEST_CODE = 12345
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        //Modo de chamar a câmera de maneira segura: resolveActivity. Pois cada modelo de celular pode fazer de um modo.
        if(cameraIntent.resolveActivity(packageManager) != null){
            //Inicie a câmera
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)     //só vai aceitar quando a identificação vier (request code)
        } else{
            Toast.makeText(this, "Opa... Alguma coisa aconteceu! Tente novamente.", Toast.LENGTH_SHORT)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //Verificando se o dado que está vindo é do tipo câmera que eu pedi conforme determinei na linha 63
        //Verificando se não houve erros durante a tirada de foto
        if(requestCode == 12345 && resultCode == RESULT_OK){
            val imagem = data?.extras?.get("data") as Bitmap    //Se vier uma imagem do tipo data,
            fotoUsuario.setImageBitmap(imagem)
        }
    }

    fun exibirUsuario(usuario: Usuario){
        Log.e("USUARIO", usuario.toString())
        Toast.makeText(this,        // makeText é um método do Java que retorna um Toast
                "Usuário cadastrado com sucesso!",
                Toast.LENGTH_SHORT).show()      //Exibe a mensagem por um tempo curto. .show() para mostrar a mensagem!

        redirecionar(usuario)
    }

    // função para abrir nova tela:
    fun redirecionar(usuario: Usuario){
        //Intent(onde eu estou, (CLASSE) para onde eu vou)
        //CUIDADO!  A classe precisa ser ::class.java pois a Intent pede uma (C)lass
        //Se pudesse passar uma class KOTLIN, poderia chamar através ::class, porém
        //O parâmetro precisaria ser do tipo (KC)lass.

        val chaveNomeUsuario = "NOME"
        val chaveIdadeUsuario = "IDADE"

        //Usuario()
        val destinoActivity = Intent(this, UsuarioActivity::class.java)  // rementente e destinatário (do tipo classe)
        destinoActivity.putExtra(chaveNomeUsuario, usuario.nome)
        destinoActivity.putExtra(chaveIdadeUsuario, usuario.idade)

        //Inicia uma nova Activity
        startActivity(destinoActivity)

        //Encerra Activity ATUAL (MainActivity)
        finish()
    }


    // Toda aplicação tem períodos (ciclo de vida); ela nasce, ela permanece, fica em segundo plano, ela morre.

    override fun onStart() {
        super.onStart()
        Log.e(CICLO_VIDA, "App em OnStart")
    }

    override fun onResume() {
        super.onResume()
        Log.e(CICLO_VIDA, "App em OnResume")
    }

    override fun onStop() {
        super.onStop()
        Log.e(CICLO_VIDA, "App em OnStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.e(CICLO_VIDA, "App em OnRestart")
    }

    //se eu apertar o botão de voltar, aparece essa mensagem (Toast)
    override fun onBackPressed() {
        super.onBackPressed()
        Toast.makeText(this,
                "Tchau!",
                Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(CICLO_VIDA, "App em OnDestroy")
    }

}