package com.cartrack.app.data.detail

object UserContent {

    val ITEM_MAP: MutableMap<Int, User> = HashMap()

    data class User(
        val id: Int,
        val name: String,
        val username: String,
        val phone: String,
        val website: String,
        val email: String,
        val address: Address,
        val company: Company
    )

    data class Address(
        val street: String,
        val suite: String,
        val city: String,
        val zipcode: String,
        val geo: Geo
    )

    data class Company(
        val name: String,
        val catchPhrase: String,
        val bs: String
    )

    data class Geo(
        val lat: String,
        val lng: String
    )
}