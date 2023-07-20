package com.example.ubayalibrary.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ubayalibrary.R
import com.example.ubayalibrary.databinding.FragmentBookDetailBinding
import com.example.ubayalibrary.model.Book
import com.example.ubayalibrary.model.Rental
import com.example.ubayalibrary.view.BookDetailInterface
import com.example.ubayalibrary.viewmodel.BookDetailViewModel
import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter

class BookDetailFragment : Fragment(), BookDetailInterface {
    private lateinit var viewModel: BookDetailViewModel
    private lateinit var dataBinding: FragmentBookDetailBinding
    private lateinit var book: Book

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate<FragmentBookDetailBinding>(inflater, R.layout.fragment_book_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(BookDetailViewModel::class.java)

        val id = BookDetailFragmentArgs.fromBundle(requireArguments()).id
        viewModel.fetch(id)
        observeViewModel()
    }

    fun observeViewModel(){
        viewModel.bookLD.observe(viewLifecycleOwner, Observer{
            book = it
            dataBinding.book = book
        })
    }

    override fun onBookRentClick(v: View) {
        val sharedPref = this.activity?.getSharedPreferences("Preferences", Context.MODE_PRIVATE)
        val nrp = sharedPref?.getString("nrp","")

        var bookId = book.uuid
        var namaBuku = book.judul
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val tanggalSewa = LocalDateTime.now().format(formatter)
        var tanggalPengembalian = LocalDateTime.now().plusDays(3).format(formatter)

        var rental = Rental(bookId.toString(), namaBuku, nrp, tanggalSewa, tanggalPengembalian)

        val list = listOf(rental)
        viewModel.rent(list)
    }
}