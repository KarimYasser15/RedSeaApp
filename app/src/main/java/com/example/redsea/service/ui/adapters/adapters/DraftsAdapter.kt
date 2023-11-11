package com.example.redsea.service.ui.adapters.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.redsea.R
import com.example.redsea.network.Response.UserWells.UserWells

class DraftsAdapter(val userWells: UserWells) :
    RecyclerView.Adapter<DraftsAdapter.DraftsAdapterViewHolder>() {

    class DraftsAdapterViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        val name = viewItem.findViewById<TextView>(R.id.draftNameTV)
        val from = viewItem.findViewById<TextView>(R.id.fromDraftTV)
        val to = viewItem.findViewById<TextView>(R.id.toDraftTV)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DraftsAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_drafts, parent, false)
        return DraftsAdapterViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d("DRAFTSTEST", userWells.size.toString())
        return userWells.size
    }

    override fun onBindViewHolder(holder: DraftsAdapterViewHolder, position: Int) {
        Log.d("DRAFTSTEST", userWells[position].name)
        holder.setIsRecyclable(false)
        if (userWells[position].published != "published") {
            holder.name.text = userWells[position].name
            holder.from.text = userWells[position].from
            holder.to.text = userWells[position].to
        }
        else{

            holder.itemView.visibility = View.GONE

        }

    }
}