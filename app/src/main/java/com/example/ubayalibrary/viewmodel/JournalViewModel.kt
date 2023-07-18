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

class JournalViewModel (application:Application) : AndroidViewModel(application), CoroutineScope {
    val journalLD = MutableLiveData<List<Journal>>()
    val journalLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun refresh(){
        loadingLD.value = true
        journalLoadErrorLD.value = false
        launch {
            val db = buildDB(getApplication())

            journalLD.postValue(db.journalDao().selectAllJournal())
        }
    }
}