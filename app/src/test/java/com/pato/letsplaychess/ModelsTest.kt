package com.pato.letsplaychess

import org.junit.Assert
import org.junit.Test

/**
 * Created by pato on 8/21/17.
 */
class ModelsTest {
    @Test fun rook_canMoveByColumnAndByRow() {
        val rook = Rook(Team.WHITE)

        val possibleSlots = rook.possibleSlots(Slot(1, 1))

        Assert.assertEquals(possibleSlots.size, 14)
        Assert.assertTrue(possibleSlots.contains(Slot(1, 2)))
        Assert.assertTrue(possibleSlots.contains(Slot(1, 3)))
        Assert.assertTrue(possibleSlots.contains(Slot(1, 4)))
        Assert.assertTrue(possibleSlots.contains(Slot(1, 5)))
        Assert.assertTrue(possibleSlots.contains(Slot(1, 6)))
        Assert.assertTrue(possibleSlots.contains(Slot(1, 7)))
        Assert.assertTrue(possibleSlots.contains(Slot(1, 8)))
        Assert.assertTrue(possibleSlots.contains(Slot(2, 1)))
        Assert.assertTrue(possibleSlots.contains(Slot(3, 1)))
        Assert.assertTrue(possibleSlots.contains(Slot(4, 1)))
        Assert.assertTrue(possibleSlots.contains(Slot(5, 1)))
        Assert.assertTrue(possibleSlots.contains(Slot(6, 1)))
        Assert.assertTrue(possibleSlots.contains(Slot(7, 1)))
        Assert.assertTrue(possibleSlots.contains(Slot(8, 1)))
    }

    @Test fun king_canMoveAround() {
        val king = King(Team.WHITE)

        val possibleSlots = king.possibleSlots(Slot(3, 2))

        Assert.assertEquals(possibleSlots.size, 8)
        Assert.assertTrue(possibleSlots.contains(Slot(2, 1)))
        Assert.assertTrue(possibleSlots.contains(Slot(2, 2)))
        Assert.assertTrue(possibleSlots.contains(Slot(2, 3)))
        Assert.assertTrue(possibleSlots.contains(Slot(3, 1)))
        Assert.assertTrue(possibleSlots.contains(Slot(3, 3)))
        Assert.assertTrue(possibleSlots.contains(Slot(4, 1)))
        Assert.assertTrue(possibleSlots.contains(Slot(4, 2)))
        Assert.assertTrue(possibleSlots.contains(Slot(4, 3)))
    }

    @Test fun bishop_canMoveInDiagonals() {
        val bishop = Bishop(Team.WHITE)

        val possibleSlots = bishop.possibleSlots(Slot(2, 1))

        Assert.assertEquals(possibleSlots.size, 7)
        Assert.assertTrue(possibleSlots.contains(Slot(1, 2)))
        Assert.assertTrue(possibleSlots.contains(Slot(3, 2)))
        Assert.assertTrue(possibleSlots.contains(Slot(4, 3)))
        Assert.assertTrue(possibleSlots.contains(Slot(5, 4)))
        Assert.assertTrue(possibleSlots.contains(Slot(6, 5)))
        Assert.assertTrue(possibleSlots.contains(Slot(7, 6)))
        Assert.assertTrue(possibleSlots.contains(Slot(8, 7)))
    }

