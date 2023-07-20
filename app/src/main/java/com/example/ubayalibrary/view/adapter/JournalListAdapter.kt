package com.example.ubayalibrary.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.ubayalibrary.R
import com.example.ubayalibrary.databinding.ItemJournalListBinding
import com.example.ubayalibrary.model.Journal
import com.example.ubayalibrary.view.JournalItemInterface
import com.example.ubayalibrary.view.fragment.JournalListFragmentDirections

class JournalListAdapter (val journalList: ArrayList<Journal>): RecyclerView.Adapter<JournalListAdapter.JournalViewHolder>(), JournalItemInterface {
    class JournalViewHolder(var view: ItemJournalListBinding): RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val view = DataBindingUtil.inflate<ItemJournalListBinding>(inflater, R.layout.item_journal_list, parent, false)

        return JournalViewHolder(view)
    }

    override fun getItemCount(): Int {
        return journalList.size
    }

    override fun onBindViewHolder(holder: JournalViewHolder, position: Int) {
        holder.view.journal = journalList[position]
    }

    override fun onJournalDetailClick(v: View) {
        val uuid = v.tag.toString().toInt()
        val action = JournalListFragmentDirections.actionToJournalDetail(uuid)

        Navigation.findNavController(v).navigate(action)
    }

    fun updateJournalList(newJournalList : List<Journal>){
        journalList.clear()
        journalList.addAll(newJournalList)
    }
}