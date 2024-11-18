package com.example.projetoppdm2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projetoppdm2.Classes.Peca
import com.example.projetoppdm2.databinding.ActivityDadosPecaBinding
@Suppress("DEPRECATION")

class DadosPecaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDadosPecaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDadosPecaBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val peca = intent.getSerializableExtra("peca") as Peca

        binding.editNome.setText(peca.nome)
        binding.editQuantidade.setText(peca.quantidade)
        binding.editPreco.setText(peca.preco)
        binding.editProblema.setText(peca.problema)
        binding.buttonVoltar.setOnClickListener {
            finish()
        }
        binding.buttonEditar.setOnClickListener {
            binding.editNome.isEnabled = true
            binding.editQuantidade.isEnabled = true
            binding.editPreco.isEnabled = true
            binding.editProblema.isEnabled = true
            Toast.makeText(applicationContext, "Agora podes editar os dados", Toast.LENGTH_SHORT)
                .show()
        }
        binding.buttonSalvar.setOnClickListener {
            val nome = binding.editNome.text.toString()
            val quantidade = binding.editQuantidade.text.toString()
            val preco =  binding.editPreco.text.toString()
            val problema = binding.editProblema.text.toString()

            Toast.makeText(applicationContext, "Dados atualizados com sucesso", Toast.LENGTH_SHORT).show()

            val peca = Peca(nome, quantidade, preco, problema)
            val resultIntent = Intent()
            resultIntent.putExtra("peca", peca)
            resultIntent.putExtra("index", intent.getIntExtra("index", -1)) // Adicione essa linha para passar o index de volta
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}