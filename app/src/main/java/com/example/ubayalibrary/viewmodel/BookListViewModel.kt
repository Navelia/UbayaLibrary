package com.example.ubayalibrary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.ubayalibrary.model.Book
import com.example.ubayalibrary.util.buildDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class BookListViewModel(application: Application): AndroidViewModel(application), CoroutineScope {
    val bookLD = MutableLiveData<List<Book>>()
    val bookLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    var filter = ""
    var keyword = ""
    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun filter(filter1: String, keyword1: String){
        filter = filter1
        keyword = keyword1
    }

    fun refresh(){
        loadingLD.value = true
        bookLoadErrorLD.value = false
        if(keyword == ""){
            launch {
                val db = buildDB(getApplication())

                bookLD.postValue(db.bookDao().selectAllBook())
            }
        }
        else{
            if(filter == "Judul"){
                launch {
                    val db = buildDB(getApplication())

                    bookLD.postValue(db.bookDao().filterBookByName(keyword))
                }
            }
            else{
                launch {
                    val db = buildDB(getApplication())

                    bookLD.postValue(db.bookDao().filterBookByAuthor(keyword))
                }
            }
        }
        loadingLD.value = false
    }
}