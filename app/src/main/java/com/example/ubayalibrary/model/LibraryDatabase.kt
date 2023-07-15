package com.example.ubayalibrary.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ubayalibrary.util.MIGRATION_1_2

@Database(entities = arrayOf(User::class, Book::class, Rental::class, Journal::class), version = 1)
abstract class LibraryDatabase:RoomDatabase() {
//    Database here
    abstract fun userDao():UserDao
    abstract fun bookDao():BookDao
    abstract fun rentalDao():RentalDao
    abstract fun journalDao():JournalDao

    companion object {
        @Volatile private var instance: LibraryDatabase ?= null
        private val LOCK = Any()

        private fun buildDatabase(context : Context) = Room.databaseBuilder(
            context.applicationContext,
            LibraryDatabase::class.java,
            "ubaya_library"
        ).fallbackToDestructiveMigration()
            .addMigrations(MIGRATION_1_2)
            .build()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }
    }
}