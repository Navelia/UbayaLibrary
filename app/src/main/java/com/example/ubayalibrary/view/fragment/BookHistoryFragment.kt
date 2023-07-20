package com.example.ubayalibrary.view.fragment

import android.content.Context
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
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.ubayalibrary.R
import com.example.ubayalibrary.view.MainActivity
import com.example.ubayalibrary.view.adapter.RentalListAdapter
import com.example.ubayalibrary.viewmodel.BookListViewModel
import com.example.ubayalibrary.viewmodel.RentalListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BookHistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_history, container, false)
    }

    private lateinit var viewModel: RentalListViewModel
    private var rentalListAdapter = RentalListAdapter(arrayListOf())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = this.activity?.getSharedPreferences("Preferences", Context.MODE_PRIVATE)
        val nrp = sharedPref?.getString("nrp","")

        viewModel = ViewModelProvider(this).get(RentalListViewModel::class.java)
        nrp?.let { viewModel.refresh(it) }

        var recViewBook = view.findViewById<RecyclerView>(R.id.recViewBookHistory)
        recViewBook.layoutManager = LinearLayoutManager(context)
        recViewBook.adapter = rentalListAdapter

        observeViewModel(view)
    }

    fun observeViewModel(view: View){
        viewModel.rentalLD.observe(viewLifecycleOwner, Observer{
            rentalListAdapter.updateRentalList(it)
        })
        viewModel.rentalLoadErrorLD.observe(viewLifecycleOwner, Observer{
            if(it == true){
                view.findViewById<TextView>(R.id.txtErrorBookHistory).visibility = View.VISIBLE
            }
            else{
                view.findViewById<TextView>(R.id.txtErrorBookHistory).visibility = View.GONE
            }
        })
        viewModel.loadingLD.observe(viewLifecycleOwner, Observer{
            if(it == true){
                view.findViewById<RecyclerView>(R.id.recViewBookHistory).visibility = View.GONE
                view.findViewById<ProgressBar>(R.id.progressLoadBookHistory).visibility = View.VISIBLE
            }
            else{
                view.findViewById<RecyclerView>(R.id.recViewBookHistory).visibility = View.VISIBLE
                view.findViewById<ProgressBar>(R.id.progressLoadBookHistory).visibility = View.GONE
            }
        })
    }
}