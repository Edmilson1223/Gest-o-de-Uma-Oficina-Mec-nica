package com.example.projetoppdm2
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.projetoppdm2.Classes.Carro
import com.example.projetoppdm2.Classes.HistoricoCarro
import com.example.projetoppdm2.Classes.Peca
import com.example.projetoppdm2.databinding.ActivityListaPecasSelecaoBinding
import java.io.Serializable

@Suppress("DEPRECATION")

class ListaPecasSelecao : AppCompatActivity() {
    private lateinit var binding: ActivityListaPecasSelecaoBinding
    private var listaPecasSolucao: List<Peca>? = null
    private var historicoCarros: List<HistoricoCarro>? = null
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityListaPecasSelecaoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        listaPecasSolucao = intent.getSerializableExtra("listaPecasSolucao") as List<Peca>?
        historicoCarros = intent.getSerializableExtra("historico") as? ArrayList<HistoricoCarro>?: ArrayList()
        val carro = intent.getSerializableExtra("carro") as Carro



        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, getNomes())
        binding.listViewPecaSelecao.adapter = adapter


        binding.listViewPecaSelecao.setOnItemClickListener { _, _, position, _ ->
            val i = Intent(this, DadosPecaInspecao::class.java)
            i.putExtra("inspecao", listaPecasSolucao?.get(position))
            i.putExtra("historico", historicoCarros as Serializable)
            // Passa o objeto Carro para o Intent como um extra
            i.putExtra("carro", carro) // supondo que você tenha uma variável carro que armazena o objeto Carro

            resultLauncher.launch(i)
        }

        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val data: Intent? = result.data

                    val PecaAtual = data?.getSerializableExtra("peca") as Peca

                    for (pec in listaPecasSolucao!!) {
                        if (pec.nome == PecaAtual.nome) {
                            pec.quantidade = PecaAtual.quantidade
                            pec.preco = PecaAtual.preco
                            pec.problema = PecaAtual.problema
                            break
                        }
                    }
                    adapter.clear()
                    adapter.addAll(getNomes())
                    adapter.notifyDataSetChanged()
                }
            }

        binding.buttonVoltar.setOnClickListener {
            finish()
        }
    }
    // Cria um método para obter os nomes das peças
    private fun getNomes(): ArrayList<String> {
        val nomes = ArrayList<String>()
        for (peca in listaPecasSolucao!!) {
            nomes.add(peca.nome)
        }
        return nomes
    }

}