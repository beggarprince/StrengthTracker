package com.example.strengthtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_home.*
import android.widget.Toast



class Home : AppCompatActivity(), LifecycleOwner
{
private lateinit var cardAdapter: CardAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        cardAdapter = CardAdapter(mutableListOf())
        recView.adapter = cardAdapter
        recView.layoutManager = LinearLayoutManager(this)
        var viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val showNewCardDialog = findViewById<Button>(R.id.addCard)
        val complete = findViewById<Button>(R.id.cardComplete)
        val emptyField = "Please fill out field(s)"
        val duration = Toast.LENGTH_SHORT
        val emptyFieldCardCreation = Toast.makeText(applicationContext, emptyField, duration)

        showNewCardDialog.setOnClickListener{
            newName.text.clear()
            newRep.text.clear()
            newWeight.text.clear()
            newCard.visibility = View.VISIBLE
        }

        complete.setOnClickListener{
            if((newName.text.toString().isNotEmpty()) && (newRep.text.toString().isNotEmpty()) && (newWeight.text.toString().isNotEmpty()))
            {
                val card: Card = Card(newName.text.toString(), newRep.text.toString(), newWeight.text.toString(), R.drawable.default_icon)
                cardAdapter.addCard(card)
                newCard.visibility = View.INVISIBLE
            }
            else{
                emptyFieldCardCreation.show()
            }
        }

    }

}