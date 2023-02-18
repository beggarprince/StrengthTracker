package com.example.strengthtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.widget.TextView
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val regSub = findViewById<Button>(R.id.regSub)
        val regEmail : TextView = findViewById(R.id.regEmail) as TextView
        val regPass : TextView = findViewById(R.id.regPass) as TextView
        var regMessage : TextView = findViewById(R.id.regMessage) as TextView

        regSub.setOnClickListener{
            if((regEmail.text.isNotEmpty()) && (regPass.text.isNotEmpty()))
            {

                val regEmailString : String = regEmail.text.toString()
                val regPassString : String = regPass.text.toString()

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(regEmailString, regPassString)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            regMessage.text =
                                "User email: $regEmailString"// \n User password: $regPassString"
                            /*  intent.putExtra("user_id",regEmailString)
                              intent.putExtra("pass_id", regPassString)*/
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