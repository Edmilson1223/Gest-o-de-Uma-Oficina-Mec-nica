package com.example.projetoppdm2

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
import com.example.projetoppdm2.databinding.ActivityMainBinding
import java.io.Serializable

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var resultLauncher2: ActivityResultLauncher<Intent>
    private lateinit var resultLauncher3: ActivityResultLauncher<Intent>
    private lateinit var resultLauncher4: ActivityResultLauncher<Intent>


    private var listaCarros = ArrayList<Carro>()
    private var historicoCarros = ArrayList<HistoricoCarro>()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var matricula: ArrayList<String>
    private lateinit var resultLauncher1: ActivityResultLauncher<Intent>


    private var listaPecas = ArrayList<Peca>()
    private lateinit var adapter1: ArrayAdapter<String>
    private lateinit var nome: ArrayList<String>
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>



    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        listaCarros = ArrayList()
        listaCarros.add(Carro("ST-21-PG", "Toyota", "Corolla", "2018"))
        listaCarros.add(Carro("ST-54-TY", "Honda", "Civic", "2019"))
        listaCarros.add(Carro("ST-98-YT", "Ford", "Fiesta", "2020"))

        listaPecas = ArrayList()
        listaPecas.add(Peca("Pneu", "30", "45.6", "Desgaste"))
        listaPecas.add(Peca("Bateria", "10", "120.0", "Descarga"))
        listaPecas.add(Peca("Óleo", "50", "15.5", "Troca"))

        matricula = getmatricula()

        nome = getnome()

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, matricula)
        adapter1  = ArrayAdapter(this, android.R.layout.simple_list_item_1, nome)

        binding.buttonRegistarCarros.setOnClickListener {
            val i = Intent(this,RegistarCarros::class.java)
            i.putExtra("carro", listaCarros)
            resultLauncher1.launch(i)
        }
        resultLauncher1 =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                // Verificar se o resultado é da adição e se é OK
                if ( result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    // Obter o objeto carro do extra da Intent de resultado
                    val novoCarro = data?.getSerializableExtra("carro") as? Carro
                    // Adicionar o novo carro à lista de carros
                    if (novoCarro != null) {
                        listaCarros.add(novoCarro)
                    }
                    // Atualizar o adaptador da ListView
                    adapter.clear()
                    adapter.addAll(getmatricula())
                    adapter.notifyDataSetChanged()
                }
            }
        binding.buttonRegistarPecas.setOnClickListener {
            val i = Intent(this,RegistarPecas::class.java)
            i.putExtra("peca", listaPecas)
            resultLauncher.launch(i)
        }
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                // Verificar se o resultado é da adição e se é OK
                if ( result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    // Obter o objeto peca do extra da Intent de resultado
                    val novaPeca = data?.getSerializableExtra("peca") as? Peca
                    // Adicionar o nova peca à lista de pecas
                    if (novaPeca != null) {
                        listaPecas.add(novaPeca)
                    }
                    // Atualizar o adaptador da ListView
                    adapter.clear()
                    adapter.addAll(getnome())
                    adapter.notifyDataSetChanged()
                }
            }


        binding.buttonListarCarros.setOnClickListener {
            val i = Intent(this,ListarCarros::class.java)
            i.putExtra("carro", listaCarros)
            i.putExtra("listaCarros", listaCarros) // Adicione essa linha para enviar a lista de carros
            resultLauncher3.launch(i)
        }
        resultLauncher3 =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                // Verificar se o resultado é da adição e se é OK
                if ( result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    // Obter a lista de carros do extra da Intent de resultado
                    val listaCarrosAtualizada = data?.getSerializableExtra("listaCarros") as? ArrayList<Carro>
                    // Adicionar o novo carro à lista de carros
                    // Atualizar a lista de carros com a lista recebida
                    if (listaCarrosAtualizada != null) {
                        listaCarros = listaCarrosAtualizada
                    }
                    // Atualizar o adaptador da ListView
                    adapter.clear()
                    adapter.addAll(getmatricula())
                    adapter.notifyDataSetChanged()
                }
            }


        binding.buttonListarPecas.setOnClickListener {
            val i = Intent(this,ListarPecas::class.java)
            i.putExtra("peca", listaPecas)
            i.putExtra("listaPecas", listaPecas) // Adicione essa linha para enviar a lista de carros
            resultLauncher4.launch(i)
        }
        resultLauncher4 =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                // Verificar se o resultado é da adição e se é OK
                if ( result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    // Obter a lista de pecas do extra da Intent de resultado
                    val listaPecasAtualizada = data?.getSerializableExtra("listaPecas") as? ArrayList<Peca>
                    // Adicionar o novo peca à lista de pecas
                    // Atualizar a lista de pecas com a lista recebida
                    if (listaPecasAtualizada != null) {
                        listaPecas = listaPecasAtualizada
                    }
                    // Atualizar o adaptador da ListView
                    adapter.clear()
                    adapter.addAll(getnome())
                    adapter.notifyDataSetChanged()
                }
            }

        binding.buttonSimular.setOnClickListener {
            val i = Intent(this,Simular::class.java)
            i.putExtra("carro", listaCarros)
            i.putExtra("peca", listaPecas)
            i.putExtra("historico", historicoCarros)
            startActivity(i)
        }

        val historico = intent.getSerializableExtra("historico") as ArrayList<HistoricoCarro>?
        if (historico != null) {
            historicoCarros = historico
        }


        binding.buttonHistorico.setOnClickListener {

                val i = Intent(this, HistoricoIspecoes::class.java)
                i.putExtra("historico", historicoCarros as? Serializable)
            resultLauncher2.launch(i)

        }
        resultLauncher2 =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                // Verificar se o resultado é da adição e se é OK

            }
    }
    private fun getmatricula(): ArrayList<String> {
        val matricula = ArrayList<String>()
        for (car in listaCarros) {
            matricula.add(car.matricula)
        }
        return matricula
    }
    private fun getnome(): ArrayList<String> {
        val nome = ArrayList<String>()
        for (pec in listaPecas) {
            nome.add(pec.nome)
        }
        return nome
    }
}