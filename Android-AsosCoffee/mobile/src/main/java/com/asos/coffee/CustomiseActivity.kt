package com.asos.coffee

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.asos.covfefe_common.mapper.CanteenItemSizeNameToIconMapper
import com.asos.covfefe_common.model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_customise.*
import kotlinx.android.synthetic.main.extras_item.view.*
import kotlinx.android.synthetic.main.size_item.view.*


private const val EXTRA_ITEM: String = "EXTRA_ITEM"

class CustomiseActivity : AppCompatActivity() {

    private val item by lazy {
        val result: CanteenMenuItem = intent.getParcelableExtra(EXTRA_ITEM)
        result
    }

    private var selectedSize: CanteenMenuItemSize? = null
    private var selectedExtra: CanteenMenuItemExtra? = null
//    private var selectedMilk: CanteenMenuItemMilk? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customise)
        customizeTitle.text = item.name

        setupSizeList()
        setupMilkList()
        setupExtrasList()

        updateFromSelection()

        customiseProceedButton.setOnClickListener {
            // Create Order for now but TODO: and move to bag in the future...
            val orderNumber = System.currentTimeMillis()
            val database = FirebaseDatabase.getInstance()
            val ref = database.getReference("orders/$orderNumber")

            val order:Order = Order().apply {
                id = orderNumber
                inProgress = 0
                name = FirebaseAuth.getInstance().currentUser?.displayName?:"Unknown"
                totalPrice = calculateTotalPrice()
                items = listOf(
                        OrderItem().apply {
                            count = 1
                            extras = selectedExtra?.name?.let { listOf(it) }?: emptyList()
                            milky = item.milky
                            name = item.name
                            size = selectedSize?.name
                            type = 0
                            unitPrice = calculateTotalPrice()
                        }
                )
            }
            ref.setValue(order)
        }
    }

    private fun setupExtrasList() {
        extrasRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val extrasAdapter = ExtrasAdapter(item.extras ?: emptyList()) {
            selectedExtra = it
            updateFromSelection()
        }
        extrasAdapter.selectedExtra = null
        extrasRecyclerView.adapter = extrasAdapter
    }

    private fun setupMilkList() {
        if (item.milky) {
            milkRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            // TODO: MilkAdapter
        }
    }

    private fun setupSizeList() {
        sizesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val sizesAdapter = SizesAdapter(item.sizes ?: emptyList()) {
            selectedSize = it
            updateFromSelection()
        }
        item.sizes?.get(0)?.let {
            selectedSize = it
            sizesAdapter.selectedSize = selectedSize
        }
        sizesRecyclerView.adapter = sizesAdapter
    }

    private fun updateFromSelection() {
        val totalPrice = calculateTotalPrice()
        customiseProceedButton.text = getString(R.string.proceed_button_label_format, totalPrice)
        val summary = StringBuilder(selectedSize?.name)
        selectedExtra?.let { summary.append(", ${it.name}") }
        customizeSummary.text = summary.toString().toLowerCase()
    }

    private fun calculateTotalPrice(): Double {
        val sizePrice: Double = selectedSize?.price ?: 0.0
        val extraPrice: Double = selectedExtra?.price ?: 0.0
        val totalPrice = sizePrice + extraPrice
        return totalPrice
    }

    companion object {
        @JvmStatic
        fun newIntent(context: Context, item: CanteenMenuItem): Intent =
                Intent(context, CustomiseActivity::class.java).apply {
                    putExtra(EXTRA_ITEM, item)
                }
    }
}

class SizesAdapter(val items: List<CanteenMenuItemSize>, val buttonUpdater: (CanteenMenuItemSize?) -> Unit) : RecyclerView.Adapter<SizesViewHolder>() {

    var selectedSize: CanteenMenuItemSize? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizesViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.size_item, parent, false)
        return SizesViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SizesViewHolder?, position: Int) {
        val size = items[position]
        holder?.bind(size, selectedSize == size, View.OnClickListener {
            selectedSize = size
            buttonUpdater.invoke(size)
            notifyDataSetChanged()
        })
    }
}

class ExtrasAdapter(private val items: List<CanteenMenuItemExtra>, private val buttonUpdater: (CanteenMenuItemExtra?) -> Unit) : RecyclerView.Adapter<ExtrasViewHolder>() {

    var selectedExtra: CanteenMenuItemExtra? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExtrasViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.extras_item, parent, false)
        return ExtrasViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ExtrasViewHolder?, position: Int) {
        val extra = items[position]
        holder?.bind(extra, selectedExtra == extra, View.OnClickListener {
            selectedExtra = extra
            buttonUpdater.invoke(extra)
            notifyDataSetChanged()
        })
    }
}

class SizesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val canteenItemSizeNameToIconMapper = CanteenItemSizeNameToIconMapper()

    fun bind(size: CanteenMenuItemSize, selected: Boolean, clickAction: View.OnClickListener) {
        itemView.itemSizeIcon.setImageResource(canteenItemSizeNameToIconMapper.iconForSize(size.name))
        itemView.itemSizeTitle.text = size.name
        itemView.itemSizePrice.text = "£${size.price}"
        itemView.itemSizeSelectedBg?.visibility = if (selected) View.VISIBLE else View.GONE
        itemView.setOnClickListener(clickAction)
    }
}

class ExtrasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(extra: CanteenMenuItemExtra, selected: Boolean, clickAction: View.OnClickListener) {
        itemView.itemExtraTitle.text = extra.name
        itemView.itemExtraPrice.text = "£${extra.price}"
        itemView.itemExtraSelectedBg?.visibility = if (selected) View.VISIBLE else View.GONE
        itemView.setOnClickListener(clickAction)
    }
}

//class MilkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//    private val canteenItemSizeNameToIconMapper = CanteenItemSizeNameToIconMapper()
//
//    fun bind(milk: CanteenMenuItemMilk, selected: Boolean, clickAction: View.OnClickListener) {
//        itemView.itemMilkIcon.setImageResource(canteenItemSizeNameToIconMapper.iconForSize(milk.name))
//        itemView.itemMilkTitle.text = milk.name
//        itemView.itemMilkPrice.text = "£${milk.price}"
//        itemView.itemMilkSelectedBg?.visibility = if (selected) View.VISIBLE else View.GONE
//        itemView.setOnClickListener(clickAction)
//    }
//}
