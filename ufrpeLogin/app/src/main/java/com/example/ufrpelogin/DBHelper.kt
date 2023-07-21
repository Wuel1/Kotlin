package com.example.ufrpelogin

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context): SQLiteOpenHelper(context, "frequenciadatabase.db",null,1) {

    val sql = arrayOf(
        "CREATE TABLE professor (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, mac TEXT)" ,
        "INSERT INTO professor (username,password,mac) VALUES ('waldemar','12345','00:45:E2:6A:46:3C')" ,
    )

    override fun onCreate(db: SQLiteDatabase) {
        sql.forEach {
            db.execSQL(it)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE professor")
        onCreate(db)
    }
}