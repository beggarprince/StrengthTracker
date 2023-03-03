package com.example.strengthtracker

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_home.*
import java.io.File
import java.io.FileOutputStream



class Home : AppCompatActivity(), LifecycleOwner
{
private lateinit var cardAdapter: CardAdapter
lateinit var vm: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Setup Firebase Storage
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val storage = FirebaseStorage.getInstance().reference
        val cardStorage = storage.child("csv/cardList.txt")

        //Setup RecyclerView
        cardAdapter = CardAdapter(mutableListOf())
        recView.adapter = cardAdapter
        recView.layoutManager = LinearLayoutManager(this)

        //UI Setup
        val showNewCardDialog = findViewById<Button>(R.id.addCard)
        val complete = findViewById<Button>(R.id.cardComplete)
        val message = findViewById<TextView>(R.id.message)

        //File setup
        val fileOutputStream: FileOutputStream = openFileOutput("cardList.csv", MODE_APPEND)
        var cardListCsvFile = Uri.fromFile(File(
            "csv/"+ firebaseUser + "/cardList.csv"))
        //var userCsvReference = storage.child("users/"+ currentFirebaseUser + "/cardList.csv")

        //Toast messages
        val emptyField = "Please fill out field(s)"
        val duration = Toast.LENGTH_SHORT
        val emptyFieldCardCreation = Toast.makeText(applicationContext, emptyField, duration)

        var data: String
        var gson = Gson()
        var json: String

        showNewCardDialog.setOnClickListener{
            newName.text.clear()
            newRep.text.clear()
            newWeight.text.clear()
            newCard.visibility = View.VISIBLE
        }

        complete.setOnClickListener{
            if((newName.text.toString().isNotEmpty()) && (newRep.text.toString().isNotEmpty()) && (newWeight.text.toString().isNotEmpty()))
            {
                //Create Card and add it to the mutable list
                val card = Card(newName.text.toString(), newRep.text.toString(), newWeight.text.toString(), R.drawable.default_icon)
                cardAdapter.addCard(card)

                //Add to csv file
                data = cardAdapter.cardToCsv(card, "0")
                fileOutputStream.write(data.toByteArray())
               // json = gson.toJson(card)
                //cardStorage.putBytes(data.toByteArray())
                //cardStorage.putFile(cardListCsvFile)

                newCard.visibility = View.INVISIBLE
            }
            else{
                emptyFieldCardCreation.show()
            }
        }

    }

}