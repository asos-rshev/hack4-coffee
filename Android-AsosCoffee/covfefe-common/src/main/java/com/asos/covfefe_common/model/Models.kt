package com.asos.covfefe_common.model


data class MenuCategory(var name:String? = null, var type:Int? = null, var items:List<CanteenMenuItem>? = null)

data class CanteenMenuItem(var name:String? = null,
                           var type:Int? = null,
                           var milky:Boolean = false,
                           var sizes:List<CanteenMenuItemSize>? = null,
                           var extras:List<CanteenMenuItemExtra>? = null)

data class CanteenMenuItemSize(var name:String? = null, var price:Double? = null)

data class CanteenMenuItemExtra(var name:String? = null, var price:Double? = null)
