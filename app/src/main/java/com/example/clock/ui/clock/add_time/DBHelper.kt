package com.example.clock.ui.clock.add_time

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "countries.db"
        private const val TABLE_COUNTRIES = "countries"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_COUNTRY_NAME = "name"
        private const val COLUMN_TIMEZONE = "timezone"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE " + TABLE_COUNTRIES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_COUNTRY_NAME + " TEXT,"
                + COLUMN_TIMEZONE + " TEXT" + ")")
        db.execSQL(createTable)

        val values = ContentValues()
        values.put(COLUMN_COUNTRY_NAME, "USA")
        values.put(COLUMN_TIMEZONE, TimeZone.getTimeZone("America/New_York").id)
        db.insert(TABLE_COUNTRIES, null, values)

        values.clear()
        values.put(COLUMN_COUNTRY_NAME, "Japan")
        values.put(COLUMN_TIMEZONE, TimeZone.getTimeZone("Asia/Tokyo").id)
        db.insert(TABLE_COUNTRIES, null, values)


        // Добавьте любые другие страны и их часовые пояса, которые вам нужны
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_COUNTRIES")
        onCreate(db)
    }

    @SuppressLint("Range")
    fun getAllCountries(): List<Pair<String, String>> {
        val countries = mutableListOf<Pair<String, String>>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_COUNTRIES", null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val name = cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY_NAME))
            val timezone = cursor.getString(cursor.getColumnIndex(COLUMN_TIMEZONE))
            countries.add(Pair(name, timezone))
            cursor.moveToNext()
        }
        cursor.close()
        return countries
    }
}