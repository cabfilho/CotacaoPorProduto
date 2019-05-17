package com.example.cotacaoporproduto.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Produto(
        var nomeProduto: String = "",
        var valorReal: Double = 0.0,
        var valorEua: Double = 0.0,
        @PrimaryKey(autoGenerate = true) var id:Int=0

)
