package com.example.projetoppdm2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projetoppdm2.Classes.Carro
import com.example.projetoppdm2.Classes.HistoricoCarro
import com.example.projetoppdm2.Classes.Peca
import com.example.projetoppdm2.databinding.ActivityDadosPecaInspecaoBinding
import java.io.Serializable

@Suppress("DEPRECATION")

class DadosPecaInspecao : AppCompatActivity() {
    private lateinit var binding: ActivityDadosPecaInspecaoBinding
    var precoTotal = 0.0
    private var carro: Carro? = null
    val PRECO_MAO_DE_OBRA = 1500.0
    private var inspecao: Peca? = null
    var historicoCarros = ArrayList<HistoricoCarro>()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDadosPecaInspecaoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        inspecao = intent.getSerializableExtra("inspecao") as Peca?

        carro = intent.getSerializableExtra("carro") as Carro?


        historicoCarros = intent.getSerializableExtra("historico") as? ArrayList<HistoricoCarro>?: ArrayList()


        binding.editPrecoMaoDeObra.setText(PRECO_MAO_DE_OBRA.toString())



        binding.editNome.setText(inspecao!!.nome)
        binding.editQuantidade.setText(inspecao!!.quantidade)
        binding.editPreco.setText(inspecao!!.preco)
        binding.editProblema.setText(inspecao!!.problema)
        binding.editQuantidade.isEnabled=true
        binding.editHorasGastos.isEnabled=true
        binding.editPreco.isEnabled=true

        binding.buttonVerTotal.setOnClickListener {
            // Obtém os valores dos campos e converte para números dentro do evento de clique
            val horasGastos = if (binding.editHorasGastos.text.isNotEmpty()) {
                binding.editHorasGastos.text.toString().toDouble()
            } else {
                0.0 // usa um valor padrão se o campo estiver vazio
            }
            val quantidade = if (binding.editQuantidade.text.isNotEmpty()) {
                binding.editQuantidade.text.toString().toInt()
            } else {
                0 // usa um valor padrão se o campo estiver vazio
            }
            val preco = if (binding.editPreco.text.isNotEmpty()) {
                binding.editPreco.text.toString().toDouble()
            } else {
                0.0 // usa um valor padrão se o campo estiver vazio
            }

            // Calcula o preço total usando os valores obtidos
            precoTotal = ((PRECO_MAO_DE_OBRA * horasGastos) + (preco * quantidade))

            // Mostra o preço total no campo editPrecoTotal, convertendo para string
            binding.editPrecoTotal.setText(precoTotal.toString())
        }


            binding.buttonConfirmarInspecao.setOnClickListener {
                // Cria um objeto da classe HistoricoCarro com os dados do carro, da peça, do problema e do preço total
                val historicoCarro = HistoricoCarro(
                    carro = carro!!.matricula, // supondo que a classe Peca tenha uma propriedade carro
                    peca = inspecao!!.nome,
                    problema = binding.editProblema.text.toString(),
                    precoTotal = precoTotal.toString()
                )

                // Adiciona o objeto da classe HistoricoCarro à lista de histórico de carros
                historicoCarros.add(historicoCarro)

                // Mostra uma mensagem de confirmação ao usuário
                Toast.makeText(this, "Inspeção realizada com sucesso", Toast.LENGTH_SHORT).show()

                // Cria uma variável para armazenar um Intent que vai iniciar a nova atividade
                val i = Intent(this, MainActivity::class.java)
                // Passa a lista de histórico de carros para o Intent como um extra
                i.putExtra("historico", historicoCarros as Serializable)
                // Inicia a nova atividade usando o Intent
                startActivity(i)
            }

        binding.buttonVoltar.setOnClickListener {
            finish()
        }
    }
}