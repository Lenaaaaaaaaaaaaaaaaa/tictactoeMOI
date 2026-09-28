package model.rules;

import model.Board;
import model.Player;

public interface GameRule {
    
    boolean isValid(Player player, Board board, int row, int col);
}
