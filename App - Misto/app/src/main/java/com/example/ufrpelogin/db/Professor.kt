package com.example.ufrpelogin.db

class Professor(val id: Int = 0, val username: String = "", val password: String = "", val mac: String = "") {

    override fun toString(): String {
        return "Professor(id=$id, username='$username', password='$password')"
    }
}