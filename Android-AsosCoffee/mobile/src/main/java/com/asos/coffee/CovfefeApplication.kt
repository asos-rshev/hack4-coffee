package com.asos.coffee

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

class CovfefeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val database = FirebaseDatabase.getInstance()
        database.setPersistenceEnabled(true)
    }

    companion object {
        val TAG = "CANTEEN-CUSTOMER-CLIENT"
    }
}
