package com.example.cotacaoporproduto

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Spinner
import com.example.cotacaoporproduto.modelo.Produto

class IncluiProduto : AppCompatActivity() {
    private var nomeProduto: EditText? = null
    private var valorEua: EditText? = null
    private var valorBr: EditText? = null
    private lateinit var repository: ProdutoRepository
    internal lateinit var id: String
    internal var resultIntent = Intent()
    internal var produto: Produto? = Produto()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inclui_produtos)
        val extras = intent.extras
        id = ""
        nomeProduto = findViewById(R.id.inputProduto)
        valorEua = findViewById(R.id.inputVlrEua)
        valorBr = findViewById(R.id.inputVlrBrasil)
        repository = ProdutoRepository(this)

        if (extras != null) {

            id = extras.get("id").toString()
        }

        if (id.isNotBlank()) {

            produto = repository.getProduto(id.toInt())
            if(produto!= null) {
                nomeProduto!!.setText(produto!!.nomeProduto)
                valorEua!!.setText(produto!!.valorEua.toString())
                valorBr!!.setText(produto!!.valorReal.toString())


            }


        }



    }

    fun savePublicacao(view: View) {

        var bTemErro: Boolean = false

        if (nomeProduto!!.text.toString().isBlank()) {
            nomeProduto!!.error = "Nome do produto é obrigatório"
            bTemErro = true
        }
        if (valorEua!!.text.toString().isBlank()) {
            valorEua!!.error = "Valor dos EUA é obrigatório"
            bTemErro = true
        }
        if (valorBr!!.text.toString().isBlank()) {
            valorBr!!.error = "Valor do Brasil é obrigatório"
            bTemErro = true
        }


        if ((!bTemErro)) {

            //val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            //var dtConvert = dateFormat.parse(dtViagem.toString())

            val produto = Produto(nomeProduto!!.text.toString(),valorBr!!.text.toString().toDouble(),valorEua!!.text.toString().toDouble())

            if (id.isBlank()) {

                repository.insertProduto(produto)
                setResult(Activity.RESULT_OK, resultIntent)


            }else{
                produto.id = id.toInt()
                repository.updateProduto(produto)
                setResult(Activity.RESULT_OK, resultIntent)

            }
            finish()
        }
    }
}
