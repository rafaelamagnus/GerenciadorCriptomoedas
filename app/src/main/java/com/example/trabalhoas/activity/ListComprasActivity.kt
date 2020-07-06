package com.example.trabalhoas.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trabalhoas.AdapterListCompra
import com.example.trabalhoas.R
import com.example.trabalhoas.db.Ativo
import com.example.trabalhoas.db.AtivoDao
import kotlinx.android.synthetic.main.activity_list_compras.*

class ListComprasActivity : AppCompatActivity() {

    private var ativosList = mutableListOf<Ativo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_compras)
        updateAdapter()
        initRecycleView()
    }

    override fun onRestart() {
        super.onRestart()
        updateAdapter()
        initRecycleView()
    }

    override fun onResume() {
        super.onResume()
        updateAdapter()
        initRecycleView()
    }

    private fun updateAdapter(){
        val ativoDao = AtivoDao(this)
        ativosList.clear()
        ativosList = ativoDao.getAll()
        if (ativosList.isEmpty()){
            recycleListCompra.setVisibility(View.GONE)
            txtMsg.setVisibility(View.VISIBLE)
            txtMsg.setText("Sem dados para exibir")
        }else{
            recycleListCompra.setVisibility(View.VISIBLE)
            txtMsg.setVisibility(View.GONE)
        }

        recycleListCompra.adapter?.notifyDataSetChanged()
    }

    private fun initRecycleView(){
        val adapter2 = AdapterListCompra(ativosList)
        recycleListCompra.adapter = adapter2
        val layout = LinearLayoutManager(this)
        recycleListCompra.layoutManager = layout
    }


}