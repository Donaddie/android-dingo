package com.donaddie.androiddingo

import android.app.Application
import com.donaddie.androiddingo.data.database.DingoDatabase

class DingoApp : Application() {
    lateinit var database: DingoDatabase
    
    override fun onCreate() {
        super.onCreate()
        database = DingoDatabase.getDatabase(this)
    }
}
