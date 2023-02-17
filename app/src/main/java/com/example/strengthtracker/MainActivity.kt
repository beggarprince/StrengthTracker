package com.example.strengthtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    //This would be the login through firebase

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener{
        val newAct = Intent(this,Home::class.java)
        startActivity(newAct)}
    }
}

