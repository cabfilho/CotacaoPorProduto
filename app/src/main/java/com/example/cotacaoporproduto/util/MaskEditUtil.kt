package com.example.cotacaoporproduto.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.DecimalFormat

object MaskEditUtil {

    val FORMAT_CPF = "###.###.###-##"
    val FORMAT_FONE = "(###)####-#####"
    val FORMAT_CEP = "#####-###"
    val FORMAT_DATE = "##/##/####"
    val FORMAT_HOUR = "##:##"
    val FORMAT_MOEDA = "###.###,##"
    /**
     * Método que deve ser chamado para realizar a formatação
     *
     * @param ediTxt
     * @param mask
     * @return
     */
    fun mask(ediTxt: EditText, mask: String): TextWatcher {
        return object : TextWatcher {
            internal var isUpdating: Boolean = false
            internal var old = ""

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val str = MaskEditUtil.unmask(s.toString())
                var mascara = ""
                if (isUpdating) {
                    old = str
                    isUpdating = false
                    return
                }
                var i = 0
                for (m in mask.toCharArray()) {
                    if (m != '#' && str.length > old.length) {
                        mascara += m
                        continue
                    }
                    try {
                        mascara += str[i]
                    } catch (e: Exception) {
                        break
                    }

                    i++
                }
                isUpdating = true
                ediTxt.setText(mascara)
                ediTxt.setSelection(mascara.length)
            }
        }
    }

    fun unmask(s: String): String {
        return s.replace("[.]".toRegex(), "").replace("[-]".toRegex(), "").replace("[/]".toRegex(), "").replace("[(]".toRegex(), "").replace("[ ]".toRegex(), "").replace("[:]".toRegex(), "").replace("[)]".toRegex(), "")
    }

    fun maskMoeda(valor : Double): String {
        val df = DecimalFormat("#,###.00")

        return df.format(valor)
    }
}