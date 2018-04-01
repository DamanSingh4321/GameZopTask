package com.singh.daman.gamezoptask.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ben.gamezop.io.Game
import com.singh.daman.gamezoptask.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.game_items.view.*

/**
 * Created by Daman on 3/31/2018.
 */

class GamesAdapter(private val gamesList: List<Game>, private val listener: (Game) -> Unit) : RecyclerView.Adapter<GamesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.game_items, parent, false)
        return GamesAdapter.ViewHolder(v)
    }

    override fun onBindViewHolder(holder: GamesAdapter.ViewHolder, position: Int) {
        holder.bindItems(gamesList[position], listener)
    }

    override fun getItemCount(): Int {
        return gamesList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(game: Game, listener: (Game) -> Unit) = with(itemView)
        {

            itemView.tvTitle.text = game.name
            Picasso.with(context).load(game.cover).fit().into(itemView.imgGame)
            itemView.setOnClickListener { listener(game) }
        }
    }

}