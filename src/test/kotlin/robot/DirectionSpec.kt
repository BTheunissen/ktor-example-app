package robot

import io.kotlintest.assertions.arrow.either.shouldBeLeft
import io.kotlintest.assertions.arrow.either.shouldBeRight
import io.kotlintest.properties.assertAll
import io.kotlintest.specs.ShouldSpec
import io.kotlintest.specs.StringSpec
import utils.AppError

object DirectionSpec: ShouldSpec({
    "Direction" {
        "fromString" {
            should("return NORTH enum in a Right when passed NORTH string") {
                Direction.fromString("NORTH").shouldBeRight(Direction.NORTH)
            }
            should("return EAST enum in a Right when passed EAST string") {
                Direction.fromString("EAST").shouldBeRight(Direction.EAST)
            }
            should("return SOUTH enum in a Right when passed SOUTH string") {
                Direction.fromString("SOUTH").shouldBeRight(Direction.SOUTH)
            }
            should("return WEST enum in a Right when passed WEST string") {
                Direction.fromString("WEST").shouldBeRight(Direction.WEST)
            }
            should("return an AppError of type InvalidCommand when passed any other string") {
                assertAll { input: String ->
                    Direction.fromString(input)
                        .shouldBeLeft(AppError.InvalidCommand("Invalid direction: $input"))
                }
            }
        }
    }
})
