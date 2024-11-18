package com.example.projetoppdm2
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projetoppdm2.Classes.Carro
import com.example.projetoppdm2.Classes.HistoricoCarro
import com.example.projetoppdm2.Classes.Peca
import com.example.projetoppdm2.databinding.ActivityCarroSemularBinding
import java.io.Serializable
@Suppress("DEPRECATION")
class CarroSemular : AppCompatActivity() {
    private var listaPecas = ArrayList<Peca>()
    private var historicoCarros = ArrayList<HistoricoCarro>()
    private lateinit var binding: ActivityCarroSemularBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCarroSemularBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        listaPecas = intent.getSerializableExtra("peca") as? ArrayList<Peca>?: ArrayList()
        historicoCarros = intent.getSerializableExtra("historico") as? ArrayList<HistoricoCarro>?: ArrayList()

        val carro = intent.getSerializableExtra("carro") as Carro

        binding.editMatricula.setText(carro.matricula)
        binding.editMarca.setText(carro.marca)
        binding.editModelo.setText(carro.modelo)
        binding.editAno.setText(carro.ano)
        binding.editProblema.isEnabled=true

        binding.buttonInspecionar.setOnClickListener {
            val problema = binding.editProblema.text.toString()
            if (!problema.isNullOrEmpty()) {
                val listaPecasSolucao = mutableListOf<Peca>() // cria uma lista vazia para armazenar as peças que resolvem o problema
                if (!listaPecas.isNullOrEmpty()) {
                    for (peca in listaPecas) {
                        if (problema.contains(peca.problema)) {
                            // faz alguma ação se os problemas forem iguais
                            listaPecasSolucao.add(peca) // adiciona a peça à lista de solução
                            Toast.makeText(
                                applicationContext,
                                "Peca encontrada",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                    if (listaPecasSolucao.isEmpty()) {
                        // mostra a mensagem se não houver peças que resolvam o problema
                        Toast.makeText(
                            applicationContext,
                            "Não condiz com os problemas relativamente as pecas",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        // Cria um Intent para iniciar a ListaPecasActivity
                        val i = Intent(this, ListaPecasSelecao::class.java)

                        // Passa a lista de peças para o Intent como um extra
                        i.putExtra("listaPecasSolucao", listaPecasSolucao as Serializable)
                        i.putExtra("carro", carro)
                        i.putExtra("historico", historicoCarros)

                        startActivity(i)// Inicia a ListaPecasActivity
                    }
                }else{
                    Toast.makeText(applicationContext, "Não há peças disponíveis", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(applicationContext, "Preecha o campo do problema", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        binding.buttonVoltar.setOnClickListener {
            finish()
        }
    }
}
