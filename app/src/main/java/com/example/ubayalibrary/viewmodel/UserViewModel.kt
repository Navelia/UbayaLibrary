package com.example.ubayalibrary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.ubayalibrary.model.User
import com.example.ubayalibrary.util.buildDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class UserViewModel(application: Application): AndroidViewModel(application), CoroutineScope {
    val usersLD = MutableLiveData<User>()
    val userLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    private val job = Job()

    fun addUser(list:List<User>) {
        launch {
            val db = buildDB(getApplication())
            db.userDao().insertAll(*list.toTypedArray())
        }
    }

    fun registerUser(user: User) {
        launch {
            try {
                val db = buildDB(getApplication())
                db.userDao().insertAll(user)
                userLoadErrorLD.postValue(false)
            } catch (e: Exception) {
                userLoadErrorLD.postValue(true)
            }
        }
    }

    fun selectUser(nrp: String, password: String) {
        launch {
            try {
                val db = buildDB(getApplication())
                usersLD.value = db.userDao().selectUser(nrp, password)
            } catch (e: Exception) {
                userLoadErrorLD.postValue(true)
            }
        }
    }

    fun showProfile(nrp:String){
        launch {
            val db = buildDB(getApplication())
            usersLD.postValue(db.userDao().selectUser1(nrp))
        }
    }

    fun update(nama: String, password: String, photoUrl: String, nrp: String){
        launch {
            val db = buildDB(getApplication())
            db.userDao().updateUser(nama, password , photoUrl, nrp)
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
}
