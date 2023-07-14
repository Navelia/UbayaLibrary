package com.example.ubayalibrary.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book(
    var id:String?,
    var judul:String?,
    var penulis:String?,
    var tahun:String?,
    var sinopsis:String?,
    var photoUrl:String?
) {
    @PrimaryKey(autoGenerate = true)
    var uuid:Int = 0
}