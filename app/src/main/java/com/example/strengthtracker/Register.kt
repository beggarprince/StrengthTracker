package com.example.strengthtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val regSub = findViewById<Button>(R.id.regSub)
        val regEmail : TextView = findViewById(R.id.regEmail)
        val regPass : TextView = findViewById(R.id.regPass)
        var regMessage : TextView = findViewById(R.id.regMessage)

        regSub.setOnClickListener{
            if((regEmail.text.isNotEmpty()) && (regPass.text.isNotEmpty()))
            {

                val regEmailString : String = regEmail.text.toString()
                val regPassString : String = regPass.text.toString()

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(regEmailString, regPassString)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            regMessage.text =
                                "User email: $regEmailString"
                            finish()
                        } else regMessage.text = "Failed to create new user"

                    }
            }
            else
            {
                regMessage.text = "Please fill out all fields"
            }
        }
    }
}