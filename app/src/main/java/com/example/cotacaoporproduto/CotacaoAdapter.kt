package com.example.cotacaoporproduto

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.cotacaoporproduto.modelo.Produto
import org.w3c.dom.Text

class CotacaoAdapter(internal var items: MutableList<Produto>, cotacao:Double) : Adapter<RecyclerView.ViewHolder>() {

    private lateinit var context: Context
    private var cotacaoUSD = cotacao
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_layout, parent, false)
        context = parent.context
        return CotacaoViewHolder(itemView, context)

    }
    fun setData(list: MutableList<Produto>){
        items = list
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return items.size
    }
    fun addItem(novoProduto: Produto) {
        items.add(novoProduto)

        notifyItemInserted(items.size - 1)
    }
    fun removeItem(removed: Produto) {
        for (i in items.indices) {
            if (removed.id == items[i].id) {
                items.removeAt(i)
                notifyItemRemoved(i)
            }
        }
    }
    fun changeItem(changed: Produto) {
        for (i in items.indices) {
            if (changed.id == items[i].id) {
                items[i] = changed
                notifyItemChanged(i)
            }
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val produto = items[position]

        val viagemViewHolder = holder as CotacaoViewHolder

        viagemViewHolder.txtProduto.setText(produto.nomeProduto)
        viagemViewHolder.txtValorEua.setText(produto.valorEua.toString())
        viagemViewHolder.txtValorReal.setText(produto.valorReal.toString())
        var convertido = produto.valorEua * cotacaoUSD
        viagemViewHolder.txtValorConvertido.setText(convertido.toString())

        viagemViewHolder.itemView.setOnClickListener {
            val id = items[position].id
            val intent = Intent(context, IncluiProduto::class.java)
            intent.putExtra("id", id.toString())
            context!!.startActivity(intent)
        }

    }
    inner class CotacaoViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView) {

        var txtProduto: TextView
        var txtValorReal: TextView
        var deleteButton: ImageButton
        var txtValorConvertido : TextView
        var txtValorEua : TextView

        init {

            txtProduto = itemView.findViewById(R.id.txtProduto)
            txtValorReal = itemView.findViewById(R.id.txtValorReal)
            deleteButton = itemView.findViewById(R.id.imgDeleteBtn)
            txtValorConvertido = itemView.findViewById(R.id.txtVlrConvertidoInput)
            txtValorEua = itemView.findViewById(R.id.txtValorEua)

            deleteButton.setOnClickListener {
                val index = adapterPosition
                //val idToRemove = items[index].key

                val produtoRepository = ProdutoRepository(context)
                produtoRepository.deleteProduto(items[index])
                items.removeAt(index)
                notifyItemRemoved(index)


            }
        }
    }
}