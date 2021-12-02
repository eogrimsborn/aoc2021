package aoc

import java.io.File

sealed class SubCommand(val value: Int)
class UpCommand(value: Int) : SubCommand(value)
class DownCommand(value: Int) : SubCommand(value)
class ForwardCommand(value: Int) : SubCommand(value)

fun parseSubCommand(args: String): SubCommand {
    val (command, value) = args.split(" ")
    return when (command) {
        "up" -> UpCommand(value.toInt())
        "down" -> DownCommand(value.toInt())
        "forward" -> ForwardCommand(value.toInt())
        else -> throw Exception("Invalid sub coord")
    }
}

data class SubCoord(val depth: Int, val reach: Int, val aim: Int = 0) {
    operator fun plus(other: SubCoord) = SubCoord(depth + other.depth, reach + other.reach)
    fun applyCommand(command: SubCommand): SubCoord = when (command) {
        is DownCommand -> copy(aim = aim + command.value)
        is UpCommand -> copy(aim = aim - command.value)
        is ForwardCommand -> copy(reach = reach + command.value, depth = depth + aim * command.value)
    }

    companion object {
        operator fun invoke(command: SubCommand) = when (command) {
            is UpCommand -> SubCoord(-command.value, 0)
            is DownCommand -> SubCoord(command.value, 0)
            is ForwardCommand -> SubCoord(0, command.value)
        }
    }
}


object Two : Day(2) {
    override fun a(): String {
        val commands = File("inputs/input2.txt").readLines().map(::parseSubCommand)
        val subCoords = commands.map { SubCoord(it) }
        val result = subCoords.reduce { acc, coord ->
            acc + coord
        }
        return "The product of depth and reach is ${result.depth * result.reach}"
    }

    override fun b(): String {
        val commands = File("inputs/input2.txt").readLines().map(::parseSubCommand)
        val result = commands.fold(SubCoord(0, 0)) { acc, subCommand ->
            acc.applyCommand(subCommand)
        }
        return "The product of depth and reach is ${result.depth * result.reach}"
    }
}