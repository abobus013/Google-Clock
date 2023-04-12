package com.example.clock.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ClockData::class], version = 7)
abstract class DataBase: RoomDatabase() {

    abstract fun getClocksDao(): ClocksDao


    companion object {
        const val DATABASE_NAME = "db_name"

        fun getInstance(context: Context): DataBase {
            return Room.databaseBuilder(
                context.applicationContext,
                DataBase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration().build()
        }
    }

}