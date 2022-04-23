package com.example.rabtay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val permissionRequester = PermissionRequester(this)
    private lateinit var contactViewModel: ContactViewModel
    private var contactAdapter: ContactAdaptor? = null
    private lateinit var contactsList: List<Contact>
    private lateinit var tempContactsList: List<Contact>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        contactViewModel = ViewModelProvider(this)[ContactViewModel::class.java]
        val contactsListRecyclerView =
            this.findViewById<RecyclerView>(R.id.contacts_list_recyclerview)

        if (permissionRequester.hasReadContactsPermission()) {
            // read contacts from the content provider
            contactsList = readContacts()

            // insert contacts in the database
            contactViewModel.insertContacts(contactsList)

            contactViewModel.fetchContacts.observe(this, Observer {
                contactsList = it
                contactAdapter = ContactAdaptor(contactsList)
                contactsListRecyclerView.adapter = contactAdapter
                contactsListRecyclerView.layoutManager = LinearLayoutManager(this)
            })

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val search = menu?.findItem(R.id.menu_search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = true
            override fun onQueryTextChange(query: String?): Boolean {
                if(query != null && query.isNotEmpty()){
                    searchContacts(query)
                }
                else{
                    contactAdapter?.setData(contactsList)
                }
                return true
            }
        })
        return true
    }

    private fun searchContacts(query: String){
        contactViewModel.searchContacts("%${query}%").observe(this, Observer {
            it.let {
                contactAdapter?.setData(it)
            }
        })
    }
}