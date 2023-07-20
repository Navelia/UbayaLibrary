package com.example.ubayalibrary.view.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.ubayalibrary.R
import com.example.ubayalibrary.databinding.FragmentBookDetailBinding
import com.example.ubayalibrary.model.Book
import com.example.ubayalibrary.model.Rental
import com.example.ubayalibrary.util.NotificationHelper
import com.example.ubayalibrary.view.BookDetailInterface
import com.example.ubayalibrary.view.MainActivity
import com.example.ubayalibrary.viewmodel.BookDetailViewModel
import com.example.ubayalibrary.viewmodel.UserViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class BookDetailFragment : Fragment(), BookDetailInterface {
    private lateinit var viewModel: BookDetailViewModel
    private lateinit var dataBinding: FragmentBookDetailBinding
    private lateinit var userViewModel: UserViewModel
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
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        val id = BookDetailFragmentArgs.fromBundle(requireArguments()).id
        viewModel.fetch(id)
        observeViewModel()
        dataBinding.listener = this
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

        var password = ""

        var builder = AlertDialog.Builder(context)
        builder.setTitle("Password Confirmation")

        var input = EditText(context)
        input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        builder.setView(input)

        builder.setPositiveButton("OK") { dialog, which ->
            password = input.text.toString()
            userViewModel.selectUser(nrp!!, password)

            // Observe the LiveData to get the user data
            userViewModel.usersLD.observe(viewLifecycleOwner) { user ->
                if (user != null) {
                    viewModel.rent(list)
                    Toast.makeText(requireContext(), "Rent Success", Toast.LENGTH_SHORT).show()
                    MainActivity.showNotification("Sewa Bukumu Berhasil", "Selamat penyewaan buku " + book.judul + " berhasil. Jangan lupa kembalikan buku pada " + tanggalPengembalian + " ya")
                } else {
                    Toast.makeText(requireContext(), "Invalid password", Toast.LENGTH_SHORT).show()
                }
            }
        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.cancel()
        }

        builder.show()
    }
}