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

    // Método para verificar se um professor existe no banco de dados
    fun checkProfessor(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val columns = arrayOf(COLUMN_PROFESSOR_ID)
        val selection = "$COLUMN_PROFESSOR_USERNAME = ? AND $COLUMN_PROFESSOR_PASSWORD = ?"
        val selectionArgs = arrayOf(username, password)
        val cursor: Cursor? = db.query(TABLE_PROFESSOR, columns, selection, selectionArgs, null, null, null)
        val count = cursor?.count ?: 0
        cursor?.close()
        return count > 0
    }
    fun criarTabelaFrequencia(nomeTabela: String) {
        val db = this.writableDatabase

        // Cria a tabela de frequência com o nome fornecido (substituindo espaços por underscores para evitar problemas)
        val tableName = "frequencia_${nomeTabela.replace(" ", "_")}"
        val createTableQuery = ("CREATE TABLE IF NOT EXISTS " + tableName + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nome TEXT,"
                + "mac TEXT"
                + ")")
        db.execSQL(createTableQuery)
        db.close()
    }
}
