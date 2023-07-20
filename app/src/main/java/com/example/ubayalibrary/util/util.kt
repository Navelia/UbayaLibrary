package com.example.ubayalibrary.util

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.ubayalibrary.R
import com.example.ubayalibrary.model.LibraryDatabase
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

@BindingAdapter("android:imageUrl", "android:progressBar")
fun loadImageFromUrl(view: ImageView, url:String?, pb: ProgressBar){
    view.loadImage(url, pb)
}

fun ImageView.loadImage(url: String?, progressBar: ProgressBar){
    Picasso.get()
        .load(url)
        .resize(200,300)
        .centerCrop()
        .error(R.drawable.ic_baseline_error_24)
        .into(this, object: Callback {
            override fun onSuccess() {
                progressBar.visibility = View.GONE
            }

            override fun onError(e: Exception?) {
                Log.d("picasso", e.toString())
            }
        })
}

// Database Code Bellow (Build and Migration)
val DB_NAME = "ubaya_library"

fun buildDB(context: Context): LibraryDatabase {
    val db = Room.databaseBuilder(context, LibraryDatabase::class.java, DB_NAME)
        .fallbackToDestructiveMigration()
        .addMigrations(MIGRATION_1_2)
        .addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(database: SupportSQLiteDatabase) {
                super.onCreate(database)
                populateSeedData(database)
            }
        })
        .build()

    return db
}

val MIGRATION_1_2 = object : Migration(1,2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE 'User' ('uuid' INTEGER, 'nrp' TEXT, 'nama' TEXT, 'password' TEXT, 'photoUrl' TEXT, PRIMARY KEY('uuid'))"
        )
        database.execSQL(
            "CREATE TABLE 'Book' ('uuid' INTEGER, 'judul' TEXT, 'penulis' TEXT, 'tahun' TEXT, 'sinopsis' TEXT, 'photoUrl' TEXT, PRIMARY KEY('uuid'))"
        )
        database.execSQL(
            "CREATE TABLE 'Journal' ('uuid' INTEGER, 'judul' TEXT, 'penulis' TEXT, 'tahun' TEXT, 'abstrak' TEXT, 'letak' TEXT, 'kataKunci' TEXT, PRIMARY KEY('uuid'))"
        )
        database.execSQL(
            "CREATE TABLE 'Rental' ('uuid' INTEGER, 'bookId' TEXT, 'namaBuku' TEXT, 'userNrp' TEXT, 'tanggalSewa' TEXT, 'tanggalPengembalian' TEXT, PRIMARY KEY('uuid'))"
        )
    }
}

