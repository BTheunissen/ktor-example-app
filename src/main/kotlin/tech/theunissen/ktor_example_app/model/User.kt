package tech.theunissen.ktor_example_app.model

import java.util.UUID

data class User(val id: UUID, val firstName: String, val lastName: String)
