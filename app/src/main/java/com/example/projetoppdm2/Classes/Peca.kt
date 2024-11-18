package com.example.projetoppdm2.Classes

import java.io.Serializable

class Peca(var nome: String, var quantidade: String, var preco: String, var problema: String) :
    Serializable {
    override fun toString(): String {
        return nome
    }
}