private fun populateSeedData(database: SupportSQLiteDatabase) {
//    User Seeder
    database.execSQL("INSERT INTO 'User' (uuid, nrp, nama, password, photoUrl) VALUES (1, '160420046', 'Theofilus Arifin', 'anmp1234', 'https://my.ubaya.ac.id/img/mhs/160420046_l.jpg')")
    database.execSQL("INSERT INTO 'User' (uuid, nrp, nama, password, photoUrl) VALUES (2, '160420023', 'Jonathan Ryan', 'anmp1234', 'https://my.ubaya.ac.id/img/mhs/160420023_l.jpg')")
    database.execSQL("INSERT INTO 'User' (uuid, nrp, nama, password, photoUrl) VALUES (3, '160420003', 'Vincentius Christian', 'anmp1234', 'https://my.ubaya.ac.id/img/mhs/160420003_l.jpg')")

//    Book Seeder
    database.execSQL("INSERT INTO 'Book' (uuid, judul, penulis, tahun, sinopsis, photoUrl) VALUES (1, 'To Kill a Mockingbird', 'Harper Lee', '1960', 'To Kill a Mockingbird is a novel by Harper Lee published in 1960. It was immediately successful, winning the Pulitzer Prize, and has become a classic of modern American literature.', 'https://picsum.photos/id/30/200/300')")
    database.execSQL("INSERT INTO 'Book' (uuid, judul, penulis, tahun, sinopsis, photoUrl) VALUES (2, '1984', 'George Orwell', '1949', '1984 is a dystopian novella by George Orwell published in 1949. The novel is set in Airstrip One, a world of perpetual war, omnipresent government surveillance, and public manipulation.', 'https://picsum.photos/id/32/200/300')")
    database.execSQL("INSERT INTO 'Book' (uuid, judul, penulis, tahun, sinopsis, photoUrl) VALUES (3, 'Pride and Prejudice', 'Jane Austen', '1813', 'Pride and Prejudice is a romantic novel of manners written by Jane Austen in 1813. The story follows the main character, Elizabeth Bennet, as she deals with issues of manners, upbringing, morality, education, and marriage in the society of the landed gentry of the British Regency.', 'https://picsum.photos/id/33/200/300')")
    database.execSQL("INSERT INTO 'Book' (uuid, judul, penulis, tahun, sinopsis, photoUrl) VALUES (4, 'The Great Gatsby', 'F. Scott Fitzgerald', '1925', 'The Great Gatsby is a 1925 novel by F. Scott Fitzgerald. Set in the Jazz Age on Long Island, the novel depicts narrator Nick Carraway''s interactions with mysterious millionaire Jay Gatsby and Gatsby''s obsession to reunite with his former lover, Daisy Buchanan.', 'https://picsum.photos/id/35/200/300')")
    database.execSQL("INSERT INTO 'Book' (uuid, judul, penulis, tahun, sinopsis, photoUrl) VALUES (5, 'To the Lighthouse', 'Virginia Woolf', '1927', 'To the Lighthouse is a novel by Virginia Woolf published in 1927. The novel is set on the Isle of Skye and follows the Ramsay family and their guests over a decade as they navigate their relationships and grapple with the passage of time.', 'https://picsum.photos/id/37/200/300')")
    database.execSQL("INSERT INTO 'Book' (uuid, judul, penulis, tahun, sinopsis, photoUrl) VALUES (6, 'Moby-Dick', 'Herman Melville', '1851', 'Moby-Dick is a novel by Herman Melville published in 1851. It follows the story of Ishmael, a young sailor, and his obsession with the white whale Moby Dick and the captain of the whaling ship Pequod, Captain Ahab.', 'https://picsum.photos/id/38/200/300')")
    database.execSQL("INSERT INTO 'Book' (uuid, judul, penulis, tahun, sinopsis, photoUrl) VALUES (7, 'The Catcher in the Rye', 'J.D. Salinger', '1951', 'The Catcher in the Rye is a novel by J.D. Salinger published in 1951. It is a story narrated by Holden Caulfield, a teenager from New York City, who is undergoing treatment in a mental institution. The novel explores themes of teenage angst, alienation, and identity.', 'https://picsum.photos/id/39/200/300')")
    database.execSQL("INSERT INTO 'Book' (uuid, judul, penulis, tahun, sinopsis, photoUrl) VALUES (8, 'Brave New World', 'Aldous Huxley', '1932', 'Brave New World is a dystopian novel by Aldous Huxley published in 1932. The novel is set in a futuristic World State, where people are genetically engineered and conditioned to conform to a rigid social hierarchy.', 'https://picsum.photos/id/43/200/300')")
//    Journal Seeder
    database.execSQL("INSERT INTO 'Journal' (uuid, judul, penulis, tahun, abstrak, letak, kataKunci) VALUES (1, 'Nature', 'John Smith', '2021', 'Nature is a scientific journal published since 1869. It covers a wide range of scientific disciplines, including biology, chemistry, physics, and earth sciences.', 'Library Section A', 'science, research, biology')")
    database.execSQL("INSERT INTO 'Journal' (uuid, judul, penulis, tahun, abstrak, letak, kataKunci) VALUES (2, 'Science', 'Jane Johnson', '2022', 'Science is a leading scientific journal published since 1880. It publishes research articles across various scientific fields, including biology, chemistry, physics, and social sciences.', 'Library Section B', 'science, research, interdisciplinary')")
    database.execSQL("INSERT INTO 'Journal' (uuid, judul, penulis, tahun, abstrak, letak, kataKunci) VALUES (3, 'The Lancet', 'Robert Williams', '2023', 'The Lancet is a medical journal published since 1823. It publishes original research, reviews, and news articles on various medical topics and global health issues.', 'Library Section C', 'medical, health, research')")
    database.execSQL("INSERT INTO 'Journal' (uuid, judul, penulis, tahun, abstrak, letak, kataKunci) VALUES (4, 'Nature Communications', 'Emily Davis', '2024', 'Nature Communications is a multidisciplinary open-access journal published by Nature Research since 2010. It covers a broad range of scientific disciplines and publishes high-quality research articles.', 'Library Section D', 'science, research, multidisciplinary')")
    database.execSQL("INSERT INTO 'Journal' (uuid, judul, penulis, tahun, abstrak, letak, kataKunci) VALUES (5, 'Cell', 'Michael Brown', '2025', 'Cell is a scientific journal published since 1974. It publishes research articles in the field of molecular biology, biochemistry, genetics, and cell biology.', 'Library Section E', 'science, research, molecular biology')")
    database.execSQL("INSERT INTO 'Journal' (uuid, judul, penulis, tahun, abstrak, letak, kataKunci) VALUES (6, 'New England Journal of Medicine', 'Sarah Thompson', '2026', 'The New England Journal of Medicine is a medical journal published since 1812. It publishes clinical research and review articles on various medical specialties.', 'Library Section F', 'medical, health, research')")
    database.execSQL("INSERT INTO 'Journal' (uuid, judul, penulis, tahun, abstrak, letak, kataKunci) VALUES (7, 'Nature Medicine', 'David Wilson', '2027', 'Nature Medicine is a medical research journal published since 1995. It covers biomedical research, drug discovery, and clinical practice in the field of medicine.', 'Library Section G', 'medical, health, biomedical research')")
    database.execSQL("INSERT INTO 'Journal' (uuid, judul, penulis, tahun, abstrak, letak, kataKunci) VALUES (8, 'Journal of the American Chemical Society', 'Jennifer Anderson', '2028', 'The Journal of the American Chemical Society is a scientific journal published since 1879. It publishes research articles in the field of chemistry and related disciplines.', 'Library Section H', 'chemistry, research, scientific journal')")

//    Rental Seeder
    database.execSQL("INSERT INTO 'Rental' (uuid, bookId, namaBuku, userNrp, tanggalSewa, tanggalPengembalian) VALUES (1, '1', 'Test', '160420023', '20-07-2023', '23-07-2023')")
}