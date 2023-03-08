package com.example.strengthtracker

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_home.*
import androidx.activity.viewModels

class Home : AppCompatActivity(), LifecycleOwner
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val vm: HomeViewModel by viewModels()

        recView.adapter = vm.cardAdapter
        recView.layoutManager = LinearLayoutManager(this)

        //UI Setup
        val showNewCardDialog = findViewById<Button>(R.id.addCard)
        val complete = findViewById<Button>(R.id.cardComplete)
        val message = findViewById<TextView>(R.id.message)
        val updateCsv = findViewById<Button>(R.id.update)

       vm.storageSetup(this)

        //Toast messages
        val emptyField = "Please fill out field(s)"
        val duration = Toast.LENGTH_SHORT
        val emptyFieldCardCreation = Toast.makeText(applicationContext, emptyField, duration)

        //Show new card screen
        showNewCardDialog.setOnClickListener{
            recView.visibility = View.INVISIBLE
            newRep.visibility = View.VISIBLE
            newWeight.visibility = View.VISIBLE
            newName.visibility = View.VISIBLE
            cardComplete.visibility = View.VISIBLE
            addCard.visibility = View.INVISIBLE
        }

        //Finish card creation
        complete.setOnClickListener{
            if(vm.checkComplete(newName.text.toString(),
                    newRep.text.toString(),
                    newWeight.text.toString()))
            {
                newName.text.clear()
                newRep.text.clear()
                newWeight.text.clear()
                recView.visibility = View.VISIBLE
                newRep.visibility = View.INVISIBLE
                newWeight.visibility = View.INVISIBLE
                newName.visibility = View.INVISIBLE
                cardComplete.visibility = View.INVISIBLE
                addCard.visibility = View.VISIBLE

                vm.updateFirebase()
            }
            else {emptyFieldCardCreation.show()
            }
        }
        update.setOnClickListener{
            vm.updateCsv(this)
        }


    }

}