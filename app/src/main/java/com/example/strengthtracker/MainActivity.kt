package com.example.strengthtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val welcomeText: TextView = findViewById(R.id.welcome)
        val emailText : TextView = findViewById(R.id.editEmail)
        val passText : TextView = findViewById(R.id.password)
        val login = findViewById<Button>(R.id.loginButton)
        val register = findViewById<Button>(R.id.register)
        val loginEmail = intent.getStringExtra("user_id")
        val loginPass = intent.getStringExtra("pass_id")
        var auth = FirebaseAuth.getInstance()


        login.setOnClickListener{
            if((emailText.text.isNotEmpty()) &&  (passText.text.isNotEmpty())) {
                val emailTextString = emailText.text.toString()
                val passTextString = passText.text.toString()
                auth.signInWithEmailAndPassword(emailTextString, passTextString).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        welcomeText.text = "Logging In"
                        val home = Intent(this, Home::class.java)
                        startActivity(home)
                    } else {
                        welcomeText.text = "Incorrect Email/Password"
                    }
                }

            }
            else {welcomeText.text = "Please fill out field"
                val home = Intent(this, Home::class.java)//debug to skip sign in
                startActivity(home)
            }
        }
        register.setOnClickListener{
            val reg = Intent(this,Register::class.java)
            startActivity(reg)
        }
    }
}

