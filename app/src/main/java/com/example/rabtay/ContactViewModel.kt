package com.example.rabtay

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var repository: ContactRepository
    lateinit var fetchContacts: LiveData<List<Contact>>

    init {
        val contactDao = ContactRoomDatabase.getDatabase(application).contactDao()
        repository = ContactRepository(contactDao)
        fetchContacts = repository.fetchContacts()
    }


    fun insertContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertContact(contact)
        }
    }

    fun insertContacts(contactsList: List<Contact>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertContacts(contactsList)
        }
    }

}