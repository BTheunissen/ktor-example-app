package robot

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import utils.AppError

enum class Direction {
    NORTH, EAST, SOUTH, WEST;

    companion object {
        fun fromString(directionString: String): Either<AppError, Direction> =
          when (directionString) {
              "NORTH" -> valueOf("NORTH").right()
              "EAST" -> valueOf("EAST").right()
              "SOUTH" -> valueOf("SOUTH").right()
              "WEST" -> valueOf("WEST").right()
              else -> AppError.InvalidCommand("Invalid direction: $directionString").left()
          }
    }
}
