package com.example.clock.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "search_country")
data class
CountryTime(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val time: String
    )
