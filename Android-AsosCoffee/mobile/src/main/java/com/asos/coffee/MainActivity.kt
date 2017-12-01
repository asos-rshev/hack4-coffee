package com.asos.coffee

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.asos.covfefe_common.mapper.CategoryTypeToIconMapper
import com.asos.covfefe_common.model.CanteenMenuCategory
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.menu_category.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: FirebaseRecyclerAdapter<CanteenMenuCategory, MenuCategoryHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // Init Recycler View from Firebase db
        configureList()

        toolbar.setLogo(R.mipmap.ic_launcher_round)
        // Choose authentication providers
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
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

    private fun configureList() {
        val database = FirebaseDatabase.getInstance()
        val query = database.reference.child("menu")
        val options = FirebaseRecyclerOptions.Builder<CanteenMenuCategory>()
                .setQuery(query, CanteenMenuCategory::class.java)
                .build()
        adapter = object : FirebaseRecyclerAdapter<CanteenMenuCategory, MenuCategoryHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuCategoryHolder {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.menu_category, parent, false)

                return MenuCategoryHolder(view)
            }

            override fun onBindViewHolder(holder: MenuCategoryHolder, position: Int, model: CanteenMenuCategory) {
                holder.bind(model, View.OnClickListener {
                    startActivity(CanteenItemListActivity.newIntent(this@MainActivity, categoryIndex = position, categoryTitle = model.name))
                })
            }
        }

        menuCategoriesRecyclerView.layoutManager = LinearLayoutManager(this)
        menuCategoriesRecyclerView.adapter = adapter

    }

    companion object {
        @JvmStatic
        fun newIntent(context: Context): Intent = Intent(context, MainActivity::class.java)
    }
}

class MenuCategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val categoryTypeToIconMapper = CategoryTypeToIconMapper()
    fun bind(category: CanteenMenuCategory, clickAction:View.OnClickListener) {
        itemView.menuCategoryIcon.setImageResource(categoryTypeToIconMapper.iconForType(category.type))
        itemView.menuCategoryTitle.text = category.name
        itemView.setOnClickListener(clickAction)
    }
}
