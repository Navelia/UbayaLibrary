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
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.ubayalibrary.R
import com.example.ubayalibrary.view.MainActivity
import com.example.ubayalibrary.view.adapter.BookListAdapter
import com.example.ubayalibrary.viewmodel.BookListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BookListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_list, container, false)
    }

    private lateinit var viewModel:BookListViewModel
    private val bookListAdapter = BookListAdapter(arrayListOf())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //        Menghilangkan Back supaya tidak ke login
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        viewModel = ViewModelProvider(this).get(BookListViewModel::class.java)
        viewModel.refresh()

        var recViewBook = view.findViewById<RecyclerView>(R.id.recViewBookList)
        recViewBook.layoutManager = LinearLayoutManager(context)
        recViewBook.adapter = bookListAdapter

        view.findViewById<FloatingActionButton>(R.id.fabSeeHistory).setOnClickListener{
            val action = BookListFragmentDirections.actionToHistoryBook()
            Navigation.findNavController(it).navigate(action)
        }

        view.findViewById<SwipeRefreshLayout>(R.id.refreshLayoutBookList).setOnRefreshListener{
            view.findViewById<RecyclerView>(R.id.recViewBookList).visibility = View.GONE
            view.findViewById<TextView>(R.id.txtErrorBookList).visibility = View.GONE
            view.findViewById<ProgressBar>(R.id.progressLoadBookList).visibility = View.VISIBLE
            val spinner = view.findViewById<Spinner>(R.id.spinnerFilterBook)
            spinner.setSelection(0)
            view.findViewById<EditText>(R.id.txtKeywordBook).setText("")
            viewModel.filter("", "")
            viewModel.refresh()
            view.findViewById<SwipeRefreshLayout>(R.id.refreshLayoutBookList).isRefreshing = false
        }

        observeViewModel(view)

        val filter = resources.getStringArray(R.array.BookFilter)
        val spinner = view.findViewById<Spinner>(R.id.spinnerFilterBook)
        val adapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, filter) }
        spinner.adapter = adapter

        view.findViewById<Button>(R.id.btnSearchBook).setOnClickListener{
            val spinner = view.findViewById<Spinner>(R.id.spinnerFilterBook)
            val filter = spinner.selectedItem.toString()
            val keyword = view.findViewById<EditText>(R.id.txtKeywordBook).text.toString()
            viewModel.filter(filter, keyword)
            viewModel.refresh()
        }
    }

    fun observeViewModel(view: View){
        viewModel.bookLD.observe(viewLifecycleOwner, Observer{
            bookListAdapter.updateBookList(it)
        })
        viewModel.bookLoadErrorLD.observe(viewLifecycleOwner, Observer{
            if(it == true){
                view.findViewById<TextView>(R.id.txtErrorBookList).visibility = View.VISIBLE
            }
            else{
                view.findViewById<TextView>(R.id.txtErrorBookList).visibility = View.GONE
            }
        })
        viewModel.loadingLD.observe(viewLifecycleOwner, Observer{
            if(it == true){
                view.findViewById<RecyclerView>(R.id.recViewBookList).visibility = View.GONE
                view.findViewById<ProgressBar>(R.id.progressLoadBookList).visibility = View.VISIBLE
            }
            else{
                view.findViewById<RecyclerView>(R.id.recViewBookList).visibility = View.VISIBLE
                view.findViewById<ProgressBar>(R.id.progressLoadBookList).visibility = View.GONE
            }
        })
    }
}