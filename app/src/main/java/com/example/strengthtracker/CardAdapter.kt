package com.example.strengthtracker

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
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
        }

    }

    fun addCard(newCard: Card)
    {
        cardList.add(newCard)
        notifyItemInserted(cardList.size-1)
    }


}