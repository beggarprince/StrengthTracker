package com.example.strengthtracker

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.io.FileOutputStream


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
              addCard(name, rep, weight.trim().removeSurrounding("\""))
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
    }

    fun updateFirebase(){
        firebaseCsvRef.putFile(localCsv)
    }
    fun cardToCsv(card: Card, imgFlag: String): String {
        return "${card.title},${card.reps},${card.weight},$imgFlag\n"
    }
    fun updateCsv(context: Context){
        fOut  = context.openFileOutput("cardList.csv", 0)
        var updatedCsv = cardAdapter.listToCsv()
        fOut.write(updatedCsv.toByteArray())
        updateFirebase()
    }


}