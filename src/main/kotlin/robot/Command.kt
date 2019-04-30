package robot

sealed class Command {
    object Left : Command()
    object Right : Command()
    object Move: Command()
    object Report : Command()
    data class Place(val x: Int, val y: Int, val direction: Direction) : Command()
}
