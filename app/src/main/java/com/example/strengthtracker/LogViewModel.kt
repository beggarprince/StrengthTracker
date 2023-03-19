package com.example.strengthtracker

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.FileOutputStream

lateinit var fileName:String
lateinit var fOutLog: FileOutputStream
lateinit var logUri: Uri
lateinit var firebaseLogRef: StorageReference

lateinit var lineChart: LineChart
lateinit var lineDataSetWeight: LineDataSet
lateinit var lineDataWeight: LineData


class LogViewModel : ViewModel(){
    //       val firebaseLogRef = firebaseMainDir.child("users/" + firebaseUser + "/log/${name}.csv")
    private val firebaseMain = FirebaseStorage.getInstance().reference //Can't pass as extra

    var lineChartArrayWeight = ArrayList<Entry>()

    fun setupLogRef(name: String,
                    firebaseUser:String,
    context:Context) {
        firebaseLogRef = firebaseMain.child(
            "users/" + firebaseUser + "/log/${name}.csv")
        fileName = name//for reference
        fOutLog =context.openFileOutput("${name}.csv",0)

        //Open local file output
        val logCsv = File.createTempFile(name, "csv")
        firebaseLogRef.getFile(logCsv).addOnSuccessListener {
            println("Successfully !${name}! CSV")
            //Get csv values from firebase
            retrieveCsv(logCsv)

        }.addOnFailureListener {
            println("CSV not downloaded\n$firebaseUser\n")
        }

        val androidLogRef = "/data/data/com.example.strengthtracker/files/${fileName}.csv" //Reference for localCsv
        logUri = Uri.fromFile(File(androidLogRef))

    }

    fun writeLog(line:String){
        fOutLog.write(line.toByteArray())
        firebaseLogRef.putFile(logUri)
    }

    fun checkLogEntry(rep: String,
    weight: String,
    set: String): Boolean{
        if(rep.isNotEmpty() && weight.isNotEmpty() && set.isNotEmpty())
        {
            val csv = logCsv(rep, weight, set, "03/16/23")
            writeLog(csv)
            return true
        }
        return false
    }

    fun logCsv(
        rep: String,
        weight: String,
        set: String,
        date: String
    ): String {
        return "$rep,$weight,$set,$date\n"
    }

    private fun retrieveCsv(inputStream : File){
        println("retrieveCsv running\n")
        val reader = inputStream.bufferedReader()
        var floatyMcFloaty: Float = 0f
        var header = reader.readLine()
        while (header != null) {
            val (rep, weight, set, date) =
                header.split(',', ignoreCase = false, limit = 4)
            println("Newline: ")
            val floaty: Float = weight.toFloat()
            lineChartArrayWeight.add(Entry(floatyMcFloaty,floaty))

            floatyMcFloaty += 10f
            println("$weight\n")
            writeLog(header+"\n")
            header = reader.readLine()
            println("loop finished\n")
        }
        lineDataSetWeight = LineDataSet(lineChartArrayWeight, "Weight")
        lineDataWeight = LineData(lineDataSetWeight)
        lineChart.setData(lineDataWeight)



        lineChart.invalidate()

        println("retrieveCsv finished\n")
        reader.close()

    }


}

