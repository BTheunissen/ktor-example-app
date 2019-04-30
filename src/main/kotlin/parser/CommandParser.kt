package parser

import arrow.core.*
import robot.Command
import robot.Direction
import utils.AppError
import java.io.InputStream
import kotlin.streams.toList

object CommandParser {

    fun parseInput(input: InputStream): List<Either<AppError,Command>> =
        input.bufferedReader().lines().map(this::parseCommand).toList()

    private fun parseCommand(commandString: String): Either<AppError, Command> {
        val placePattern = """\d+,\d+,(NORTH|EAST|SOUTH|WEST)""".toRegex()

        return when {
            commandString == "LEFT" -> Command.Left.right()
            commandString == "RIGHT" -> Command.Right.right()
            commandString == "MOVE" -> Command.Move.right()
            commandString == "REPORT" -> Command.Report.right()
            commandString.matches(placePattern) -> {
                val placeCommandParts = placePattern.split(",")

                return Direction.fromString(placeCommandParts[2]).flatMap { direction ->
                    Command.Place(
                        placeCommandParts[0].toInt(),
                        placeCommandParts[1].toInt(),
                        direction
                    ).right()
                }
            }
            else -> Left(AppError.InvalidCommand("Invalid command: $commandString"))
        }
    }
}
