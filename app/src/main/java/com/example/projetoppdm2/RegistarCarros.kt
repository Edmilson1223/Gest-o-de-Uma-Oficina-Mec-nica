package com.example.projetoppdm2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projetoppdm2.Classes.Carro
import com.example.projetoppdm2.databinding.ActivityRegistarCarrosBinding

class RegistarCarros : AppCompatActivity() {
    private lateinit var binding: ActivityRegistarCarrosBinding
    private lateinit var matricula: String
    private lateinit var marca: String
    private lateinit var modelo: String
    private lateinit var ano: String
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegistarCarrosBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.editMatricula.isEnabled = true
        binding.editMarca.isEnabled = true
        binding.editModelo.isEnabled = true
        binding.editAno.isEnabled = true
        Toast.makeText(applicationContext, "Agora podes adicionar os dados", Toast.LENGTH_SHORT)
            .show()
        // Adicionar um listener para o botão de confirmação
        binding.buttonConfirmar.setOnClickListener {
            // Inicializar as variáveis com os dados dos EditText
            matricula = binding.editMatricula.text.toString()
            marca = binding.editMarca.text.toString()
            modelo = binding.editModelo.text.toString()
            ano = binding.editAno.text.toString()

            // Verificar se algum campo está vazio
            if (matricula.isEmpty() || marca.isEmpty() || modelo.isEmpty() || ano.isEmpty()) {
                // Mostrar um Toast de aviso
                Toast.makeText(applicationContext, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT)
                    .show()
            } else {
                // Criar um objeto carro com os dados
                val carro = Carro(matricula, marca, modelo, ano)
                // Criar uma intent com o carro como extra
                val resultIntent = Intent()
                resultIntent.putExtra("carro", carro)
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
