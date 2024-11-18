package com.example.projetoppdm2.Classes

import java.io.Serializable

class Carro(var matricula: String,
            var marca: String,
            var modelo: String,
            var ano: String): Serializable {
    override fun toString(): String {
        return matricula}}
