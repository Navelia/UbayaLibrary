package com.example.ubayalibrary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.ubayalibrary.model.Book
import com.example.ubayalibrary.model.Rental
import com.example.ubayalibrary.util.buildDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class BookDetailViewModel(application: Application): AndroidViewModel(application), CoroutineScope {
    private val job = Job()
    val bookLD = MutableLiveData<Book>()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun fetch(id:Int){
        launch {
            val db = buildDB(getApplication())
            bookLD.postValue(db.bookDao().selectBook(id))
        }
    }

    fun rent(list:List<Rental>){
        launch {
            val db = buildDB(getApplication())
            db.rentalDao().insertAll(*list.toTypedArray())
        }
    }
}