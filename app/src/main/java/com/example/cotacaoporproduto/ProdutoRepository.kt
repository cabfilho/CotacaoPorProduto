package com.example.cotacaoporproduto

import android.content.Context
import com.example.cotacaoporproduto.database.ProdutoDataBase
import com.example.cotacaoporproduto.database.ProdutosDAO
import com.example.cotacaoporproduto.modelo.Produto

class ProdutoRepository(context: Context) {
    val produtoDAO: ProdutosDAO = ProdutoDataBase.get(context).publicacaoDao()
    val contextInterno:Context = context
    fun insertProduto(produto: Produto){
        produtoDAO.insert(produto)
    }
    fun updateProduto(produto: Produto){
        produtoDAO.update(produto)
    }
    fun deleteProduto(produto: Produto){
        produtoDAO.delete(produto)
    }
    fun getProduto(id: Int) : Produto{
       return produtoDAO.getProduto(id)
    }
    fun getAllProduto() : MutableList<Produto>{
       return  produtoDAO.getAllProdutos()
    }
}