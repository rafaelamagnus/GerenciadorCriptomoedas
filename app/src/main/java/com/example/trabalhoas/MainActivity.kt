package com.example.trabalhoas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.trabalhoas.activity.AddCoinActivity
import com.example.trabalhoas.activity.ListComprasActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAdd.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, AddCoinActivity::class.java)
            startActivity(intent)
        })

        btnListCompra.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ListComprasActivity::class.java)
            startActivity(intent)
        })
    }
}