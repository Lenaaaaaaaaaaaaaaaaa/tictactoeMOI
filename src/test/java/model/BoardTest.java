package model;

import model.rules.GameRule;
import model.rules.TicTacToeRule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTest {

    private Board board;

    @BeforeEach
    public void setUp() {
        GameRule rule = new TicTacToeRule();
        board = new Board(3,4,3, rule);
    }

    @Test
    public void testMarkIsCorrect() {
        board.mark(0, 0);
        Player turn = board.getCurrentTurn();
        Player value = board.getCell(0, 0).getValue();
        assertEquals(Player.X, value);
        assertEquals(Category.DEFAULT, board.getCell(0, 0).getCategory());
        assertEquals(Player.O, turn);
    }

    @Test
    public void testMarkIsNotOnTheBoard(){
        Player turnBefore = board.getCurrentTurn();
        board.mark(-1, 0);
        Player turnAfter = board.getCurrentTurn();
        assertEquals(turnBefore, turnAfter);
    }

    @Test
    public void testMarkCellIsAlreadyMarked() {
        board.mark(0, 0); // X plays
        Player turnBefore = board.getCurrentTurn();
        board.mark(0, 0); // O tries to play on the same cell   
        Player turnAfter = board.getCurrentTurn();
        Player value = board.getCell(0, 0).getValue();

        assertEquals(Player.X, value);
        assertEquals(turnBefore, turnAfter);
    }

    @Test
    public void testMarkWinningMove() {
        board.mark(0, 0); // X plays
        board.mark(1, 0); // O plays
        board.mark(0, 1); // X plays
        board.mark(1, 1); // O plays
        board.mark(0, 2); // X plays and wins

        Boolean isFinished = board.isInFinishedMode();
        
        Player winner = board.getWinner();
        assertEquals(Player.X, winner);
        assertEquals(true, isFinished);
    }

    @Test
    public void testMarkGameNotInProgress() {
        board.setState(GameState.FINISHED);
        Player turnBefore = board.getCurrentTurn();
        board.mark(0, 0); // X tries to play while the game is finished
        Player turnAfter = board.getCurrentTurn();
        Player value = board.getCell(0, 0).getValue();

        assertEquals(null, value);
        assertEquals(turnBefore, turnAfter);
    }

    @Test
    public void testIsWinningMoveByPlayerWinByHorizontal() {
        board.mark(0, 0); // X plays
        board.mark(1, 0); // O plays
        board.mark(0, 1); // X plays
        board.mark(1, 1); // O plays
        board.mark(0, 2); // X plays and wins

        Boolean isWinningMove = board.isWinningMoveByPlayer(Player.X, 0, 2);
        assertEquals(true, isWinningMove);
    }

    @Test
    public void testIsWinningMoveByPlayerWinByVertical() {
        board.mark(0, 0); // X plays
        board.mark(0, 1); // O plays
        board.mark(1, 0); // X plays
        board.mark(1, 1); // O plays
        board.mark(2, 0); // X plays and wins

        Boolean isWinningMove = board.isWinningMoveByPlayer(Player.X, 2, 0);
        assertEquals(true, isWinningMove);
    }

    @Test
    public void testIsWinningMoveByPlayerWinByDiagonal() {
        board.mark(0, 0); // X plays
        board.mark(0, 1); // O plays
        board.mark(1, 1); // X plays
        board.mark(1, 0); // O plays
        board.mark(2, 2); // X plays and wins

        Boolean isWinningMove = board.isWinningMoveByPlayer(Player.X, 2, 2);
        assertEquals(true, isWinningMove);
    }

    @Test
    public void testIsWinningMoveByPlayerWinByOtherDiagonal() {
        board.mark(0, 2); // X plays
        board.mark(0, 1); // O plays
        board.mark(1, 1); // X plays
        board.mark(1, 0); // O plays
        board.mark(2, 0); // X plays and wins

        Boolean isWinningMove = board.isWinningMoveByPlayer(Player.X, 2, 0);
        assertEquals(true, isWinningMove);
    }

    @Test
    public void testIsWinningMoveByPlayerNotWinning() {
        board.mark(0, 0);
    
        Boolean isWinningMove = board.isWinningMoveByPlayer(Player.X, 0, 0);
        assertEquals(false, isWinningMove);
    }

}