package com.example.pigeon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.pigeon_core.MessageCenter
import com.example.pigeon_core.SubscribeEnv
import com.example.pigeon_core.extention.post
import com.example.pigeon_core.extention.subscribeEvent

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val text = this.findViewById<TextView>(R.id.text)
        text.setOnClickListener {
            val test = "123"
            val test2 = ArrayList<String>()
            this.post("123")
        }

        this.subscribeEvent(String::class.java) {
            Log.i("hello", "second receive a message $it current thread ${Thread.currentThread()}")
        }
    }
}