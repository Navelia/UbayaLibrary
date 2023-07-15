package com.example.ubayalibrary.model

import androidx.room.*

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg book:Book)

    @Query("SELECT * FROM Book")
    suspend fun selectAllBook(): List<Book>

    @Query("SELECT * FROM Book WHERE uuid = :id")
    suspend fun selectBook(id:Int):Book

    @Query("SELECT * FROM Book WHERE judul LIKE '%' || :judul || '%'")
    suspend fun filterBookByName(judul: String): Book

    @Query("SELECT * FROM Book WHERE penulis LIKE '%' || :penulis || '%'")
    suspend fun filterBookByAuthor(penulis: String): Book

    @Query("UPDATE Book SET id = :id, judul = :judul, penulis = :penulis, tahun = :tahun, sinopsis = :sinopsis, photoUrl = :photoUrl WHERE uuid = :uuid")
    suspend fun updateBook(id: Int, judul: String, penulis: String, tahun: Int, sinopsis: String, photoUrl: String, uuid: Int)

    @Delete
    suspend fun deleteBook(book:Book)
}

@Dao
interface JournalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg journal:Journal)

    @Query("SELECT * FROM Journal")
    suspend fun selectAllJournal(): List<Journal>

    @Query("SELECT * FROM Journal WHERE uuid = :id")
    suspend fun selectJournal(id:Int):Journal

    @Query("SELECT * FROM Journal WHERE judul LIKE '%' || :judul || '%'")
    suspend fun filterJournalByName(judul: String): Journal

    @Query("SELECT * FROM Journal WHERE penulis LIKE '%' || :penulis || '%'")
    suspend fun filterJournalByAuthor(penulis: String): Journal

    @Query("UPDATE Journal SET id = :id, judul = :judul, penulis = :penulis, tahun = :tahun, abstrak = :abstrak, letak = :letak, kataKunci = :kataKunci WHERE uuid = :uuid")
    suspend fun updateJournal(id: Int, judul: String, penulis: String, tahun: Int, abstrak: String, letak :String, kataKunci: String, uuid: Int)

    @Delete
    suspend fun deleteJournal(journal:Journal)
}

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg user: User)

    @Query("SELECT * FROM User WHERE nrp= :nrp AND password= :password")
    suspend fun selectUser(nrp:Int, password:String):User

    @Query("UPDATE User SET nama= :nama, password= :password, photoUrl= :photoUrl  WHERE nrp = :nrp")
    suspend fun updateUser(nama: String, password: String, photoUrl: String, nrp: Int)

    @Delete
    suspend fun deleteUser(user:User)
}

@Dao
interface RentalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg rental: Rental)

    @Query("SELECT * FROM Rental WHERE userNrp= :userNrp")
    suspend fun selectRental(userNrp:Int):Rental

    @Delete
    suspend fun deleteRental(user:Rental)
}