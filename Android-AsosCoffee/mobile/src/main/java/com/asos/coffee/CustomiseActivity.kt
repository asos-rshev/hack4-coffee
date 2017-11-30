package com.asos.coffee

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.asos.covfefe_common.model.CanteenMenuItem
import kotlinx.android.synthetic.main.activity_customise.*

private const val EXTRA_ITEM:String = "EXTRA_ITEM"

class CustomiseActivity : AppCompatActivity() {

    private val item by lazy {
        val result:CanteenMenuItem = intent.getParcelableExtra(EXTRA_ITEM)
        result
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customise)
        customizeTitle.text = item.name
        customiseProceedButton.text = "PROCEED (total: £${item.sizes!![0].price})"
    }

    companion object {
        @JvmStatic
        fun newIntent(context: Context, item: CanteenMenuItem): Intent =
                Intent(context, CustomiseActivity::class.java).apply {
                    putExtra(EXTRA_ITEM, item)
                }
    }
}
