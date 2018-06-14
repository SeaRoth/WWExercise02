package com.searoth.ww_exercise_02.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.searoth.ww_exercise_02.R
import com.searoth.ww_exercise_02.adapter.ContactAdapter
import com.searoth.ww_exercise_02.model.Contact
import kotlinx.android.synthetic.main.activity_main.*
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.MenuItem
import com.searoth.ww_exercise_02.R.id.*


class MainActivity : AppCompatActivity() {

    companion object {
        const val ACTION_CANCEL = 120
        const val ACTION_NEW_CONTACT = 121
        const val ACTION_EDIT_CONTACT = 122
        const val ACTION_DEL_CONTACT = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        recycler_view.adapter = ContactAdapter(this)
        recycler_view.layoutManager = LinearLayoutManager(this)

        (recycler_view.adapter as ContactAdapter).onItemClick = { contact ->
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contact.number))
            startActivity(intent)
        }

        (recycler_view.adapter as ContactAdapter).onItemLongClick = { contact ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("Contact", contact)
            startActivityForResult(intent, ACTION_EDIT_CONTACT)
        }

        add_contact.setOnClickListener {
            scaleDown.cancel()
            startActivityForResult(Intent(this, DetailActivity::class.java), ACTION_NEW_CONTACT)
        }

        checkIfListEmpty()
    }

    private lateinit var scaleDown: ObjectAnimator
    private fun checkIfListEmpty(){
        if((recycler_view.adapter as ContactAdapter).itemCount == 0){
            recycler_view.visibility = View.GONE
            empty_view.visibility = View.VISIBLE

            scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                    add_contact,
                    PropertyValuesHolder.ofFloat("scaleX", 1.3f),
                    PropertyValuesHolder.ofFloat("scaleY", 1.3f))
            scaleDown.duration = 1010
            scaleDown.repeatCount = ObjectAnimator.INFINITE
            scaleDown.repeatMode = ObjectAnimator.REVERSE
            scaleDown.start()
        }else{
            recycler_view.visibility = View.VISIBLE
            empty_view.visibility = View.GONE
            scaleDown.cancel()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        val thatThing = (recycler_view.adapter as ContactAdapter).getList()
        outState?.putParcelableArrayList("Contacts", thatThing)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        val mContacts = savedInstanceState?.getParcelableArrayList<Contact>("Contacts")
        if(mContacts != null) {
            (recycler_view.adapter as ContactAdapter).setList(mContacts)
            checkIfListEmpty()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(resultCode){
            ACTION_CANCEL -> {
                checkIfListEmpty()
            }
            ACTION_NEW_CONTACT -> {
                val name = data?.getStringExtra("name")
                val number = data?.getStringExtra("number")
                (recycler_view.adapter as ContactAdapter).add(name!!,number!!)
                checkIfListEmpty()
            }
            ACTION_EDIT_CONTACT -> {
                val contact = data?.getParcelableExtra<Contact>("Contact")
                if(contact != null)
                    (recycler_view.adapter as ContactAdapter).update(contact)
            }
            ACTION_DEL_CONTACT -> {
                val id = data?.getIntExtra("id", -1)
                if(id != null && id != -1) {
                    (recycler_view.adapter as ContactAdapter).remove(id)
                    checkIfListEmpty()
                }
            }
            else -> print("$resultCode $requestCode")
        }
    }

    private fun addFakeContacts(){
        (recycler_view.adapter as ContactAdapter).add("Buggs Bunny", "1(425)260-6800")
        (recycler_view.adapter as ContactAdapter).add("Dawyne the rock Johnson", "+1(425)260-6800")
        checkIfListEmpty()
        (recycler_view.adapter as ContactAdapter).add("Library Statue", "800-123-4567")
        (recycler_view.adapter as ContactAdapter).add("Joe Mcmahon", "800-342-3434")
        (recycler_view.adapter as ContactAdapter).add("Jordan Objective", "(425)260-6800")
        (recycler_view.adapter as ContactAdapter).add("Contacts Fragment Basics", "800-342-3434")
        (recycler_view.adapter as ContactAdapter).add("Light Water Possibly", "7603432934")
        (recycler_view.adapter as ContactAdapter).add("Logitech Macbook", "+1(425)260-6800")
        (recycler_view.adapter as ContactAdapter).add("Logitech Macbook", "+1(425)260-6800")
        (recycler_view.adapter as ContactAdapter).add("Logitech Macbook", "+1(425)260-6800")

    }

    private fun clearList(){
        (recycler_view.adapter as ContactAdapter).clear()
        checkIfListEmpty()
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filter -> print("need to filter")
            R.id.pop_list -> addFakeContacts()
            R.id.clear_list -> clearList()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

}
