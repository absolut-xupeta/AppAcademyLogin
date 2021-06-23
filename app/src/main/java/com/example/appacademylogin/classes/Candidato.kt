package com.example.appacademylogin.classes

import com.example.appacademylogin.utils.Utils
import java.util.*

/**
 * Class to store the candidates information's.
 */
class Candidato {
    var nome: String

    var vaga: String
        set(value) {
            field = value.lowercase()
        }

    var idade: Int

    var estado: String
        set(value) {
            field = value.lowercase()
        }

    var username: String
        set(value) {
            field = Utils.getLogin(value)
        }
    var password: Int
        set(value) {
            field = Calendar.getInstance().get(Calendar.YEAR) - value
        }

    constructor(nome: String, vaga: String, idade: Int, estado: String) {
        this.nome = nome
        this.vaga = vaga
        this.idade = idade
        this.estado = estado
        this.username = nome
        this.password = idade
    }

    constructor(nome: String, ano: Int, estado: String) {
        this.nome = nome
        this.idade = Calendar.getInstance().get(Calendar.YEAR) - ano
        this.vaga = "QA"
        this.estado = estado
        this.username = nome
        this.password = ano
    }
}