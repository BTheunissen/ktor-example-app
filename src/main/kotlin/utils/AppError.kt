package utils

sealed class AppError(msg: String) {
    data class InvalidCommand(val msg: String) : AppError(msg)
}
