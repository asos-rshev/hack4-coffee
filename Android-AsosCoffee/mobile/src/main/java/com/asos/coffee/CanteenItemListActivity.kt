package com.asos.coffee

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.asos.covfefe_common.mapper.CanteenItemTypeToIconMapper
import com.asos.covfefe_common.model.CanteenMenuItem
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_canteen_item_list.*
import kotlinx.android.synthetic.main.canteen_item.view.*

const val EXTRA_ITEM_INDEX:String = "EXTRA_ITEM_INDEX"
const val EXTRA_ITEM_TITLE:String = "EXTRA_ITEM_TITLE"

class CanteenItemListActivity : AppCompatActivity() {

    private lateinit var adapter: FirebaseRecyclerAdapter<CanteenMenuItem, CanteenMenuItemHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        toolbar.setLogo(R.mipmap.ic_launcher_round)
//        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.activity_canteen_item_list)
        configureList(intent.getIntExtra(EXTRA_ITEM_INDEX, -1))
        itemsListTitle.text = getString(R.string.item_list_title_format, intent.getStringExtra(EXTRA_ITEM_TITLE))
    }

    private fun configureList(categoryIndex: Int) {
        if (categoryIndex == -1) {
            return
        }
        val database = FirebaseDatabase.getInstance()
        val query = database.reference.child("menu/$categoryIndex/items")
        val options = FirebaseRecyclerOptions.Builder<CanteenMenuItem>()
                .setQuery(query, CanteenMenuItem::class.java)
                .build()
        adapter = object : FirebaseRecyclerAdapter<CanteenMenuItem, CanteenMenuItemHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CanteenMenuItemHolder {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.canteen_item, parent, false)

                return CanteenMenuItemHolder(view)
            }

            override fun onBindViewHolder(holder: CanteenMenuItemHolder, position: Int, model: CanteenMenuItem) {
                holder.bind(model, View.OnClickListener {

                })
            }
        }

        itemListRecyclerView.layoutManager = GridLayoutManager(this, 2)
        itemListRecyclerView.adapter = adapter

    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.startListening()
    }

    companion object {
        @JvmStatic
        fun newIntent(context: Context, categoryIndex: Int, categoryTitle: String?): Intent =
            Intent(context, CanteenItemListActivity::class.java).apply {
                putExtra(EXTRA_ITEM_INDEX, categoryIndex)
                putExtra(EXTRA_ITEM_TITLE, categoryTitle)
        }
    }
}

class CanteenMenuItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val canteenItemTypeToIconMapper = CanteenItemTypeToIconMapper()

    fun bind(item: CanteenMenuItem, clickAction: View.OnClickListener) {
        itemView.itemIcon.setImageResource(canteenItemTypeToIconMapper.iconForType(item.type))
        itemView.itemTitle.text = item.name
        itemView.itemPrice.text = item.sizes?.joinToString(separator = "  ") { "£${it.price}" }
        itemView.setOnClickListener(clickAction)
    }
}
