package com.example.frequenciafederalprofessor.db


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf

class DBHelper(context: Context): SQLiteOpenHelper(context, "frequenciadatabase.db",null,1) {

    companion object {
        private const val DATABASE_NAME = "frequenciadatabase.db"
        private const val DATABASE_VERSION = 1

        // Tabela de professores
        private const val TABLE_PROFESSOR = "professor"
        private const val COLUMN_PROFESSOR_ID = "id"
        private const val COLUMN_PROFESSOR_USERNAME = "username"
        private const val COLUMN_PROFESSOR_PASSWORD = "password"
        private const val COLUMN_PROFESSOR_MAC = "mac"
        // Adicione as colunas para mac_2 e mac_3, se necessário.

    }


    val sql = arrayOf(
        "CREATE TABLE professor (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, mac TEXT, mac_2 TEXT, mac_3 TEXT)",
        "INSERT INTO professor (username,password,mac) VALUES ('waldemar.neto','12345','00:45:E2:6A:46:3C')",
        "INSERT INTO professor (username,password,mac) VALUES ('lucas.ferreira','12345','00:45:E2:6A:46:3C')",
        )

    override fun onCreate(db: SQLiteDatabase) {
        sql.forEach {
            db.execSQL(it)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE professor")
        db.execSQL("DROP TABLE alunos")
        onCreate(db)
    }
}
