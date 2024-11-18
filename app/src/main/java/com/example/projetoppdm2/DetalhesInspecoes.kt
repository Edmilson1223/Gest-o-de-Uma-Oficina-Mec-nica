package com.example.projetoppdm2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projetoppdm2.Classes.HistoricoCarro
import com.example.projetoppdm2.databinding.ActivityDetalhesInspecoesBinding
@Suppress("DEPRECATION")
class DetalhesInspecoes : AppCompatActivity() {
    private lateinit var binding: ActivityDetalhesInspecoesBinding
    var historicoCarro: HistoricoCarro? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetalhesInspecoesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (intent.hasExtra("historico")) {
            historicoCarro = intent.getSerializableExtra("historico") as? HistoricoCarro
        } else {
            // Se o Intent não tiver o extra, você deve mostrar uma mensagem de erro ou terminar a atividade
            Toast.makeText(this, "Erro: não foi possível obter o histórico de carro", Toast.LENGTH_SHORT).show()
            finish()
        }
        binding.editMatricula.setText(historicoCarro!!.carro)
        binding.editPeca.setText(historicoCarro!!.peca)
        binding.editProblema.setText(historicoCarro!!.problema)
        binding.editPrecoTotal.setText(historicoCarro!!.precoTotal)

        binding.buttonVoltar.setOnClickListener {
            finish()
        }

    }
}