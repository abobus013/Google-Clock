package com.example.clock.data.locals

import androidx.room.*
import com.example.clock.data.models.ClockData

@Dao
interface ClockDao {

    @Query("SELECT * FROM table_clock")
    suspend fun getListOfClocks(): List<ClockData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addClocks(clockData: ClockData)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateClocks(clockData: ClockData)

    @Query("SELECT * FROM table_clock WHERE id=:id")
    suspend fun getClocksById(id: Int): ClockData

    @Delete
    suspend fun deleteClock(clockData: ClockData)

}