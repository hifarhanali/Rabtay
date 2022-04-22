package com.example.rabtay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val permissionRequester = PermissionRequester(this)
    private lateinit var contactsList: List<Contact>
    private lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        contactViewModel = ViewModelProvider(this)[ContactViewModel::class.java]

        if (permissionRequester.hasReadContactsPermission()) {
            // read contacts from the content provider
            contactsList = readContacts()

            // insert contacts in the database
            contactViewModel.insertContacts(contactsList)

            contactViewModel.fetchContacts.observe(this, Observer {
                contactsList = it
            })

            val contactsListRecyclerView =
                this.findViewById<RecyclerView>(R.id.contacts_list_recyclerview)
            contactsListRecyclerView.adapter = ContactsAdaptor(contactsList)
            contactsListRecyclerView.layoutManager = LinearLayoutManager(this)

        } else {
            permissionRequester.requestReadContactsPermission()
        }
    }


    // read contacts from the Contact Content Provider
    private fun readContacts() = ContactContentResolver(this.contentResolver).fetch()


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (!permissionRequester.onRequestPermissionsResult(
                requestCode,
                grantResults,
                ::readContacts
            )
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}