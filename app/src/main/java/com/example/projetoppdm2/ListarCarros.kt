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
import com.example.projetoppdm2.Classes.Carro
import com.example.projetoppdm2.databinding.ActivityListarCarrosBinding
@Suppress("DEPRECATION")
class ListarCarros : AppCompatActivity() {
    private var listaCarros = ArrayList<Carro>()
    private lateinit var binding: ActivityListarCarrosBinding
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var matricula: ArrayList<String>
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityListarCarrosBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        listaCarros = intent.getSerializableExtra("carro") as? ArrayList<Carro>?: ArrayList()

        matricula = getmatricula()

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, matricula)

        binding.listViewCarro.adapter = adapter

        binding.listViewCarro.setOnItemClickListener { _, _, position, _ ->
            val i = Intent(this, DadosCarroActivity::class.java)
            i.putExtra("carro", listaCarros[position])
            i.putExtra("index", position) // Adicione essa linha para passar o index
            resultLauncher.launch(i)
        }

        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data

                    val carroAtual = data?.getSerializableExtra("carro") as Carro
                    val index = data?.getIntExtra("index", -1) // Recupere o index do Intent

                    for (car in listaCarros) {
                        if (car.matricula == carroAtual.matricula) {
                            car.marca = carroAtual.marca
                            car.modelo = carroAtual.modelo
                            car.ano = carroAtual.ano
                            break
                        }
                    }
                    // Substitua essa linha
                    // listaCarros.add(carroAtual)
                    // Por essa linha
                    if (index != null) {
                        listaCarros.set(index, carroAtual)
                    } // Substitua o carro na posição index pelo carroAtual
                    adapter.clear()
                    adapter.addAll(getmatricula())
                    adapter.notifyDataSetChanged()
                }
            }
        binding.listViewCarro.setOnItemLongClickListener { _, _, position, _ ->
            // Aqui você pode mostrar uma mensagem de confirmação ou um diálogo para o usuário
            // Por exemplo:
            AlertDialog.Builder(this)
                .setTitle("Eliminar carro")
                .setMessage("Tem certeza que quer eliminar o carro ${listaCarros[position].matricula}?")
                .setPositiveButton("Sim") { _, _ ->
                    // Aqui você pode chamar o método para eliminar o carro da lista
                    // Por exemplo:
                    eliminarCarro(position)
                }
                .setNegativeButton("Não", null)
                .show()
            // Você deve retornar true para indicar que o evento foi consumido
            true
        }
        binding.buttonVoltar.setOnClickListener {
    val resultIntent = Intent()
    resultIntent.putExtra("listaCarros", listaCarros)
    setResult(Activity.RESULT_OK, resultIntent)
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
    private fun eliminarCarro(position: Int) {
        // Aqui você pode remover o carro da lista e atualizar o adapter
        // Por exemplo:
        listaCarros.removeAt(position)
        adapter.clear()
        adapter.addAll(getmatricula())
        adapter.notifyDataSetChanged()
        // Você também pode mostrar uma mensagem de sucesso ou erro
        // Por exemplo:
        Toast.makeText(this, "Carro eliminado com sucesso", Toast.LENGTH_SHORT).show()
    }
}