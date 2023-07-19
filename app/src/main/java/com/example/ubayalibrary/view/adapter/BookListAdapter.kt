package com.example.ubayalibrary.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.ubayalibrary.R
import com.example.ubayalibrary.databinding.ItemBookListBinding
import com.example.ubayalibrary.model.Book
import com.example.ubayalibrary.view.BookItemInterface
import com.example.ubayalibrary.view.fragment.BookListFragmentDirections

class BookListAdapter(val bookList: ArrayList<Book>):RecyclerView.Adapter<BookListAdapter.BookViewHolder>(), BookItemInterface {
    class BookViewHolder(var view: ItemBookListBinding): RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val view = DataBindingUtil.inflate<ItemBookListBinding>(inflater, R.layout.item_book_list, parent, false)

        return BookViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.view.book = bookList[position]
        holder.view.listener = this
    }

    override fun onBookDetailClick(v: View) {
        val uuid = v.tag.toString().toInt()
        val action = BookListFragmentDirections.actionToDetailBook(uuid)

        Navigation.findNavController(v).navigate(action)
    }

    fun updateBookList(newBookList: List<Book>){
        bookList.clear()
        bookList.addAll(newBookList)
        notifyDataSetChanged()
    }
}