package com.example.ufrpelogin.db

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

        // Tabela de alunos
        private const val TABLE_ALUNOS = "alunos"
        private const val COLUMN_ALUNO_ID = "Id"
        private const val COLUMN_ALUNO_USERNAME = "username"
        private const val COLUMN_ALUNO_PASSWORD = "password"
        private const val COLUMN_ALUNO_MAC = "mac"
        // Adicione as colunas para mac_2 e mac_3, se necessário.
    }


    val sql = arrayOf(
        "CREATE TABLE professor (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, mac TEXT, mac_2 TEXT, mac_3 TEXT)" ,
        "INSERT INTO professor (username,password,mac) VALUES ('waldemar.neto','12345','00:45:E2:6A:46:3C')" ,


        "CREATE TABLE alunos (Id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, mac TEXT, mac_2 TEXT, mac_3 TEXT)",
        "INSERT INTO alunos (username,password,mac) VALUES ('wandson.emanuel','12345','4c:63:71:88:bb:0a')"
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

    fun inserirFrequencia(tabela: String, nome: String, mac: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("dispositivo", nome)
        contentValues.put("mac", mac)
        val res = db.insert("tabela", null, contentValues)
        db.close()
        return res
    }

    fun alunosInsert(username: String, password: String, mac: String): Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("username", username)
        contentValues.put("password", password)
        contentValues.put("mac", mac)
        val res = db.insert("alunos",null,contentValues)
        db.close()
        return res
    }

    fun professorInsert(username: String, password: String, mac: String): Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("username", username)
        contentValues.put("password", password)
        contentValues.put("mac", mac)
        val res = db.insert("professor",null,contentValues)
        db.close()
        return res
    }

    fun alunosUpdate(id: Int, username: String, password: String, mac: String): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("username", username)
        contentValues.put("password", password)
        contentValues.put("mac", mac)
        val res = db.update("alunos", contentValues, "id=?", arrayOf(id.toString()))
        db.close()
        return res
    }

    fun professorUpdate(id: Int, username: String, password: String, mac: String): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("username", username)
        contentValues.put("password", password)
        contentValues.put("mac", mac)
        val res = db.update("professor", contentValues, "id=?", arrayOf(id.toString()))
        db.close()
        return res
    }

    fun alunosDelete(id: Int): Int{
        val db = this.writableDatabase
        val res = db.delete("alunos", "id=?", arrayOf(id.toString()))
        db.close()
        return res
    }

    fun professorDelete(id: Int): Int{
        val db = this.writableDatabase
        val res = db.delete("professor", "id=?", arrayOf(id.toString()))
        db.close()
        return res
    }

    fun isProfessorMac(mac: String): Boolean {
        val db = this.readableDatabase
        val selection = "mac = ?"
        val selectionArgs = arrayOf(mac)
        val cursor = db.query("professor", null, selection, selectionArgs, null, null, null)

        val isMatchFound = cursor.count > 0
        cursor.close()
        db.close()
        return isMatchFound
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

    // Método para verificar se um aluno existe no banco de dados
    fun checkAluno(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val columns = arrayOf(COLUMN_ALUNO_ID)
        val selection = "$COLUMN_ALUNO_USERNAME = ? AND $COLUMN_ALUNO_PASSWORD = ?"
        val selectionArgs = arrayOf(username, password)
        val cursor: Cursor? = db.query(TABLE_ALUNOS, columns, selection, selectionArgs, null, null, null)
        val count = cursor?.count ?: 0
        cursor?.close()
        return count > 0
    }

    fun professorSelect(): Cursor {
        val db = this.readableDatabase
        val c = db.rawQuery("SELECT * FROM professor",null)
        db.close()
        return c
    }

    fun professorSelectById(id: Int): Cursor{
        val db = this.readableDatabase
        val c = db.rawQuery("SELECT * FROM professor WHERE id=?", arrayOf(id.toString()))
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