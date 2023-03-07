package com.example.strengthtracker

import android.content.Context
import android.content.Context.MODE_APPEND
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.protobuf.LazyStringArrayList
import java.io.File
import java.io.FileOutputStream
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader


class HomeViewModel : ViewModel() {
 var cardAdapter= CardAdapter(mutableListOf())

    lateinit var csvLine: String
    //Setup Firebase Storage
    val firebaseUser = FirebaseAuth.getInstance().uid
    val firebaseMainDir = FirebaseStorage.getInstance().reference
    var firebaseCsvRef = firebaseMainDir.child("users/"+ firebaseUser + "/cardList.csv") //Reference for firebase csv
    //TODO change hardcoded path
    val androidCsvRef = "/data/data/com.example.strengthtracker/files/cardList.csv" //Reference for localCsv
    lateinit var fOut: FileOutputStream //Output stream to write to localCsv
    lateinit var localCsv: Uri //File upload to Firebase

    //Setup Storage
    fun storageSetup(context: Context){
        //Get firebase file
        //Setup Output Stream
       fOut  = context.openFileOutput("cardList.csv", 0)
        println("Attempting to connect to Firebase\n")
        var localFile = File.createTempFile("cardList", "csv")
        firebaseCsvRef.getFile(localFile).addOnSuccessListener {
            println("Successfully Downloaded CSV")
            populateRecView(localFile)

        }.addOnFailureListener {
            println("CSV not downloaded\n$firebaseUser\n")
        }
        localCsv = Uri.fromFile(File(androidCsvRef))
    }

    fun populateRecView(inputStream: File) {
        val reader = inputStream.bufferedReader()
        var header = reader.readLine()
          while (header != null) {
              val (name, rep, weight, img) = header.split(',', ignoreCase = false, limit = 4)
              println("\n$name $rep $weight")
              var newcard =Card(name, rep, weight.trim().removeSurrounding("\""), R.drawable.default_icon)

              cardAdapter.addCard(newcard)
              csvLine = cardToCsv(newcard, "0")
              fOut.write(csvLine.toByteArray())

              header = reader.readLine()
          }
        reader.close()
    }
    fun checkComplete( name: String,
                      rep: String,
                      weight: String): Boolean{
        if((name.isNotEmpty())
            && (rep.isNotEmpty())
            && (weight.isNotEmpty()))
        {
            addCard(name, rep, weight)
            return true}
        else return false
    }

    fun addCard(name: String,
                rep: String,
                weight: String) {
        val card = Card(name,rep,weight,R.drawable.default_icon)
        cardAdapter.addCard(card)
        csvLine = cardToCsv(card, "0")
        fOut.write(csvLine.toByteArray())
        updateFirebase(localCsv)
    }

    fun updateFirebase(uri: Uri){
        //Would preferably simply write to the file, thils will do for now
        firebaseCsvRef.putFile(uri)
    }
    fun csvToString(name: String, rep: String, weight: String, imgFlag: String ): String {
         csvLine = "$name,$rep,$weight,$imgFlag\n"
        return csvLine
    }

    fun cardToCsv(card: Card, imgFlag: String): String
    {
        return "${card.title},${card.reps},${card.weight},$imgFlag\n"
    }


}