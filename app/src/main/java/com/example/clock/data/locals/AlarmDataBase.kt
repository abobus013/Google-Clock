package com.example.clock.data.locals

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.clock.data.models.AlarmData
import com.example.clock.data.models.ClockData
import com.example.clock.data.models.CountryTime

@Database(entities = [AlarmData::class , ClockData::class, CountryTime::class ],  version = 11)
abstract class AlarmDataBase: RoomDatabase() {
    abstract fun getClocksDao(): ClockDao
    abstract fun getAlarmDao(): AlarmDao
    abstract fun getSearchCountryDao(): SearchCountryDao

    companion object {
        const val DATABASE_NAME = "db_name"

        fun getInstance(context: Context): AlarmDataBase {
            return Room.databaseBuilder(
                context.applicationContext,
                AlarmDataBase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration().build()
        }
    }

}