package com.example.strengthtracker

import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_home.*
import android.widget.Toast
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.gson.Gson
import java.io.FileOutputStream
import com.google.firebase.storage.ktx.storage
import java.io.File


class Home : AppCompatActivity(), LifecycleOwner
{
private lateinit var cardAdapter: CardAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Setup Firebase Storage
        val storage = FirebaseStorage.getInstance().reference
        val cardStorage = storage.child("csv/cardList.txt")

        // Context.getFilesDir().getPath()
        /*Path to the cardListCSV
        /data/data/com.example.strengthtracker/files/cardList.csv
        */

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
        var cardListCsvFile = Uri.fromFile(File("/data/data/com.example.strengthtracker/files/cardList.csv"))

        //Toast messages
        val emptyField = "Please fill out field(s)"
        val duration = Toast.LENGTH_SHORT
        val emptyFieldCardCreation = Toast.makeText(applicationContext, emptyField, duration)

        //Variables
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
                cardStorage.putFile(cardListCsvFile)
                newCard.visibility = View.INVISIBLE
            }
            else{
                emptyFieldCardCreation.show()
            }
        }

    }

}