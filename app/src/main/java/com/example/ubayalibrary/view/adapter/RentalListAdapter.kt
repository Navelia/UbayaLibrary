package com.example.ubayalibrary.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ubayalibrary.R
import com.example.ubayalibrary.databinding.ItemRentalListBinding
import com.example.ubayalibrary.model.Rental

class RentalListAdapter(val rentalList: ArrayList<Rental>):RecyclerView.Adapter<RentalListAdapter.RentalViewHolder>() {
    class RentalViewHolder(var view: ItemRentalListBinding): RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RentalViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val view = DataBindingUtil.inflate<ItemRentalListBinding>(inflater, R.layout.item_rental_list, parent, false)

        return RentalListAdapter.RentalViewHolder(view)
    }

    override fun getItemCount(): Int {
        return rentalList.size
    }

    override fun onBindViewHolder(holder: RentalViewHolder, position: Int) {
        holder.view.rental = rentalList[position]
    }

    fun updateRentalList(newRentalList: List<Rental>){
        rentalList.clear()
        rentalList.addAll(newRentalList)
        notifyDataSetChanged()
    }
}