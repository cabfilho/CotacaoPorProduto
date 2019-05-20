package com.example.cotacaoporproduto.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfig {
    private val retrofit: Retrofit

    val cotacaoService: CotacaoService
        get() = this.retrofit.create(CotacaoService::class.java!!)

    init {

        this.retrofit = Retrofit.Builder()
            .baseUrl("https://economia.awesomeapi.com.br/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}