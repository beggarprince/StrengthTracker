package com.example.strengthtracker

import android.content.Context
import android.content.Context.MODE_APPEND
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.FileOutputStream

lateinit var fileName:String
lateinit var fOutLog: FileOutputStream
lateinit var logUri: Uri
lateinit var firebaseLogRef: StorageReference

class LogViewModel : ViewModel(){
    //       val firebaseLogRef = firebaseMainDir.child("users/" + firebaseUser + "/log/${name}.csv")
    private val firebaseMain = FirebaseStorage.getInstance().reference //Can't pass as extra

    fun setupLogRef(name: String,
                    firebaseUser:String,
    context:Context) {
        firebaseLogRef = firebaseMain.child(
            "users/" + firebaseUser + "/log/${name}.csv")
        fileName = name//for reference

        //Open local file output
        val logCsv = File.createTempFile(name, "csv")
        fOutLog =context.openFileOutput("${name}.csv",MODE_APPEND)
        val androidLogRef = "/data/data/com.example.strengthtracker/files/${fileName}.csv" //Reference for localCsv
        logUri = Uri.fromFile(File(androidLogRef))

    }

    fun writeLog(line:String, context: Context){

        fOutLog.write(line.toByteArray())
        firebaseLogRef.putFile(logUri)
    }

}