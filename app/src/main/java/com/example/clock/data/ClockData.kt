package com.example.clock.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clock")
data class ClockData(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val time: String,
    var monday: Boolean = false,
    var tuesday: Boolean = false,
    var wednesday: Boolean = false,
    var thursday: Boolean = false,
    var friday: Boolean = false,
    var saturday: Boolean = false,
    var sunday: Boolean = false,


)
