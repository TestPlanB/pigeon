package com.example.pigeon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.pigeon_core.SubscribeEnv
import com.example.pigeon_core.extention.subscribeEvent


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val text = this.findViewById<TextView>(R.id.text)
        text.setOnClickListener {
            this.subscribeEvent(String::class.java,SubscribeEnv.IO) {
                Log.i("hello", "receive a message $it current thread ${Thread.currentThread()}")
            }
            startActivity(Intent(this, SecondActivity::class.java))
        }

    }


}