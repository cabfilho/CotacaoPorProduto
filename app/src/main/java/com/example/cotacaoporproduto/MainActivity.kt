package com.example.cotacaoporproduto

import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cotacaoporproduto.modelo.CotacaoApiModel
import com.example.cotacaoporproduto.modelo.Produto
import com.example.cotacaoporproduto.service.RetrofitConfig
import com.example.cotacaoporproduto.util.MaskEditUtil
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
                        txtVlrCotacao.text = MaskEditUtil.maskMoeda(cotacaoUSD)
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
        setupRecyclerView(0.0)



    }
    fun addProduto(view: View) {
        val intent = Intent(this, IncluiProduto::class.java)

        startActivityForResult(intent, 1)

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        // check if the requestCode is the wanted one and if the result is what we are expecting
        if (requestCode == 1 && resultCode == RESULT_OK) {
            setupRecyclerView(cotacaoUSD)
            txtVlrCotacao.text = MaskEditUtil.maskMoeda(cotacaoUSD)
        }
    }

    private fun setupRecyclerView(cotacao:Double){


        //progressBar = findViewById(R.id.progressBar)
        var lista: MutableList<Produto>  = mutableListOf()


        val repository = ProdutoRepository(this)
        lista = repository.getAllProduto()
        var valorTotalConvertido = 0.0
        for(prod in lista){
            valorTotalConvertido = valorTotalConvertido + prod.valorEua
        }
        valorTotalConvertido = valorTotalConvertido * cotacao
        txtVlrTotalConvertido.text = MaskEditUtil.maskMoeda(valorTotalConvertido)

        val adapter = CotacaoAdapter(lista, cotacao)

        recyclerItem.adapter = adapter

        val lm = LinearLayoutManager(applicationContext)

        recyclerItem.layoutManager = lm

        recyclerItem.addItemDecoration(MarginItemDecoration(8))



    }

    class MarginItemDecoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View,
                                    parent: RecyclerView, state: RecyclerView.State) {
            with(outRect) {
                if (parent.getChildAdapterPosition(view) == 0) {
                    top = spaceHeight
                }
                top = spaceHeight
                left =  spaceHeight
                right = spaceHeight
                bottom = spaceHeight
            }
        }
    }
}
