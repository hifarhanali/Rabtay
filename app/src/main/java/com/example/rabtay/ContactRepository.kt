package com.example.rabtay

import androidx.lifecycle.LiveData
import kotlinx.coroutines.*

class ContactRepository(private val contactDao: ContactDao) {

    suspend fun insertContact(contact: Contact) = contactDao.insert(contact)

    suspend fun insertContacts(contactsList: List<Contact>) = contactDao.insertAll(contactsList)

    fun fetchContacts(): LiveData<List<Contact>> = contactDao.fetchAll()

    fun searchContacts(query: String): LiveData<List<Contact>> = contactDao.search(query)
}