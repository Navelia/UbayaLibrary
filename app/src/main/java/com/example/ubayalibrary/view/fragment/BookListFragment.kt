package com.example.ubayalibrary.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ubayalibrary.R
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

        viewModel = ViewModelProvider(this).get(BookListViewModel::class.java)
        viewModel.refresh()

        var recViewBook = view.findViewById<RecyclerView>(R.id.recViewBookList)
        recViewBook.layoutManager = LinearLayoutManager(context)
        recViewBook.adapter = bookListAdapter

        view.findViewById<FloatingActionButton>(R.id.fabSeeHistory).setOnClickListener{
            val action = BookListFragmentDirections.actionHistoryBook()
            Navigation.findNavController(it).navigate(action)
        }

        observeViewModel()
    }

    fun observeViewModel(){
        viewModel.bookLD.observe(viewLifecycleOwner, Observer{
            bookListAdapter.updateTodoList(it)
        })
    }
}