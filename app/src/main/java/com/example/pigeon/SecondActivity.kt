package com.example.pigeon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.pigeon_core.MessageCenter

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val text = this.findViewById<TextView>(R.id.text)
        text.setOnClickListener {
            val test = "123"
            val test2 = ArrayList<String>()
            MessageCenter.post(test)
        }
    }
}