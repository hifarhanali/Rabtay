package com.example.rabtay

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: Contact)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(contacts: List<Contact>)

    @Delete
    suspend fun delete(vararg contact: Contact)

    @Query("SELECT * FROM contact")
    fun fetchAll() : LiveData<List<Contact>>

    @Query("SELECT * FROM contact WHERE LOWER(name) LIKE LOWER(:query) OR number LIKE :query")
    fun search(query: String) : LiveData<List<Contact>>
}