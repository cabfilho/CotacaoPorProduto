package com.example.cotacaoporproduto.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cotacaoporproduto.modelo.Produto

@Database(entities = [Produto::class], version = 1)
abstract class ProdutoDataBase: RoomDatabase() {

    abstract fun publicacaoDao(): ProdutosDAO

    companion object {
        var INSTANCE: ProdutoDataBase? = null
        fun get(context: Context): ProdutoDataBase{
            return INSTANCE ?: run {
                val instance = Room.databaseBuilder(
                    context,
                    ProdutoDataBase::class.java,
                    "ProdutoDataBase"
                ).allowMainThreadQueries().fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }

}