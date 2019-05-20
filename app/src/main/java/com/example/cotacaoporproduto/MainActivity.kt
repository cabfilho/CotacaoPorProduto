package com.example.cotacaoporproduto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cotacaoporproduto.modelo.CotacaoApiModel
import com.example.cotacaoporproduto.modelo.Produto
import com.example.cotacaoporproduto.service.RetrofitConfig
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    var cotacaoUSD: Double = 0.0




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val call: Call<CotacaoApiModel>
        val countriesAPI = RetrofitConfig.cotacaoService

        try {

            call = countriesAPI.buscaCotacao()
            Log.d("Http Retroift",call.request().toString())

            call.enqueue(object : Callback<CotacaoApiModel> {
                override fun onResponse(call: Call<CotacaoApiModel>, response: Response<CotacaoApiModel>) {
                    Log.e("CotacaoService   ", "Entrou")


                    if (response.code() == 200) {
                        var cotacoes = response.body()

                        cotacaoUSD = cotacoes!!.USDT!!.ask!!.toDouble()
                        setupRecyclerView(cotacaoUSD)
                        Log.e("CotacaoService   ", "Entrou cotacao:" + cotacaoUSD.toString())
                    }


                }

                override fun onFailure(call: Call<CotacaoApiModel>, t: Throwable) {
                    Log.e("CotacaoService   ", "Erro ao buscar o cotacao:" + t.message)
                }
            })
        } catch (e: Exception) {
            Log.e("RetrofitConfig", e.message)
        }




    }
    fun addProduto(view: View) {
        val intent = Intent(this, IncluiProduto::class.java)

        startActivityForResult(intent, 1)

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        // check if the requestCode is the wanted one and if the result is what we are expecting
        if (requestCode == 1 && resultCode == RESULT_OK) {
            setupRecyclerView(cotacaoUSD)
        }
    }

    private fun setupRecyclerView(cotacao:Double){


        //progressBar = findViewById(R.id.progressBar)
        var lista: MutableList<Produto>  = mutableListOf()


        val repository = ProdutoRepository(this)
        lista = repository.getAllProduto()
        val adapter = CotacaoAdapter(lista, cotacao)

        recyclerItem.adapter = adapter

        val lm = LinearLayoutManager(applicationContext)

        recyclerItem.layoutManager = lm

        recyclerItem.addItemDecoration(
            DividerItemDecoration(applicationContext,
                DividerItemDecoration.VERTICAL))



    }
}
