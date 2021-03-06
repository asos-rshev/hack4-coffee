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
    fun iconForSize(sizeName:String?):Int = when(sizeName?.toLowerCase()) {
        "small", "single" -> R.drawable.ic_latte_small
        "regular", "double" -> R.drawable.ic_latte_medium
        "large" -> R.drawable.ic_latte_large
        "" -> R.drawable.ic_food
        else -> R.drawable.ic_drinks
    }
}

class CanteenItemMilkNameToIconMapper {
    @DrawableRes
    fun iconForSize(milkName:String?):Int = when(milkName?.toLowerCase()) {
        "cow" -> R.drawable.ic_cow_milk
        "soy" -> R.drawable.ic_soy_milk
        "almond" -> R.drawable.ic_almond_milk
        else -> R.drawable.ic_cow_milk
    }
}

class CategoryTypeToIconMapper {
    @DrawableRes
    fun iconForType(type:Int?):Int = when(type) {
        0 -> R.drawable.ic_drinks
        else -> R.drawable.ic_food
    }
}