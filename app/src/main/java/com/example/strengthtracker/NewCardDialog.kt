package com.example.strengthtracker


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.view.*
import kotlinx.android.synthetic.main.dialog_newcard.*

class NewCardDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
    : View? {
        var rootView: View = inflater.inflate(R.layout.dialog_newcard,container,false)

        val completeButt = rootView.findViewById<Button>(R.id.complete)
        completeButt.setOnClickListener() {
            //Check if all fields are complete



            dismiss()
        }

        return rootView
       // return super.onCreateView(inflater, container, savedInstanceState)
    }


}
