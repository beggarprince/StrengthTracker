package com.example.strengthtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Log : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)

        val title = findViewById<TextView>(R.id.logTitle)
        val days = findViewById<TextView>(R.id.dayRecorded)
        val recordLog = findViewById<Button>(R.id.newLog)

        title.text = intent.extras?.getString("Card")

        recordLog.setOnClickListener{

        }

    }
}