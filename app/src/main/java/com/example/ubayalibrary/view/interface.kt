package com.example.ubayalibrary.view

import android.view.View
import com.example.ubayalibrary.model.User

interface BookItemInterface{
    fun onBookDetailClick(v: View)
}

interface BookDetailInterface{
    fun onBookRentClick(v: View)
}

interface JournalItemInterface{
    fun onJournalDetailClick(v: View)
}

interface ProfileInterface{
    fun onProfileEditClick(v : View)
}

interface ProfileEditInterface{
    fun onProfileSaveClick(v : View, user: User)
}