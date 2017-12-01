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
import com.asos.covfefe_common.model.CanteenMenuItem
import com.asos.covfefe_common.model.CanteenMenuItemSize
import kotlinx.android.synthetic.main.activity_customise.*
import kotlinx.android.synthetic.main.size_item.view.*

private const val EXTRA_ITEM: String = "EXTRA_ITEM"

class CustomiseActivity : AppCompatActivity() {

    private val item by lazy {
        val result: CanteenMenuItem = intent.getParcelableExtra(EXTRA_ITEM)
        result
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customise)
        customizeTitle.text = item.name

        sizesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        extrasRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        milkRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val sizesAdapter = SizesAdapter(item.sizes ?: emptyList()) {
            updateFromSelection(it)
        }
        item.sizes?.get(0)?.let { sizesAdapter.selectedSize = it }
        sizesRecyclerView.adapter = sizesAdapter

        updateFromSelection(sizesAdapter.selectedSize)
    }

    private fun updateFromSelection(selectedSize: CanteenMenuItemSize?) {
        customiseProceedButton.text = getString(R.string.proceed_button_label_format, selectedSize?.price ?: 0.0)
        customizeSummary.text = selectedSize?.name
        sizesRecyclerView.adapter.notifyDataSetChanged()
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

class SizesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val canteenItemSizeNameToIconMapper = CanteenItemSizeNameToIconMapper()

    fun bind(size: CanteenMenuItemSize, selected: Boolean, clickAction: View.OnClickListener) {
        itemView.itemSizeIcon.setImageResource(canteenItemSizeNameToIconMapper.iconForSize(size.name))
        itemView.itemSizeTitle.text = size.name
        itemView.itemSizePrice.text = "£${size.price}"
        itemView.customiseSelectedBg?.visibility = if (selected) View.VISIBLE else View.GONE
        itemView.setOnClickListener(clickAction)
    }
}
