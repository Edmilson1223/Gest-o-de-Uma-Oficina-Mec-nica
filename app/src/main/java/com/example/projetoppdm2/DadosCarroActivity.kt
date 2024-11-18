package com.example.projetoppdm2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projetoppdm2.Classes.Carro
import com.example.projetoppdm2.databinding.ActivityDadosCarroBinding
@Suppress("DEPRECATION")

class DadosCarroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDadosCarroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDadosCarroBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val carro = intent.getSerializableExtra("carro") as Carro

        binding.editMatricula.setText(carro.matricula)
        binding.editMarca.setText(carro.marca)
        binding.editModelo.setText(carro.modelo)
        binding.editAno.setText(carro.ano)

        binding.buttonVoltar.setOnClickListener {
            finish()
        }
        binding.buttonEditar.setOnClickListener {
            binding.editMatricula.isEnabled = true
            binding.editMarca.isEnabled = true
            binding.editModelo.isEnabled = true
            binding.editAno.isEnabled = true
            Toast.makeText(applicationContext, "Agora podes editar os dados", Toast.LENGTH_SHORT)
                .show()
        }

        binding.buttonSalvar.setOnClickListener {
            val matricula = binding.editMatricula.text.toString()
            val marca = binding.editMarca.text.toString()
            val modelo =  binding.editModelo.text.toString()
            val ano = binding.editAno.text.toString()

            Toast.makeText(applicationContext, "Dados atualizados com sucesso", Toast.LENGTH_SHORT).show()

            val carro = Carro(matricula, marca, modelo, ano)
            val resultIntent = Intent()
            resultIntent.putExtra("carro", carro)
            resultIntent.putExtra("index", intent.getIntExtra("index", -1)) // Adicione essa linha para passar o index de volta
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}