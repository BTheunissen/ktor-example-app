package parser

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import io.kotlintest.assertions.arrow.either.shouldBeLeftOfType
import io.kotlintest.inspectors.forAll
import io.kotlintest.matchers.types.shouldBeInstanceOf
import io.kotlintest.shouldBe
import io.kotlintest.specs.ShouldSpec
import robot.Command
import robot.Direction
import utils.AppError
import java.nio.charset.Charset

class CommandParserSpec: ShouldSpec({
    "CommandParser" {
        "parseInput" {
            should("return a List(Right(Command)) for well-formed PLACE input") {
                val validInputStream = """LEFT
                    |LEFT
                    |RIGHT
                    |PLACE,2,2,WEST
                    |REPORT
                """.trimMargin().byteInputStream(Charset.defaultCharset())

                CommandParser.parseInput(validInputStream).shouldBe(listOf(
                    Command.Left,
                    Command.Left,
                    Command.Right,
                    Command.Place(2, 2, Direction.WEST),
                    Command.Report
                ).map { it.right() })
            }

            should("return a List(Left(AppError)) for badly-formed inputs") {
                val invalidInputStream = """
                    |THIS
                    |INPUT
                    |IS
                    |BAD
                """.trimMargin().byteInputStream(Charset.defaultCharset())

                CommandParser.parseInput(invalidInputStream).forAll {
                    it.shouldBeLeftOfType<AppError>()
                }
            }
        }
    }
})
