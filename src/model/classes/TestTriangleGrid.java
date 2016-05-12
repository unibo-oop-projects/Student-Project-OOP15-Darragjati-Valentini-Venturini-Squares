package model.classes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.enumerations.GridOption;
import model.enumerations.ListType;
import model.interfaces.SquareGrid;
import model.interfaces.TriangleGrid;
import model.interfaces.Game;
import model.interfaces.LastMove;

/**
 * This class simulates the possible moves of a game. The games rules are
 * modified by using the TriangleGridImpl object.
 */
public class TestTriangleGrid {

    private static final Integer STANDARD_SIZE = 6;
    private static final Integer HORIZONTAL_SIZE = 5;
    private static final Integer VERTICAL_SIZE = 4;
    private final LastMove move = new LastMoveImpl();
    
    /**
     * Tests the methods of TriangleGridImpl and TurnImpl.
     */
    @Test
    public void test() {

        final TriangleGrid triangleGrid = new TriangleGridImpl(HORIZONTAL_SIZE, VERTICAL_SIZE);
        final Game gridOfSize = new GameImpl(triangleGrid);

        assertEquals(triangleGrid.getTotalMoves(), triangleGrid.getRemainingMoves());

        // verifies that every element in the list is initialized as EMPTY
        for (int i = 0; i < HORIZONTAL_SIZE + 1; i++) {
            for (int z = 0; z < HORIZONTAL_SIZE; z++) {
                assertEquals(triangleGrid.getCopyOfHorizontalElement(i, z), GridOption.EMPTY);
            }
        }
        for (int i = 0; i < VERTICAL_SIZE + 1; i++) {
            for (int z = 0; z < VERTICAL_SIZE; z++) {
                assertEquals(triangleGrid.getCopyOfVerticalElement(i, z), GridOption.EMPTY);
            }
        }
        for (int i = 0; i < HORIZONTAL_SIZE; i++) {
            for (int z = 0; z < VERTICAL_SIZE; z++) {
                assertEquals(triangleGrid.getCopyOfDiagonalElement(i, z), GridOption.EMPTY);
            }
        }

        assertFalse(gridOfSize.isStarted());
        gridOfSize.startMatch();
        assertTrue(gridOfSize.isStarted());
        move.setLastListType(ListType.VERTICAL);
        move.setLastListIndex(0);
        move.setLastPosition(0);
        gridOfSize.setLine(move);
        assertEquals(triangleGrid.getRemainingMoves(), (Integer) (triangleGrid.getTotalMoves() - 1));
        assertEquals(gridOfSize.getCopyOfLastMove().getLastListType(), ListType.VERTICAL);
        assertEquals(gridOfSize.getCopyOfLastMove().getLastListIndex(), (Integer) 0);
        assertEquals(gridOfSize.getCopyOfLastMove().getLastPosition(), (Integer) 0);
        move.setLastListType(ListType.HORIZONTAL);
        move.setLastListIndex(0);
        move.setLastPosition(0);
        gridOfSize.setLine(move);
        move.setLastListType(ListType.HORIZONTAL);
        move.setLastListIndex(1);
        move.setLastPosition(0);
        gridOfSize.setLine(move);
         //this turn memorization is used later to check if the turn switch is correctly implemented
        GridOption player = gridOfSize.getCopyOfCurrentPlayerTurn();
        move.setLastListType(ListType.VERTICAL);
        move.setLastListIndex(1);
        move.setLastPosition(0);
        gridOfSize.setLine(move);
        assertEquals(triangleGrid.getRemainingMoves(), (Integer) (triangleGrid.getTotalMoves() - 4));
        // the player points should be the same with this game mode
        assertEquals(gridOfSize.getPlayerPoints(GridOption.PLAYER1), gridOfSize.getPlayerPoints(GridOption.PLAYER2));
        assertNotEquals(player, gridOfSize.getCopyOfCurrentPlayerTurn());
        player = gridOfSize.getCopyOfCurrentPlayerTurn();
        move.setLastListType(ListType.DIAGONAL);
        move.setLastListIndex(0);
        move.setLastPosition(0);
        gridOfSize.setLine(move);
        assertNotEquals(gridOfSize.getPlayerPoints(GridOption.PLAYER1), gridOfSize.getPlayerPoints(GridOption.PLAYER2));
        assertEquals(player, gridOfSize.getCopyOfCurrentPlayerTurn());
        assertEquals(gridOfSize.getCopyOfLastMove().getLastListType(), ListType.DIAGONAL);
        assertEquals(gridOfSize.getCopyOfLastMove().getLastListIndex(), (Integer) 0);
        assertEquals(gridOfSize.getCopyOfLastMove().getLastPosition(), (Integer) 0);

        gridOfSize.undoLastMove();
        assertEquals(gridOfSize.getPlayerPoints(GridOption.PLAYER1), gridOfSize.getPlayerPoints(GridOption.PLAYER2));
        assertEquals(player, gridOfSize.getCopyOfCurrentPlayerTurn());
        assertEquals(gridOfSize.getCopyOfLastMove().getLastListType(), ListType.VERTICAL);
        assertEquals(gridOfSize.getCopyOfLastMove().getLastListIndex(), (Integer) 1);
        assertEquals(gridOfSize.getCopyOfLastMove().getLastPosition(), (Integer) 0);
        gridOfSize.undoLastMove();
        assertNotEquals(player, gridOfSize.getCopyOfCurrentPlayerTurn());

        final SquareGrid squareGrid2 = new TriangleGridImpl(STANDARD_SIZE, STANDARD_SIZE);
        final Game gridOfSize2 = new GameImpl(squareGrid2);
        gridOfSize2.startMatch();
        // fills the grid with all thepossible moves
        for (int i = 0; i < STANDARD_SIZE + 1; i++) {
            for (int z = 0; z < STANDARD_SIZE; z++) {
                move.setLastListType(ListType.HORIZONTAL);
                move.setLastListIndex(i);
                move.setLastPosition(z);
                gridOfSize2.setLine(move);
                move.setLastListType(ListType.VERTICAL);
                gridOfSize2.setLine(move);
            }
        }
        for (int i = 0; i < STANDARD_SIZE; i++) {
            for (int z = 0; z < STANDARD_SIZE; z++) {
                move.setLastListType(ListType.DIAGONAL);
                move.setLastListIndex(i);
                move.setLastPosition(z);
                gridOfSize2.setLine(move);
            }
        }
        assertTrue(squareGrid2.getRemainingMoves().equals(0));
        assertNotEquals(gridOfSize2.getPlayerPoints(GridOption.PLAYER1),
                gridOfSize2.getPlayerPoints(GridOption.PLAYER2));
        assertTrue(gridOfSize2.isEnded());
        assertNotEquals(GridOption.EMPTY, gridOfSize2.getWinner());
    }
}
