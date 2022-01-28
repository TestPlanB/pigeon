package com.example.pigeon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.pigeon_core.SubscribeEnv
import com.example.pigeon_core.extention.post
import com.example.pigeon_core.extention.subscribeEvent


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val text = this.findViewById<TextView>(R.id.text)
        text.setOnClickListener {
            this.post("test normal message")
        }


        val text2 = this.findViewById<TextView>(R.id.text2)
        text2.setOnClickListener {
            Log.i("hello", "click")
            this.post("test stick message",true)

        }

        val text3 = this.findViewById<TextView>(R.id.text3)
        text3.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }


        this.subscribeEvent(String::class.java, SubscribeEnv.MAIN) {
            Log.i("hello", "main activity receive a message $it current thread ${Thread.currentThread()}")
        }

    }


}