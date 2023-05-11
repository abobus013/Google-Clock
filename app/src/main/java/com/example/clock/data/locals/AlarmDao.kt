package com.example.clock.data.locals

import androidx.room.*
import com.example.clock.data.models.AlarmData

@Dao
interface AlarmDao {

    @Query("SELECT * FROM alarm")
    suspend fun getListOfAlarms(): List<AlarmData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAlarms(alarmData: AlarmData)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAlarms(alarmData: AlarmData)

    @Query("SELECT * FROM alarm WHERE id=:id")
    suspend fun getAlarmsById(id: Int): AlarmData

    @Delete
    suspend fun deleteAlarm(alarmData: AlarmData)


}