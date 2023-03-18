package com.example.strengthtracker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.mikephil.charting.charts.LineChart
import android.view.View
import android.widget.EditText
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.util.*

class Log : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)
        //Retrieve Extras
        val user = intent.extras?.getString("firebaseUser")
        val title = findViewById<TextView>(R.id.logTitle)
        title.text = intent.extras?.getString("Card")


        //View model
        val lvm: LogViewModel by viewModels()
        //LineChart
        lineChart = findViewById<LineChart>(R.id.chart1)

        lvm.setupLogRef(title.text.toString(), user!!, this@Log)

        //Setup UI
        val days = findViewById<TextView>(R.id.dayRecorded)
        val recordLog = findViewById<Button>(R.id.newLog)
        val monthlyPr = findViewById<TextView>(R.id.monthlySets)
        val monthlyTonnage = findViewById<TextView>(R.id.monthlyTonnage)


        //LogCard
        val logCard = findViewById<ConstraintLayout>(R.id.logCard)
        val logRep = findViewById<EditText>(R.id.logRep)
        val logWeight = findViewById<EditText>(R.id.logWeight)
        val logSet = findViewById<EditText>(R.id.logSets)
        val logComplete = findViewById<Button>(R.id.logComplete)

        recordLog.setOnClickListener{
            logCard.visibility = View.VISIBLE
            lineChart.visibility = View.INVISIBLE

        }

        logComplete.setOnClickListener{
            if(lvm.checkLogEntry(logRep.text.toString(),
            logWeight.text.toString(),
            logSet.text.toString())) {
                logCard.visibility = View.INVISIBLE
                lineChart.visibility = View.VISIBLE
                logRep.text.clear()
                logWeight.text.clear()
                logSet.text.clear()

            }
        }

    }
}
