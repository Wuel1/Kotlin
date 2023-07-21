package com.example.ufrpelogin.db

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context): SQLiteOpenHelper(context, "frequenciadatabase.db",null,1) {

    val sql = arrayOf(
        "CREATE TABLE professor (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, mac TEXT)" ,
        "INSERT INTO professor (username,password,mac) VALUES ('Waldemar','12345','00:45:E2:6A:46:3C')" ,
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

    fun professorSelect(): Cursor {
        val db = this.readableDatabase
        val c = db.rawQuery("SELECT * FROM professor",null)
        db.close()
        return c
    }
    fun professorListSelectAll(): ArrayList<Professor>{
        val db = this.readableDatabase
        val c = db.rawQuery("SELECT * FROM professor",null)
        val listaProfessor: ArrayList<Professor> = ArrayList()
        if(c.count > 0){
            c.moveToFirst()
            do{
                val idIndex =  c.getColumnIndex("id")
                val usernameIndex =  c.getColumnIndex("username")
                val passwordIndex =  c.getColumnIndex("password")
                val macIndex =  c.getColumnIndex("mac")

                val id = c.getInt(idIndex)
                val username = c.getString(usernameIndex)
                val password = c.getString(passwordIndex)
                val mac = c.getString(macIndex)

                listaProfessor.add(Professor(id,username,password,mac))

            }while (c.moveToNext())
        }
        db.close()
        return listaProfessor
    }



}