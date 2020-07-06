package com.example.trabalhoas.activity

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.trabalhoas.Coin
import com.example.trabalhoas.R
import com.example.trabalhoas.db.Ativo
import com.example.trabalhoas.db.AtivoDao
import com.example.trabalhoas.httpCoin
import kotlinx.android.synthetic.main.activity_ativo_comprado.*
import kotlinx.android.synthetic.main.activity_ativo_comprado.txtNome
import kotlinx.android.synthetic.main.activity_ativo_comprado.txtValor
import kotlinx.android.synthetic.main.activity_compra_coin.*
import java.math.RoundingMode
import java.text.DecimalFormat

class AtivoCompradoActivity : AppCompatActivity() {

    private lateinit var ativo : Ativo
    private var asyncTask: AtivoTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ativo_comprado)

        ativo = intent.getParcelableExtra<Ativo>("ativo")
        carregarDados()
        txtNome.setText(ativo.nome.toString())
        txtValor.setText(total(ativo.total).toString())
        txtQtd.setText(ativo.qtd.toString())
        txtData.setText(ativo.data.toString())
        txtSigla.setText(ativo.sigla.toString())


        btnRemove.setOnClickListener{
            val ativoDao = AtivoDao(this)
            ativoDao.remove(ativo)
            onBackPressed()
        }
    }

    fun carregarDados(){
        if(asyncTask==null){
            if (httpCoin.hasConnetcion(this)){
                if(asyncTask?.status!= AsyncTask.Status.RUNNING){
                    asyncTask = AtivoTask()
                    asyncTask?.execute()
                }
            }
        }
    }

    inner class AtivoTask: AsyncTask<Void, Void, Coin?>(){

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: Void?): Coin? {
            return httpCoin.loadCoin(ativo.sigla.toString())
        }

        private fun update(result: Coin?){
            if(result != null){
                    result.last.toString()
                val variacao = variacao(result.high.toString().toDouble(), ativo.valor)
                txtVariacao.text = "$variacao"+"%"
                val lucro = lucro(ativo.qtd, ativo.total, result.high)
                txtLucro.text = lucro
            }

            asyncTask = null
        }

        override fun onPostExecute(result: Coin?) {
            super.onPostExecute(result)
            update(result as Coin?)
        }

    }

    fun variacao(vf: Double, vi: Double ): CharSequence? {
        val variacao = (vf/vi-1)*100

        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(variacao)
    }

    fun total(total: Double): String {
        val result = total
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(result)
    }

    fun lucro(qtd: Double, total: Double, preco:Double):String{
        val result = qtd*preco
        val lucro = total-result
        if(total<=result){
            val aux = lucro * -1
            return aux.toString()
        }else{
            val aux = lucro * -1
            return aux.toString()
        }
    }

}