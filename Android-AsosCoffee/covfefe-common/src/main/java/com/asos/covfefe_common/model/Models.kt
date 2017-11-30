package com.asos.covfefe_common.model

import android.os.Parcel
import android.os.Parcelable
import paperparcel.PaperParcel

@PaperParcel
data class CanteenMenuCategory(var name:String? = null, var type:Int? = null, var items:List<CanteenMenuItem>? = null): Parcelable {
    companion object {
        @JvmField val CREATOR = PaperParcelCanteenMenuCategory.CREATOR
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        PaperParcelCanteenMenuCategory.writeToParcel(this, dest, flags)
    }
}

@PaperParcel
data class CanteenMenuItem(var name:String? = null,
                           var type:Int? = null,
                           var milky:Boolean = false,
                           var sizes:List<CanteenMenuItemSize>? = null,
                           var extras:List<CanteenMenuItemExtra>? = null) : Parcelable {
    companion object {
        @JvmField val CREATOR = PaperParcelCanteenMenuItem.CREATOR
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        PaperParcelCanteenMenuItem.writeToParcel(this, dest, flags)
    }
}

@PaperParcel
data class CanteenMenuItemSize(var name:String? = null, var price:Double? = null) : Parcelable {
    companion object {
        @JvmField val CREATOR = PaperParcelCanteenMenuItemSize.CREATOR
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        PaperParcelCanteenMenuItemSize.writeToParcel(this, dest, flags)
    }
}

@PaperParcel
data class CanteenMenuItemExtra(var name:String? = null, var price:Double? = null) : Parcelable {
    companion object {
        @JvmField val CREATOR = PaperParcelCanteenMenuItemExtra.CREATOR
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        PaperParcelCanteenMenuItemExtra.writeToParcel(this, dest, flags)
    }
}