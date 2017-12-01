package com.asos.covfefe_common.mapper

import android.support.annotation.DrawableRes
import com.asos.covfefe_common.R

class CanteenItemTypeToIconMapper {
    @DrawableRes
    fun iconForType(type:Int?):Int = when(type) {
        0, 2 -> R.drawable.ic_americano
        1 -> R.drawable.ic_espresso
        else -> R.drawable.ic_food
    }
}
class CanteenItemSizeNameToIconMapper {
    @DrawableRes
    fun iconForSize(sizeName:String?):Int = when(sizeName) {
        "Small" -> R.drawable.ic_latte_small
        "Medium" -> R.drawable.ic_latte_medium
        "Large" -> R.drawable.ic_latte_large
        else -> R.drawable.ic_drinks
    }
}

class CategoryTypeToIconMapper {
    @DrawableRes
    fun iconForType(type:Int?):Int = when(type) {
        0 -> R.drawable.ic_drinks
        else -> R.drawable.ic_food
    }
}