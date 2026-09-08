package model;

import static model.Player.O;
import static model.Player.X;

public class Board {

    private Cell[][] cells = new Cell[3][3];

    private Player winner;
    private GameState state;
    private Player currentTurn;
    public enum GameState { IN_PROGRESS, FINISHED };

    public Board() {
        restart();
    }

    public void restart() {
        clearCells();
        winner = null;
        currentTurn = Player.X;
        setInProgressMode();
    }

    public void mark(int row, int col) {
        if (isValid(row, col)) {
            cells[row][col].setValue(currentTurn);

            if (isWinningMoveByPlayer(currentTurn, row, col)) {
                setInFinishedMode();
                winner = currentTurn;
            } else {
                flipCurrentTurn();
            }
        }
    }

    public Player getWinner() {
        return winner;
    }

    public Player getCurrentTurn() {
        return currentTurn;
    }

    public Cell getCell(int row, int col) {
        return cells[row][col];
    }

    public void setCurrentTurn(Player currentTurn) {
        this.currentTurn = currentTurn;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    private void setInProgressMode() {
        setState(GameState.IN_PROGRESS);
    }

    private void setInFinishedMode() {
        setState(GameState.FINISHED);
    }

    public Boolean isInProgressMode() {
        return getState().equals(GameState.IN_PROGRESS);
    }

    public Boolean isInFinishedMode() {
        return getState().equals(GameState.FINISHED);
    }

    private void clearCells() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    private boolean isValid(int row, int col) {
        if (state == GameState.FINISHED) {
            return false;
        } else if (isOutOfBounds(row) || isOutOfBounds(col)) {
            return false;
        } else if (isCellValueAlreadySet(row, col)) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isOutOfBounds(int idx) {
        return idx < 0 || idx > 2;
    }

    private boolean isCellValueAlreadySet(int row, int col) {
        return cells[row][col].getValue() != null;
    }

    public boolean isWinningMoveByPlayer(Player player, int currentRow, int currentCol) {
        return (cells[currentRow][0].getValue() == player
                && cells[currentRow][1].getValue() == player
                && cells[currentRow][2].getValue() == player
                || cells[0][currentCol].getValue() == player
                && cells[1][currentCol].getValue() == player
                && cells[2][currentCol].getValue() == player
                || currentRow == currentCol
                && cells[0][0].getValue() == player
                && cells[1][1].getValue() == player
                && cells[2][2].getValue() == player
                || currentRow + currentCol == 2
                && cells[0][2].getValue() == player
                && cells[1][1].getValue() == player
                && cells[2][0].getValue() == player);
    }

    private void flipCurrentTurn() {
        currentTurn = currentTurn == X ? O : X;
    }
}