    @Test fun queen_canMoveByColumnAndRow() {
        val queen = Queen(Team.WHITE)

        val possibleSlots = queen.possibleSlots(Slot(1, 1))

        Assert.assertTrue(possibleSlots.contains(Slot(1, 2)))
        Assert.assertTrue(possibleSlots.contains(Slot(1, 3)))
        Assert.assertTrue(possibleSlots.contains(Slot(1, 4)))
        Assert.assertTrue(possibleSlots.contains(Slot(1, 5)))
        Assert.assertTrue(possibleSlots.contains(Slot(1, 6)))
        Assert.assertTrue(possibleSlots.contains(Slot(1, 7)))
        Assert.assertTrue(possibleSlots.contains(Slot(1, 8)))
        Assert.assertTrue(possibleSlots.contains(Slot(2, 1)))
        Assert.assertTrue(possibleSlots.contains(Slot(3, 1)))
        Assert.assertTrue(possibleSlots.contains(Slot(4, 1)))
        Assert.assertTrue(possibleSlots.contains(Slot(5, 1)))
        Assert.assertTrue(possibleSlots.contains(Slot(6, 1)))
        Assert.assertTrue(possibleSlots.contains(Slot(7, 1)))
        Assert.assertTrue(possibleSlots.contains(Slot(8, 1)))
    }

    @Test fun queen_canMoveInDiagonals() {
        val queen = Queen(Team.WHITE)

        val possibleSlots = queen.possibleSlots(Slot(2, 1))

        Assert.assertTrue(possibleSlots.contains(Slot(1, 2)))
        Assert.assertTrue(possibleSlots.contains(Slot(3, 2)))
        Assert.assertTrue(possibleSlots.contains(Slot(4, 3)))
        Assert.assertTrue(possibleSlots.contains(Slot(5, 4)))
        Assert.assertTrue(possibleSlots.contains(Slot(6, 5)))
        Assert.assertTrue(possibleSlots.contains(Slot(7, 6)))
        Assert.assertTrue(possibleSlots.contains(Slot(8, 7)))
    }

    @Test fun knight_canMoveWeirdly() {
        val knight = Knight(Team.WHITE)

        val possibleSlots = knight.possibleSlots(Slot(4, 4))

        Assert.assertEquals(possibleSlots.size, 8)
        Assert.assertTrue(possibleSlots.contains(Slot(3, 2)))
        Assert.assertTrue(possibleSlots.contains(Slot(2, 3)))
        Assert.assertTrue(possibleSlots.contains(Slot(2, 5)))
        Assert.assertTrue(possibleSlots.contains(Slot(3, 6)))
        Assert.assertTrue(possibleSlots.contains(Slot(5, 2)))
        Assert.assertTrue(possibleSlots.contains(Slot(6, 3)))
        Assert.assertTrue(possibleSlots.contains(Slot(6, 5)))
        Assert.assertTrue(possibleSlots.contains(Slot(5, 6)))
    }

    @Test fun whitePawn_canMoveNormally() {
        val whitePawn = Pawn(Team.WHITE)

        val possibleSlots = whitePawn.possibleSlots(Slot(1, 3))

        Assert.assertEquals(possibleSlots.size, 1)
        Assert.assertTrue(possibleSlots.contains(Slot(1, 4)))
    }

    @Test fun whitePawn_specialMove() {
        val whitePawn = Pawn(Team.WHITE)

        val possibleSlots = whitePawn.possibleSlots(Slot(1, 2))

        Assert.assertEquals(possibleSlots.size, 2)
        Assert.assertTrue(possibleSlots.contains(Slot(1, 3)))
        Assert.assertTrue(possibleSlots.contains(Slot(1, 4)))
    }

    @Test fun blackPawn_canMoveNormally() {
        val blackPawn = Pawn(Team.BLACK)

        val possibleSlots = blackPawn.possibleSlots(Slot(1, 3))

        Assert.assertEquals(possibleSlots.size, 1)
        Assert.assertTrue(possibleSlots.contains(Slot(1, 2)))
    }

    @Test fun blackPawn_specialMove() {
        val blackPawn = Pawn(Team.BLACK)

        val possibleSlots = blackPawn.possibleSlots(Slot(1, 7))

        Assert.assertEquals(possibleSlots.size, 2)
        Assert.assertTrue(possibleSlots.contains(Slot(1, 6)))
        Assert.assertTrue(possibleSlots.contains(Slot(1, 5)))
    }
}
