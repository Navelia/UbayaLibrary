package com.example.ubayalibrary.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(User::class, Book::class, Rental::class, Journal::class), version = 1)
abstract class LibraryDatabase:RoomDatabase() {
//    Database here
}