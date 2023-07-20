package com.example.ubayalibrary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.ubayalibrary.model.Rental
import com.example.ubayalibrary.util.buildDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RentalListViewModel(application: Application): AndroidViewModel(application), CoroutineScope {
    val rentalLD = MutableLiveData<List<Rental>>()
    val rentalLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun refresh(userNRP: String){
        loadingLD.value = true
        rentalLoadErrorLD.value = false
        launch {
            val db = buildDB(getApplication())
            rentalLD.postValue(db.rentalDao().selectRental(userNRP))
        }
        loadingLD.value = false
    }
}