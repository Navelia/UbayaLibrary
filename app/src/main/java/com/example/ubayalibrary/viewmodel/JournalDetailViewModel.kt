package com.example.ubayalibrary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.ubayalibrary.model.Journal
import com.example.ubayalibrary.util.buildDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class JournalDetailViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    private val job = Job()
    val journalLD = MutableLiveData<Journal>()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun fetch(id:Int){
        launch {
            val db = buildDB(getApplication())
            journalLD.postValue(db.journalDao().selectJournal(id))
        }
    }

}