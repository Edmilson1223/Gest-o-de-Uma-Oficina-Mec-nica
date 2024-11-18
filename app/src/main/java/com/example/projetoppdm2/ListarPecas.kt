package com.example.projetoppdm2

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.projetoppdm2.Classes.Peca
import com.example.projetoppdm2.databinding.ActivityListarPecasBinding
@Suppress("DEPRECATION")

class ListarPecas : AppCompatActivity() {
    private lateinit var binding: ActivityListarPecasBinding
    private var listaPecas = ArrayList<Peca>()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var nome: ArrayList<String>
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityListarPecasBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        listaPecas= intent.getSerializableExtra("peca") as? ArrayList<Peca>?: ArrayList()

        nome = getnome()

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, nome)

        binding.listViewPeca.adapter = adapter

        binding.listViewPeca.setOnItemClickListener { _, _, position, _ ->
            val i = Intent(this, DadosPecaActivity::class.java)
            i.putExtra("peca", listaPecas[position])

            i.putExtra("index", position) // Adicione essa linha para passar o index
            resultLauncher.launch(i)
        }

        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data

                    val pecaAtual = data?.getSerializableExtra("peca") as Peca
                    val index = data?.getIntExtra("index", -1) // Recupere o index do Intent

                    for (pec in listaPecas) {
                        if (pec.nome == pecaAtual.nome) {
                            pec.quantidade = pecaAtual.quantidade
                            pec.preco = pecaAtual.preco
                            pec.problema = pecaAtual.problema
                            break
                        }
                    }

                    // Por essa linha
                    if (index != null) {
                        listaPecas.set(index, pecaAtual)
                    } // Substitua o carro na posição index pelo carroAtual
                    adapter.clear()
                    adapter.addAll(getnome())
                    adapter.notifyDataSetChanged()
                }
            }
        binding.listViewPeca.setOnItemLongClickListener { _, _, position, _ ->
            // Aqui você pode mostrar uma mensagem de confirmação ou um diálogo para o usuário
            // Por exemplo:
            AlertDialog.Builder(this)
                .setTitle("Eliminar Peca")
                .setMessage("Tem certeza que quer eliminar o carro ${listaPecas[position].nome}?")
                .setPositiveButton("Sim") { _, _ ->
                    // Aqui você pode chamar o método para eliminar o carro da lista
                    // Por exemplo:
                    eliminarPeca(position)
                }
                .setNegativeButton("Não", null)
                .show()
            // Você deve retornar true para indicar que o evento foi consumido
            true
        }

        binding.buttonVoltar.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("listaPecas", listaPecas)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
    // Dentro do método getmatricula
    private fun getnome(): ArrayList<String> {
        val nome = ArrayList<String>()
        for (car in listaPecas) { // Use listaCarros em vez de ListarCarros
            nome.add(car.nome)
        }
        return nome
    }
    private fun eliminarPeca(position: Int) {
        listaPecas.removeAt(position)
        adapter.clear()
        adapter.addAll(getnome())
        adapter.notifyDataSetChanged()
        // Você também pode mostrar uma mensagem de sucesso ou erro
        // Por exemplo:
        Toast.makeText(this, "Peca eliminado com sucesso", Toast.LENGTH_SHORT).show()
    }
}


