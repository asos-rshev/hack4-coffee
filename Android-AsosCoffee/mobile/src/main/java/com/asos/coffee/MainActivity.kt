package com.asos.coffee

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import com.asos.covfefe_common.model.MenuCategory
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.ResultCodes
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.menu_category.view.*


private const val TAG = "ASOS-COVFEFE-CLIENT"
private const val RC_SIGN_IN = 123

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: FirebaseRecyclerAdapter<MenuCategory, MenuCategoryHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // Init Recycler View from Firebase db
        configureList()

        // Choose authentication providers
        val providers = listOf(
                AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build())
        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN)
    }

    private fun configureList() {
        val database = FirebaseDatabase.getInstance()
        val query = database.reference.child("menu")
        val options = FirebaseRecyclerOptions.Builder<MenuCategory>()
                .setQuery(query, MenuCategory::class.java)
                .build()
        adapter = object : FirebaseRecyclerAdapter<MenuCategory, MenuCategoryHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuCategoryHolder {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.menu_category, parent, false)

                return MenuCategoryHolder(view)
            }

            override fun onBindViewHolder(holder: MenuCategoryHolder, position: Int, model: MenuCategory) {
                holder.bind(model, View.OnClickListener {
                    startActivity(CanteenItemListActivity.newIntent(this@MainActivity, categoryIndex = position, categoryTitle = model.name))
                })
            }
        }

        menuCategoriesRecyclerView.layoutManager = LinearLayoutManager(this)
        menuCategoriesRecyclerView.adapter = adapter

    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.startListening()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == ResultCodes.OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                Log.i(TAG, "User $user logged in! ")
                loadData()
            } else {
                // Sign in failed, check response for error code
                Log.e(TAG, "Log In Failure ")
            }
        }
    }

    private fun loadData() {
        adapter.startListening()
    }
}

class MenuCategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(category: MenuCategory, clickAction:View.OnClickListener) {
        itemView.menuCategoryTitle.text = category.name
        itemView.setOnClickListener(clickAction)
    }
}
