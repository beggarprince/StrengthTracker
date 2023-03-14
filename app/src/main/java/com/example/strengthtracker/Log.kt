package com.example.strengthtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels

class Log : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)

        //View model
        val lvm: LogViewModel by viewModels()

        //Setup UI
        val title = findViewById<TextView>(R.id.logTitle)
        val days = findViewById<TextView>(R.id.dayRecorded)
        val recordLog = findViewById<Button>(R.id.newLog)

        title.text = intent.extras?.getString("Card")
        val user = intent.extras?.getString("firebaseUser")

        //Retrieve csv, setup firebase references
        lvm.setupLogRef(title.text.toString(),user!!, this)


        recordLog.setOnClickListener{
            lvm.writeLog("This worked",this)
        }

    }
}