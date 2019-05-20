package com.example.cotacaoporproduto.service

import com.example.cotacaoporproduto.modelo.CotacaoApiModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface CotacaoService {
   @GET("all")
   fun buscaCotacao(): Call<CotacaoApiModel>
  // https://api.hgbrasil.com/finance?format=json&key=d55c5156
      //  fun getQuestions(@Query("order") order: String, @Query("sort") sort: String,
        //                 @Query("tagged") tagged: String,@Query("site") site: String): Call<StackQuestion>
}
//https://api.stackexchange.com/2.2/questions?order=desc%20&sort=activity&tagged=android&site=stackoverflow

//        @GET("finance?format=json&key=d55c5156")
   //     ?format=json&key=d55c5156"