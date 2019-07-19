package tech.theunissen.ktor_example_app.repository

import tech.theunissen.ktor_example_app.model.User
import java.util.*

interface UserRepository {

    fun getUserById(id: UUID): User

}
