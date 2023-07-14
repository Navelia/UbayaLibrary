package com.example.ubayalibrary.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Rental(
    var id:String?,
    var book_id:String?,
//    Nama buku taruh sini supaya ga usah join ke entitas buku
    var nama_buku:String?,
    var user_nrp:String?,
    var tanggal_sewa:String?,
    var tanggal_pengembalian:String?,
) {
    @PrimaryKey(autoGenerate = true)
    var uuid:Int = 0
}