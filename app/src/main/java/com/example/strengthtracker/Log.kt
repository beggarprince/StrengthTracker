package com.example.strengthtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import com.github.mikephil.charting.charts.LineChart
import android.view.View
import android.widget.EditText
import org.w3c.dom.Text
import java.util.*

class Log : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)

        //View model
        val lvm: LogViewModel by viewModels()

        //Setup UI
        val title = findViewById<TextView>(R.id.logTitle)
        val days = findViewById<TextView>(R.id.dayRecorded)
        val recordLog = findViewById<Button>(R.id.newLog)
        val monthlyPr = findViewById<TextView>(R.id.monthlySets)
        val monthlyTonnage = findViewById<TextView>(R.id.monthlyTonnage)


        val lineChart = findViewById<LineChart>(R.id.chart1)

        //LogCard
        val logCard = findViewById<ConstraintLayout>(R.id.logCard)
        val logRep = findViewById<EditText>(R.id.logRep)
        val logWeight = findViewById<EditText>(R.id.logWeight)
        val logSet = findViewById<EditText>(R.id.logSets)
        val logComplete = findViewById<Button>(R.id.logComplete)

        //Retrieve Extras
        title.text = intent.extras?.getString("Card")
        val user = intent.extras?.getString("firebaseUser")

        //Retrieve csv, setup firebase references
        lvm.setupLogRef(title.text.toString(),user!!, this)

        var currentTime = Calendar.getInstance()


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