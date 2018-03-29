package com.github.tictactoe.model;

import com.github.tictactoe.view.GameBox;
import com.github.tictactoe.view.Tile;

/**
 * Created by maxtar.
 */
@SuppressWarnings("unused")
public class Game {

    public enum State {DRAW, NOUGHT, CROSS, CONTINUES}
    @SuppressWarnings("WeakerAccess")
    public enum LineType {ROW, COLUMN, DIAGONAL, DRAW}

    private boolean isCross = true;
    private State state = State.CONTINUES;
    private LineType lineType;
    private final GameBox gameBox;

    public Game(GameBox gameBox) {
        this.gameBox = gameBox;
    }

    public int checkResult() {
        Tile[][] tiles = gameBox.getTiles();
        int row = checkLines(tiles);
        int column = checkColumns(tiles);
        int diagonal = checkDiagonals(tiles);
        if (row > 0) {
            lineType = LineType.ROW;
            return row;
        }
        if (column > 0) {
            lineType = LineType.COLUMN;
            return column;
        }
        if (diagonal > 0) {
            lineType = LineType.DIAGONAL;
            return diagonal;
        }
        if (checkDraw(tiles)) {
            lineType = LineType.DRAW;
            return 0;
        }
        return -1;
    }

    private int checkLines(Tile[][] tiles) {
        for (int i = 0; i < tiles.length; i++) {
            Tile[] row = tiles[i];
            Tile.State first = row[0].getState();
            boolean isLine = true;
            if (first != Tile.State.EMPTY) {
                for (int j = 1; j < row.length; j++) {
                    if (isLine && row[j].getState() != first) {
                        isLine = false;
                    }
                }
                if (isLine) {
                    if (first == Tile.State.CROSS) {
                        state = State.CROSS;
                    } else {
                        state = State.NOUGHT;
                    }
                    return i + 1;
                }
            }
        }
        return 0;
    }

    private int checkColumns(Tile[][] tiles) {
        for (int i = 0; i < tiles[0].length; i++) {
            Tile.State first = tiles[0][i].getState();
            boolean isColumn = true;
            if (first != Tile.State.EMPTY) {
                for (int j = 1; j < tiles.length; j++) {
                    if (isColumn && tiles[j][i].getState() != first) {
                        isColumn = false;
                    }
                }
                if (isColumn) {
                    if (first == Tile.State.CROSS) {
                        state = State.CROSS;
                    } else {
                        state = State.NOUGHT;
                    }
                    return i + 1;
                }
            }
        }
        return 0;
    }

    private int checkDiagonals(Tile[][] tiles) {
        Tile.State first = tiles[0][0].getState();
        boolean isDiagonal = true;
        for (int i = 1; i < tiles.length; i++) {
            if (first != Tile.State.EMPTY) {
                if (isDiagonal && tiles[i][i].getState() != first) {
                    isDiagonal = false;
                }
            } else {
                isDiagonal = false;
            }
        }
        if (isDiagonal) {
            if (first == Tile.State.CROSS) {
                state = State.CROSS;
            } else {
                state = State.NOUGHT;
            }
            return 1;
        }
        first = tiles[0][tiles.length - 1].getState();
        isDiagonal = true;
        for (int i = 1; i < tiles.length; i++) {
            if (first != Tile.State.EMPTY) {
                if (isDiagonal && tiles[i][tiles.length - (i + 1)].getState() != first) {
                    isDiagonal = false;
                }
            } else {
                isDiagonal = false;
            }
        }
        if (isDiagonal) {
            if (first == Tile.State.CROSS) {
                state = State.CROSS;
            } else {
                state = State.NOUGHT;
            }
            return 2;
        }
        return 0;
    }

    private boolean checkDraw(Tile[][] tiles) {
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                if (tile.getState() == Tile.State.EMPTY) {
                    return false;
                }
            }
        }
        state = State.DRAW;
        return true;
    }

    public State getState() {
        return state;
    }

    public boolean isCross() {
        return isCross;
    }

    public void setIsCross(boolean isCross) {
        this.isCross = isCross;
    }

    public LineType getLineType() {
        return lineType;
    }
}
