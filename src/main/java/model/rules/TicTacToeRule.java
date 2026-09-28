package model.rules;

import model.Board;
import model.GameState;
import model.Player;

public class TicTacToeRule implements GameRule {
    @Override
    public boolean isValid(Player player, Board board, int row, int col) {
        return !(board.getState() == GameState.FINISHED || board.isOutOfBounds(col, row) || board.isCellValueAlreadySet(row, col));
    }
    
}
