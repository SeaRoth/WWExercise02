package com.searoth.ww_exercise_02.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.searoth.ww_exercise_02.R
import com.searoth.ww_exercise_02.model.Contact
import kotlinx.android.synthetic.main.activity_detail_contact.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_contact)

        val mContact = intent.getParcelableExtra<Contact>("Contact")
        if(mContact != null) updateUI(mContact)

        save_button.setOnClickListener {
            val name = name_edit_text.text.toString()
            val number = number_edit_text.text.toString()

            if(name.isEmpty())
                Snackbar.make(it, getString(R.string.error_name), Snackbar.LENGTH_SHORT).show()
            else if(number.isNotEmpty() && Contact.validateNumber(number)){
                val data = intent
                if(newOrEditContact == MainActivity.ACTION_EDIT_CONTACT){
                    mContact.name = name
                    mContact.number = number
                    data.putExtra("Contact", mContact)
                }else {
                    data.putExtra("name", name)
                    data.putExtra("number", number)
                }
                setResult(newOrEditContact, data)
                finish()
            }else
                Snackbar.make(it, getString(R.string.error_number), Snackbar.LENGTH_SHORT).show()
        }

        cancel_button.setOnClickListener {
            cancel()
        }

        delete_button.setOnClickListener {
            if(newOrEditContact == MainActivity.ACTION_EDIT_CONTACT){
                val data = intent
                if(mContact != null)
                    data.putExtra("id", mContact.id)
                setResult(MainActivity.ACTION_DEL_CONTACT, data)
                finish()
            }else{
                cancel()
            }
        }
    }

    private fun cancel(){
        setResult(MainActivity.ACTION_CANCEL)
        finish()
    }

    var newOrEditContact = MainActivity.ACTION_NEW_CONTACT
    private fun updateUI(contact: Contact){
        newOrEditContact = MainActivity.ACTION_EDIT_CONTACT
        name_edit_text.setText(contact.name)
        number_edit_text.setText(contact.number)
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}
