package com.example.clock.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_clock")
data class ClockData(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val time: String,
    val city: String,
    val timeDifference: String
)
