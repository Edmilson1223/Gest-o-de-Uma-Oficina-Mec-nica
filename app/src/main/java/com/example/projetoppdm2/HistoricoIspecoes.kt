package com.example.projetoppdm2

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.projetoppdm2.Classes.HistoricoCarro
import com.example.projetoppdm2.databinding.ActivityHistoricoIspecoesBinding
@Suppress("DEPRECATION")
class HistoricoIspecoes : AppCompatActivity() {
    private lateinit var binding: ActivityHistoricoIspecoesBinding
    var historicoCarros: ArrayList<HistoricoCarro>? = null
    private lateinit var matricula: ArrayList<String>
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHistoricoIspecoesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        historicoCarros = intent.getSerializableExtra("historico") as? ArrayList<HistoricoCarro>?

        matricula = getmatricula()

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, matricula)

        binding.listViewCarroInspecionado.adapter = adapter


        binding.listViewCarroInspecionado.setOnItemClickListener { _, _, position, _ ->
            val i = Intent(this, DetalhesInspecoes::class.java)
            i.putExtra("historico", historicoCarros?.get(position))
             startActivity(i)
        }
        binding.buttonVoltar.setOnClickListener {
            finish()
        }
    }
    private fun getmatricula(): ArrayList<String> {
        val matricula = ArrayList<String>()
        for (car in historicoCarros!!) { // Use listaCarros em vez de ListarCarros
            matricula.add(car.carro)
        }
        return matricula
    }
}