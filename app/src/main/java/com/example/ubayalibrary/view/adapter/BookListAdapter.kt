package com.example.ubayalibrary.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ubayalibrary.databinding.ItemBookListBinding
import com.example.ubayalibrary.model.Book
import com.example.ubayalibrary.view.BookItemInterface
import com.example.ubayalibrary.view.fragment.BookListFragmentDirections

class BookListAdapter(val bookList: ArrayList<Book>):RecyclerView.Adapter<BookListAdapter.BookViewHolder>(), BookItemInterface {
    class BookViewHolder(var view: ItemBookListBinding): RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val view = ItemBookListBinding.inflate(inflater, parent, false)

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
    }

    fun updateTodoList(newBookList: List<Book>){
        bookList.clear()
        bookList.addAll(newBookList)
        notifyDataSetChanged()
    }
}