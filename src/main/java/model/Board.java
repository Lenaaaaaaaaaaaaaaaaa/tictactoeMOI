package model;

import static model.Player.O;
import static model.Player.X;
import model.rules.GameRule;

public class Board {

    private int width;
    private int height;
    private Cell[][] cells;

    private int winningLength ;

    private Player winner;
    private GameState state;
    private Player currentTurn;
    private GameRule gameRule;




    public Board(int width, int height, int winningLength, GameRule gameRule) {
        if (width < 1 || height < 1) {
            throw new IllegalArgumentException("Width and height must be greater than 0");
        }
        if (winningLength < 1 || winningLength > Math.max(width, height)) {
            throw new IllegalArgumentException("Winning length must be greater than 0 and less than or equal to the maximum of width and height");
        }
        this.width = width;
        this.height = height;
        this.winningLength = winningLength;
        this.cells = new Cell[height][width];
        this.gameRule = gameRule;
        restart();
    }

    public void restart() {
        clearCells();
        winner = null;
        currentTurn = Player.X;
        setInProgressMode();
    }

    public void mark(int row, int col) {
        if (gameRule.isValid(currentTurn, this, row, col)) {
            cells[row][col].setValue(currentTurn);
            cells[row][col].setCategory(Category.DEFAULT);

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
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Cell();
            }
        }
    }


    public boolean isOutOfBounds(int col, int row) {
        return col < 0 || col > width - 1 || row < 0 || row > height - 1;
    }

    public boolean isCellValueAlreadySet(int row, int col) {
        return cells[row][col].getValue() != null;
    }

    public boolean isWinningMoveByPlayer(Player player, int currentRow, int currentCol) {
        Category category = cells[currentRow][currentCol].getCategory();
        boolean isWinningRow = isWinningMoveHorizontal(player, category, currentRow);
        boolean isWinningCol = isWinningMoveVertical(player, category, currentCol);
        boolean isWinningMoveDiagonal1 = isWinningMoveDiagonal1(player, category, currentRow, currentCol);
        boolean isWinningMoveDiagonal2 = isWinningMoveDiagonal2(player, category, currentRow, currentCol);


        return isWinningRow || isWinningCol || isWinningMoveDiagonal1 || isWinningMoveDiagonal2;
    }

    private boolean isWinningMoveHorizontal(Player player, Category category, int currentRow) {
        int length=0;
        for (int col = 0; col < width; col++) {

            length = isSamePiece(cells[currentRow][col], player, category) ? length + 1 : 0;
            
            if (length == winningLength) {
                return true;
            }
        }
        return false;
    }

    private boolean isWinningMoveVertical(Player player, Category category, int currentCol) {
        int length=0;
        for (int row = 0; row < height; row++) {
            length = isSamePiece(cells[row][currentCol], player, category) ? length + 1 : 0;
            if (length == winningLength) {
                return true;
            }
        }
        return false;
    }

    private boolean isWinningMoveDiagonal1(Player player, Category category, int currentRow, int currentCol) {
        int length=0;
        if (currentRow == currentCol) {
            for (int i = 0; i < width; i++) {
                length = isSamePiece(cells[i][i], player, category) ? length + 1 : 0;
                if (length == winningLength) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean isWinningMoveDiagonal2(Player player, Category category, int currentRow, int currentCol) {
        int length=0;
        if (currentRow + currentCol == width - 1) {
            for (int i = 0; i < width; i++) {
                length = isSamePiece(cells[i][width - 1 - i], player, category) ? length + 1 : 0;
                if (length == winningLength) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isSamePiece(Cell cell, Player player, Category category) {
        return cell.getValue() == player && cell.getCategory() == category;
    }

    private void flipCurrentTurn() {
        currentTurn = currentTurn == X ? O : X;
    }
}