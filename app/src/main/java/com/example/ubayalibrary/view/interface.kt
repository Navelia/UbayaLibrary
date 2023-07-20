package com.example.ubayalibrary.view

import android.view.View

interface BookItemInterface{
    fun onBookDetailClick(v: View)
}

interface BookDetailInterface{
    fun onBookRentClick(v: View)
}