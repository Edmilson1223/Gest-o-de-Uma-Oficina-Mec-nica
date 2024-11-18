package com.example.projetoppdm2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projetoppdm2.Classes.Peca
import com.example.projetoppdm2.databinding.ActivityRegistarPecasBinding

class RegistarPecas : AppCompatActivity() {
    private lateinit var binding: ActivityRegistarPecasBinding
    private lateinit var nome: String
    private lateinit var quantidade: String
    private lateinit var preco: String
    private lateinit var problema: String
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegistarPecasBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.editNome.isEnabled = true
        binding.editQuantidade.isEnabled = true
        binding.editPreco.isEnabled = true
        binding.editProblema.isEnabled = true
        Toast.makeText(applicationContext, "Agora podes adicionar os dados", Toast.LENGTH_SHORT)
            .show()

        // Adicionar um listener para o botão de confirmação
        binding.buttonConfirmar.setOnClickListener {
            // Inicializar as variáveis com os dados dos EditText
            nome = binding.editNome.text.toString()
            quantidade = binding.editQuantidade.text.toString()
            preco = binding.editPreco.text.toString()
            problema = binding.editProblema.text.toString()

            // Verificar se algum campo está vazio
            if (nome.isEmpty() || quantidade.isEmpty() || preco.isEmpty() || problema.isEmpty()) {
                // Mostrar um Toast de aviso
                Toast.makeText(applicationContext, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT)
                    .show()
            } else {
                // Criar um objeto peca com os dados
                val peca = Peca(nome, quantidade, preco, problema)
                // Criar uma intent com o peca como extra
                val resultIntent = Intent()
                resultIntent.putExtra("peca", peca)
                // Retornar o resultado e terminar a atividade
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
        binding.buttonVoltar.setOnClickListener {
            finish()
        }

    }
}
