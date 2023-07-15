package com.example.ubayalibrary.util

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.ubayalibrary.R
import com.example.ubayalibrary.model.LibraryDatabase
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

@BindingAdapter("imageUrl", "progressBar")
fun loadImageFromUrl(view: ImageView, url:String?, pb: ProgressBar){
    view.loadImage(url, pb)
}

fun ImageView.loadImage(url: String?, progressBar: ProgressBar){
    Picasso.get()
        .load(url)
        .resize(400,500)
        .centerCrop()
        .error(R.drawable.ic_baseline_error_24)
        .into(this, object: Callback {
            override fun onSuccess() {
                progressBar.visibility = View.GONE
            }

            override fun onError(e: Exception?) {

            }
        })
}

// Database Code Bellow (Build and Migration)
val DB_NAME = "ubaya_library"

fun buildDB(context: Context): LibraryDatabase {
    val db = Room.databaseBuilder(context, LibraryDatabase::class.java, DB_NAME)
        .fallbackToDestructiveMigration()
        .addMigrations(MIGRATION_1_2)
        .build()

    return db
}

val MIGRATION_1_2 = object : Migration(1,2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE 'User' ('uuid' INTEGER, 'nrp' INTEGER, 'nama' TEXT, 'password' TEXT, 'photoUrl' TEXT, PRIMARY KEY('uuid'))"
        )
        database.execSQL(
            "CREATE TABLE 'Book' ('uuid' INTEGER, 'judul' TEXT, 'penulis' TEXT, 'tahun' INTEGER, 'sinopsis' TEXT, 'photoUrl' TEXT, PRIMARY KEY('uuid'))"
        )
        database.execSQL(
            "CREATE TABLE 'Journal' ('uuid' INTEGER, 'judul' TEXT, 'penulis' TEXT, 'tahun' INTEGER, 'abstrak' TEXT, 'letak' TEXT, 'photoUrl' TEXT, PRIMARY KEY('uuid'))"
        )
        database.execSQL(
            "CREATE TABLE 'Rental' ('uuid' INTEGER, 'bookId' INTEGER, 'namaBuku' TEXT, 'userNrp' INTEGER, 'tanggalSewa' TEXT, 'tanggalPengembalian' TEXT, PRIMARY KEY('uuid'))"
        )
    }
}