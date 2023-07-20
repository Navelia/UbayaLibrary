package com.example.ubayalibrary.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.ubayalibrary.R
import com.example.ubayalibrary.view.MainActivity
import com.example.ubayalibrary.view.adapter.JournalListAdapter
import com.example.ubayalibrary.viewmodel.JournalViewModel

class JournalListFragment : Fragment() {
    private lateinit var journalViewModel:JournalViewModel
    private val journalListAdapter = JournalListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_journal_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        Menghilangkan Back supaya tidak ke login
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        journalViewModel = ViewModelProvider(this).get(JournalViewModel::class.java)
        journalViewModel.refresh()

        var recViewJournal = view.findViewById<RecyclerView>(R.id.recViewJournalList)
        recViewJournal.layoutManager = LinearLayoutManager(context)
        recViewJournal.adapter = journalListAdapter

        val spinnerJournal = view.findViewById<Spinner>(R.id.spinnerFilterJournal)

        var refreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.refreshLayoutJournalList)
        refreshLayout.setOnRefreshListener{
            view.findViewById<RecyclerView>(R.id.recViewJournalList).visibility = View.GONE
            view.findViewById<TextView>(R.id.txtErrorJournalList).visibility = View.GONE
            view.findViewById<ProgressBar>(R.id.progressLoadJournalList).visibility = View.VISIBLE
            spinnerJournal.setSelection(0)
            view.findViewById<EditText>(R.id.txtKeywordJournal).setText("")
            journalViewModel.journalFilter("", "")
            journalViewModel.refresh()
            refreshLayout.isRefreshing = false
        }

        observeViewModel(view)

        val filter = resources.getStringArray(R.array.BookFilter)
        val adapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, filter) }
        spinnerJournal.adapter = adapter

        view.findViewById<Button>(R.id.btnSearchJournal).setOnClickListener{
            val filter = spinnerJournal.selectedItem.toString()
            val keyword = view.findViewById<EditText>(R.id.txtKeywordJournal).text.toString()
            journalViewModel.journalFilter(filter, keyword)
            journalViewModel.refresh()
        }

    }

    fun observeViewModel(view: View){
        journalViewModel.journalLD.observe(viewLifecycleOwner, Observer{
            journalListAdapter.updateJournalList(it)
        })
        journalViewModel.journalLoadErrorLD.observe(viewLifecycleOwner, Observer{
            if(it == true){
                view.findViewById<TextView>(R.id.txtErrorJournalList).visibility = View.VISIBLE
            }
            else{
                view.findViewById<TextView>(R.id.txtErrorJournalList).visibility = View.GONE
            }
        })
        journalViewModel.loadingLD.observe(viewLifecycleOwner, Observer{
            if(it == true){
                view.findViewById<RecyclerView>(R.id.recViewJournalList).visibility = View.GONE
                view.findViewById<ProgressBar>(R.id.progressLoadJournalList).visibility = View.VISIBLE
            }
            else{
                view.findViewById<RecyclerView>(R.id.recViewJournalList).visibility = View.VISIBLE
                view.findViewById<ProgressBar>(R.id.progressLoadJournalList).visibility = View.GONE
            }
        })
    }
}