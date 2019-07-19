package tech.theunissen.ktor_example_app.service

import tech.theunissen.ktor_example_app.model.User
import java.util.*

object UserService {

    fun getUserById(id: String?): User = User(UUID.randomUUID(), "Test", "Testington")

}
