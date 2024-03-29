package com.example.strengthtracker.Home

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.strengthtracker.Card
import com.example.strengthtracker.R
import kotlinx.android.synthetic.main.card.view.*

class CardAdapter  (private val cardList: MutableList<Card>)
    : RecyclerView.Adapter<CardAdapter.CardViewHolder>()
{
    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {

        return CardViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val curCard = cardList[position]
        holder.itemView.apply {
            name.text = curCard.title
            weight.text = curCard.weight
            rep.text = curCard.reps

            deleteButton.setOnClickListener{
                getDeleteCard(curCard)
            }
            logButton.setOnClickListener{
                getCard(curCard)
            }

        }

    }
    fun addCard(newCard: Card) {
        cardList.add(newCard)
        notifyItemInserted(cardList.size-1)
    }
    fun deleteCard(card: Card) {
        cardList.remove(card)
        notifyDataSetChanged()
    }

    fun listToCsv():String
    {
        println("Creating new csv\n")
        var temp: String =""
        var index: Int = getItemCount() -1
        while(index >= 0)
        {
            temp += cardAdapterToCsv(cardList[index],"0")
            index--
        }
        println(temp)
        return temp
    }
    fun cardAdapterToCsv(card: Card, imgFlag: String): String {
        return "${card.title},${card.reps},${card.weight},$imgFlag\n"
    }

}