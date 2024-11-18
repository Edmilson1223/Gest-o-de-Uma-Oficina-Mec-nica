package com.example.projetoppdm2

import android.R
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.projetoppdm2.Classes.Carro
import com.example.projetoppdm2.Classes.HistoricoCarro
import com.example.projetoppdm2.Classes.Peca
import com.example.projetoppdm2.databinding.ActivitySimularBinding
import java.io.Serializable

@Suppress("DEPRECATION")

class Simular : AppCompatActivity() {
    private lateinit var binding: ActivitySimularBinding
    private lateinit var adapter: ArrayAdapter<String>
    private var listaCarros = ArrayList<Carro>()
    private var listaPecas = ArrayList<Peca>()
    private var historicoCarros = ArrayList<HistoricoCarro>()
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var matricula: ArrayList<String>



    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySimularBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        listaCarros = intent.getSerializableExtra("carro") as? ArrayList<Carro>?: ArrayList()

        listaPecas = intent.getSerializableExtra("peca") as? ArrayList<Peca>?: ArrayList()

        historicoCarros = intent.getSerializableExtra("historico") as? ArrayList<HistoricoCarro>?: ArrayList()



        matricula = getmatricula()
        adapter = ArrayAdapter(this, R.layout.simple_list_item_1, matricula)

        binding.listViewCarro.adapter = adapter


        binding.listViewCarro.setOnItemClickListener { _, _, position, _ ->
            val i = Intent(this, CarroSemular::class.java)

            i.putExtra("carro", listaCarros[position])

            i.putExtra("peca", listaPecas as Serializable)

            i.putExtra("historico", historicoCarros as Serializable)

            resultLauncher.launch(i)
        }

        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data

                    val carroAtual = data?.getSerializableExtra("carro") as Carro


                    for (car in listaCarros) {
                        if (car.matricula == carroAtual.matricula) {
                            car.marca = carroAtual.marca
                            car.modelo = carroAtual.modelo
                            car.ano = carroAtual.ano
                            break
                        }
                    }
                    listaCarros.add(carroAtual)

                    adapter.clear()
                    adapter.addAll(getmatricula())
                    adapter.notifyDataSetChanged()
                }
            }


        binding.buttonSimular.setOnClickListener {
            finish()
        }
    }
    // Dentro do método getmatricula
    private fun getmatricula(): ArrayList<String> {
        val matricula = ArrayList<String>()
        for (car in listaCarros) { // Use listaCarros em vez de ListarCarros
            matricula.add(car.matricula)
        }
        return matricula
    }
    }
