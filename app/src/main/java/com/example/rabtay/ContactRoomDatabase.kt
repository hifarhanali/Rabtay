package com.example.rabtay

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Contact::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ContactRoomDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

    companion object {
        @Volatile
        private var INSTANCE: ContactRoomDatabase? = null

        fun getDatabase(context: Context): ContactRoomDatabase {
            // if INSTANCE exists, return it.
            // Otherwise, create an instance and return it.
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        // build database and return an instance
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ContactRoomDatabase::class.java,
            "contact_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}