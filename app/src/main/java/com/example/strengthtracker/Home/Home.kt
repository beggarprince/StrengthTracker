package com.example.strengthtracker.Home

import android.content.DialogInterface
import android.content.Intent
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
import androidx.appcompat.app.AlertDialog
import com.example.strengthtracker.Card
import com.example.strengthtracker.Log.Log
import com.example.strengthtracker.R

lateinit var deleteAlert: AlertDialog.Builder
lateinit var retrievedCard: Card
lateinit var logAlert: AlertDialog.Builder
class Home : AppCompatActivity(), LifecycleOwner
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //RecyclerView and ViewModel Setup
        val vm: HomeViewModel by viewModels()
        recView.adapter = vm.cardAdapter
        recView.layoutManager = LinearLayoutManager(this)

        //UI Views Setup
        val showNewCardDialog = findViewById<Button>(R.id.addCard)
        val complete = findViewById<Button>(R.id.cardComplete)
        val message = findViewById<TextView>(R.id.message)

            deleteAlert = AlertDialog.Builder( this)
            deleteAlert.apply {
                setTitle("Delete Item")
                setPositiveButton("Yes",
                    DialogInterface.OnClickListener { _, _ ->
                        message.text="${retrievedCard.title} Removed"
                        vm.delCard(retrievedCard)
                        vm.updateCsv(this@Home)
                    })
                setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { _, _ ->
                        message.text="${retrievedCard.title} Not Removed"
                    })
            }

        logAlert = AlertDialog.Builder(this)
        logAlert.apply {
            setTitle("View Log?")
            setPositiveButton("Yes", DialogInterface.OnClickListener{_,_ ->
                var newAct = Intent(this@Home, Log::class.java)
                newAct.putExtra("Card", "${retrievedCard.title}")
                newAct.putExtra("firebaseUser",vm.firebaseUser)
                startActivity(newAct)
            })
            setNegativeButton("Cancel",DialogInterface.OnClickListener {_,_ ->

            })
        }

        //Initialize recyclerview
       vm.storageSetup(this)

        //Toast messages
        val emptyField = "Please fill out field(s)"
        val duration = Toast.LENGTH_SHORT
        val emptyFieldCardCreation = Toast.makeText(applicationContext, emptyField, duration)

        //Show new card screen
        showNewCardDialog.setOnClickListener{
            //Hide Views
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
                //Hide Views
                vm.addCard(newName.text.toString(), newRep.text.toString(), newWeight.text.toString()
                ,this)
                newName.text.clear()
                newRep.text.clear()
                newWeight.text.clear()
                recView.visibility = View.VISIBLE
                newRep.visibility = View.INVISIBLE
                newWeight.visibility = View.INVISIBLE
                newName.visibility = View.INVISIBLE
                cardComplete.visibility = View.INVISIBLE
                addCard.visibility = View.VISIBLE

            }
            else {emptyFieldCardCreation.show()
            }

        }

    }

}

public fun getDeleteCard(card: Card){
    retrievedCard = card
    deleteAlert.show()
}
public fun getCard(card: Card){
    retrievedCard = card
    logAlert.show()
}
