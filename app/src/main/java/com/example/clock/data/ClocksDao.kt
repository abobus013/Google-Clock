package com.example.clock.data

import androidx.room.*

@Dao
interface ClocksDao {

    @Query("SELECT * FROM clock")
    suspend fun getListOfClocks(): List<ClockData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addClocks(clockData: ClockData)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateClocks(clockData: ClockData)

    @Query("SELECT * FROM clock WHERE id=:id")
    suspend fun getClocksById(id: Int): ClockData

    @Delete
    suspend fun deleteClock(clockData: ClockData)


}