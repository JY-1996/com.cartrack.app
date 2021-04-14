package com.cartrack.app.database

class DummyDataGenerator {
    companion object {
        fun getUsers(): List<Entity> {
            return listOf(
                Entity(1, "Noman1996", "Noman@1996", "Malaysia"),
                Entity(2, "Jason1999", "Jason@1996", "Singapore"),
                Entity(3, "Vincent2021", "Vincent@2021", "Thailand")
            )
        }
    }
}