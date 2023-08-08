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

        // Tabela de alunos
        private const val TABLE_ALUNOS = "alunos"
        private const val COLUMN_ALUNO_ID = "Id"
        private const val COLUMN_ALUNO_ALUNO = "aluno"
        private const val COLUMN_ALUNO_CURSO = "curso"
        private const val COLUMN_ALUNO_MAC = "mac"
        // Adicione as colunas para mac_2 e mac_3, se necessário.

    }

    val sql = arrayOf(
        "CREATE TABLE professor (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, mac TEXT, mac_2 TEXT, mac_3 TEXT)",
        "INSERT INTO professor (username,password,mac) VALUES ('waldemar.neto','12345','00:45:E2:6A:46:3C')",
        "INSERT INTO professor (username,password,mac) VALUES ('lucas.ferreira','12345','00:45:E2:6A:46:3C')",
        "INSERT INTO professor (username,password,mac) VALUES ('wandson.emanuel','12345','00:45:E2:6A:46:3C')",

        "CREATE TABLE alunos (Id INTEGER PRIMARY KEY AUTOINCREMENT, aluno TEXT, curso TEXT, mac TEXT, mac_2 TEXT, mac_3 TEXT)",
        "INSERT INTO alunos (aluno,curso,mac) VALUES ('Wandson Emanuel','Engenharia da Computação','00:45:E2:6A:46:3C')",
        "INSERT INTO alunos (aluno,curso,mac) VALUES ('Victor Almeida','Engenharia da Computação','48:87:59:11:8C:88')",
        "INSERT INTO alunos (aluno,curso,mac) VALUES ('Pedro Henrique','Engenharia da Computação','7C:8B:B5:DF:A8:80')",
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

    fun listarUsernames(): List<String> {
        val usernames = ArrayList<String>()
        val db = this.readableDatabase

        // Coluna que queremos recuperar (username)
        val columns = arrayOf(COLUMN_PROFESSOR_USERNAME)

        // Consulta para selecionar todos os usernames da tabela professor
        val cursor = db.query(
            TABLE_PROFESSOR,
            columns,
            null,
            null,
            null,
            null,
            null
        )

        // Percorrer o cursor e adicionar os usernames à lista
        cursor?.use {
            while (cursor.moveToNext()) {
                val username = cursor.getString(cursor.getColumnIndex(COLUMN_PROFESSOR_USERNAME))
                usernames.add(username)
            }
        }

        // Fechar o cursor e o banco de dados
        cursor?.close()
        db.close()

        return usernames
    }

    fun verificarAlunosPorMacs(macs: Array<String>): Array<String> {
        val db = this.readableDatabase

        // Monta a cláusula WHERE usando a lista de MACs
        val selection = "$COLUMN_ALUNO_MAC IN (${macs.joinToString { "'$it'" }})"

        val cursor = db.query(
            TABLE_ALUNOS,
            arrayOf(COLUMN_ALUNO_ALUNO),
            selection,
            null,
            null,
            null,
            null
        )

        val alunosEncontrados = mutableListOf<String>()

        while (cursor.moveToNext()) {
            val nomeAluno = cursor.getString(cursor.getColumnIndex(COLUMN_ALUNO_ALUNO))
            alunosEncontrados.add(nomeAluno)
        }

        cursor.close()
        db.close()

        return alunosEncontrados.toTypedArray()
    }
}
