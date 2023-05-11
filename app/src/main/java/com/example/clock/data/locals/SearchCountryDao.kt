package com.example.clock.data.locals

import androidx.room.*
import com.example.clock.data.models.ClockData
import com.example.clock.data.models.CountryTime

@Dao
interface SearchCountryDao {

    @Query("SELECT * FROM search_country")
    suspend fun getListOfCountries(): List<CountryTime>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCountries(countryTime: CountryTime)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCountries(countryTime: CountryTime)

    @Query("SELECT * FROM search_country WHERE name LIKE '%' || :searchText || '%' OR time LIKE '%' || :searchText || '%'")
    suspend fun searchCountryByName(searchText: String): List<CountryTime>

}