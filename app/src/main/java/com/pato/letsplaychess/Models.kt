package com.pato.letsplaychess

/**
 * Created by pato on 8/21/17.
 */

class Board {
    var slots: List<Slot> = (1..8).map { column -> (1..8).map { Slot(column, it) } }.flatten()

    fun insertAt(column: Int, row: Int, piece: Piece) {
        slots.find { (c, r, _) -> c == column && r == row  }?.piece = piece
    }

    init {
        for (i in 1..8) {
            insertAt(i, 2, Pawn(Team.WHITE))
        }
        putPieces(1, Team.WHITE)
        for (i in 1..8) {
            insertAt(i, 7, Pawn(Team.BLACK))
        }
        putPieces(8, Team.BLACK)
    }

    private fun putPieces(row: Int, team: Team) {
        insertAt(1, row, Rook(team))
        insertAt(2, row, Knight(team))
        insertAt(3, row, Bishop(team))
        insertAt(4, row, Queen(team))
        insertAt(5, row, King(team))
        insertAt(6, row, Bishop(team))
        insertAt(7, row, Knight(team))
        insertAt(8, row, Rook(team))
    }

    fun pieceFromTo(columnFrom: Int, rowFrom: Int, columnTo: Int, rowTo: Int, piece: Piece): Boolean {
        return piece.possibleSlots(Slot(columnFrom, rowFrom)).any { it.column == columnTo && it.row == rowTo }
    }

}

data class Slot(var column: Int, var row: Int, var piece: Piece? = null) {
    val insideBoard = column in (1..8) && row in (1..8)
}

enum class Team { WHITE, BLACK }

abstract class Piece(val team: Team) {
    abstract fun possibleSlots(currentSlot: Slot): List<Slot>
}

class Pawn(team: Team): Piece(team) {
    // TODO: Eat movement + En Passant
    override fun possibleSlots(currentSlot: Slot): List<Slot> {
        val delta = if (team == Team.WHITE) 1 else -1

        val normalMovement = listOf(Slot(currentSlot.column, currentSlot.row + delta))

        if (team == Team.WHITE && currentSlot.row == 2) {
            return normalMovement + Slot(currentSlot.column, 4)
        }

        if (team == Team.BLACK && currentSlot.row == 7) {
            return normalMovement + Slot(currentSlot.column, 5)
        }

        return normalMovement
    }
}

class Knight(team: Team): Piece(team) {
    override fun possibleSlots(currentSlot: Slot): List<Slot> {
        val twoDelta = listOf(-2, 2)
        val oneDelta = listOf(-1, 1)

        val movementsTwoOne = twoDelta.flatMap { columnDelta ->
            oneDelta.map { Slot(currentSlot.column + columnDelta, currentSlot.row + it) } }

        val movementsOneTwo = oneDelta.flatMap { columnDelta ->
            twoDelta.map { Slot(currentSlot.column + columnDelta, currentSlot.row + it) } }

        return (movementsTwoOne + movementsOneTwo).filter { it.insideBoard }
    }
}

class Rook(team: Team) : Piece(team) {
    override fun possibleSlots(currentSlot: Slot): List<Slot> =
            createColumnAndRow(currentSlot)
}

class King(team: Team) : Piece(team) {
    override fun possibleSlots(currentSlot: Slot): List<Slot> {
        val slots =
                (-1..1).flatMap { columnDelta ->
                    (-1..1).map {
                        Slot(currentSlot.column + columnDelta, currentSlot.row + it)
                    }
                }

        return slots.filter { currentSlot != it }.filter { it.insideBoard }
    }
}

class Queen(team: Team): Piece(team) {
    override fun possibleSlots(currentSlot: Slot): List<Slot> =
            createColumnAndRow(currentSlot) + createDiagonals(currentSlot)
}

class Bishop(team: Team): Piece(team) {
    override fun possibleSlots(currentSlot: Slot): List<Slot> =
        createDiagonals(currentSlot)
}

fun createColumnAndRow(currentSlot: Slot): List<Slot> {
    val byColumn = (1..8).map { Slot(it, currentSlot.row) }
    val byRow = (1..8).map { Slot(currentSlot.column, it) }

    return (byColumn + byRow).filter { currentSlot != it }
}

fun createDiagonals(currentSlot: Slot): List<Slot> {
    val firstDiagonal = (-7..7).map { Slot(currentSlot.column + it, currentSlot.row + it) }
    val secondDiagonal = (-7..7).map { Slot(currentSlot.column + it, currentSlot.row - it)}

    return (firstDiagonal + secondDiagonal).filter { currentSlot != it }.filter { it.insideBoard }
}
