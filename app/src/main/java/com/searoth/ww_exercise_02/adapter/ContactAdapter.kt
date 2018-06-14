package com.searoth.ww_exercise_02.adapter

import android.content.Context
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.searoth.ww_exercise_02.R
import com.searoth.ww_exercise_02.model.Contact
import kotlinx.android.synthetic.main.layout_contact.view.*

class ContactAdapter (private val context: Context) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    var onItemClick: ((Contact) -> Unit)? = null
    var onItemLongClick: ((Contact) -> Unit)? = null
    var contacts: ArrayList<Contact> = ArrayList<Contact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_contact, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = contacts[position]

        val secondInitial = when(contact.name.length > 1){
            true -> contact.name[1].toString()
            false -> ""
        }

        if(position%2 == 0)
            holder.layout.setBackgroundColor(Color.TRANSPARENT)
        else
            holder.layout.setBackgroundColor(Color.LTGRAY)

        holder.index.text = context.getString(R.string.two_string_placeholder,contact.name[0].toString(),secondInitial)
        holder.contactName.text = contact.name
        holder.contactNumber.text = contact.number
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    fun getList(): ArrayList<Contact> {
        return contacts
    }

    fun setList(list: ArrayList<Contact>){
        contacts.clear()
        contacts.addAll(list)
        notifyDataSetChanged()
    }

    fun add(name: String, number: String) {
        val contact = Contact(contacts.size, name,number)
        contacts.add(contact)
        notifyDataSetChanged()
    }

    fun update(contact: Contact){
        contacts[contact.id] = contact
        notifyDataSetChanged()
    }

    fun clear(){
        contacts.clear()
        notifyDataSetChanged()
    }

    fun remove(pos: Int){
        contacts.removeAt(pos)
        notifyDataSetChanged()
    }

    fun remove(contact: Contact){
        contacts.remove(contact)
        notifyDataSetChanged()
    }

    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val layout: ConstraintLayout = view.cl_contact
        val index: TextView = view.contact_number
        val contactName: TextView = view.contact_name
        val contactNumber: TextView = view.contact_phone_number

        init {

            view.setOnLongClickListener {
                onItemLongClick?.invoke(contacts[adapterPosition])
                true
            }

            view.setOnClickListener{
                onItemClick?.invoke(contacts[adapterPosition])
            }
        }
    }

}