package com.example.redsea.service.ui.adapters.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.redsea.R
import com.example.redsea.network.ViewWellsResponse.ViewWells
import com.example.redsea.network.ViewWellsResponse.ViewWellsItem
import com.example.redsea.service.ui.fragments.EditRequestFragment
import com.example.redsea.service.ui.fragments.ViewWellDetailsFragment
class ViewWellAdapter(private val fragmentTransaction: FragmentTransaction?, private val viewWells: ViewWells) :
    RecyclerView.Adapter<ViewWellAdapter.viewWellViewHolder>() {

    class viewWellViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        val viewWellName = viewItem.findViewById<TextView>(R.id.wellNameViewWellTV)
        val dateIn = viewItem.findViewById<TextView>(R.id.dateInTV)
        val dateOut = viewItem.findViewById<TextView>(R.id.dateOutTV)
        val viewWellItem = viewItem.findViewById<ConstraintLayout>(R.id.viewWellItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewWellViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_well, parent, false)
        return viewWellViewHolder(view)
    }

    override fun getItemCount(): Int {
        return viewWells.size
    }

    override fun onBindViewHolder(holder: viewWellViewHolder, position: Int) {
        val currentWell = viewWells[position]

        holder.viewWellName.text = currentWell.name
        holder.dateIn.text = currentWell.from
        holder.dateOut.text = currentWell.to

        holder.viewWellItem.setOnClickListener {
            navigateToViewWellDetailsFragment(currentWell)
        }
    }

    private fun navigateToViewWellDetailsFragment(well: ViewWellsItem) {
        val viewWellDetailsFragment = ViewWellDetailsFragment()

        val bundle = Bundle()
        bundle.putSerializable("wellItem", well)
        viewWellDetailsFragment.arguments = bundle
        fragmentTransaction?.replace(R.id.fragmentContainer, viewWellDetailsFragment)
        fragmentTransaction?.addToBackStack(null)
        fragmentTransaction?.commit()
    }
}
