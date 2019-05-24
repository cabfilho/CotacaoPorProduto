package com.example.cotacaoporproduto

import android.app.Activity
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Spinner
import com.example.cotacaoporproduto.modelo.Produto
import kotlinx.android.synthetic.main.activity_inclui_produtos.*
import java.util.*

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
        nomeProduto = inputProduto
        valorEua = inputVlrEua
        valorBr = inputVlrBrasil
        repository = ProdutoRepository(this)
        if (extras != null) {

            id = extras.get("id").toString()
        }

        if (id.isNotBlank()) {

            lblTitulo.text = "Altere Seu Produto"
            produto = repository.getProduto(id.toInt())
            btnDeletar.visibility = View.VISIBLE
            if(produto!= null) {
                nomeProduto!!.setText(produto!!.nomeProduto)
                valorEua!!.setText(produto!!.valorEua.toString())
                valorBr!!.setText(produto!!.valorReal.toString())


            }
        }else{
            actionBar?.title = "Cadastre Seu Produto"
            btnDeletar.visibility = View.INVISIBLE
        }



    }
    fun deletePublicacao(view: View){
        val produto = Produto(nomeProduto!!.text.toString(),valorBr!!.text.toString().toDouble(),valorEua!!.text.toString().toDouble())
        produto.id = id.toInt()
        repository.deleteProduto(produto)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
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
