package com.example.rabtay

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactsAdaptor(private val contactsList: List<Contact>): RecyclerView.Adapter<ContactsAdaptor.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var contactProfilePhoto: ImageView = itemView.findViewById(R.id.contact_photo_imageview)
        var contactName: TextView = itemView.findViewById(R.id.contact_name_textview)
        var contactNumber: TextView = itemView.findViewById(R.id.contact_number_textview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if(contactsList[position].profilePhoto != null) holder.contactProfilePhoto.setImageBitmap(contactsList[position].profilePhoto)
        holder.contactName.text = contactsList[position].name
        holder.contactNumber.text = contactsList[position].number
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }
}