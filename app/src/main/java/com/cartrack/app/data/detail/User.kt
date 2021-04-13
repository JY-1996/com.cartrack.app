package com.cartrack.app.data.detail

import org.json.JSONObject

object UserContent {

    val ITEM_MAP: MutableMap<Int, User> = HashMap()

    data class User(
        val id: Int,
        val name: String,
        val username: String,
        val phone: String,
        val website: String,
        val email: String,
        val address: JSONObject,
        val company: JSONObject
    ) {
//        override fun toString(): String = content
    }
}