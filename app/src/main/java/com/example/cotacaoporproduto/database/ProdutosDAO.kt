package com.example.cotacaoporproduto.database

import androidx.room.*
import com.example.cotacaoporproduto.modelo.Produto

@Dao
interface ProdutosDAO {
    @Query("SELECT * FROM Produto")
    fun getAllProdutos(): MutableList<Produto>

    @Query("select * from Produto where id =:id")
    fun getProduto(id:Int): Produto

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(produto: Produto): Long

    @Update()
    fun update(produto: Produto)

    @Delete()
    fun delete(produto: Produto)

  //  @Insert(onConflict = OnConflictStrategy.REPLACE)
  //  fun insertAll(publicacaoList: List<Publicacao>)




}