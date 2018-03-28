package com.github.tictactoe.model;

import com.github.tictactoe.view.GameBox;
import com.github.tictactoe.view.Tile;

import java.util.List;

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
        List<List<Tile>> tiles = gameBox.getTiles();
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

    private int checkLines(List<List<Tile>> tiles) {
        for (int i = 0; i < tiles.size(); i++) {
            List<Tile> row = tiles.get(i);
            Tile.State first = row.get(0).getState();
            boolean line = true;
            if (first != Tile.State.EMPTY) {
                for (int j = 1; j < row.size(); j++) {
                    if (line && row.get(j).getState() != first) {
                        line = false;
                    }
                }
                if (line) {
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

    private int checkColumns(List<List<Tile>> tiles) {
        for (int i = 0; i < tiles.get(0).size(); i++) {
            Tile.State first = tiles.get(0).get(i).getState();
            boolean column = true;
            if (first != Tile.State.EMPTY) {
                for (int j = 1; j < tiles.size(); j++) {
                    if (column && tiles.get(j).get(i).getState() != first) {
                        column = false;
                    }
                }
                if (column) {
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

    private int checkDiagonals(List<List<Tile>> tiles) {
        Tile.State first = tiles.get(0).get(0).getState();
        boolean diagonal = true;
        for (int i = 1; i < tiles.size(); i++) {
            if (first != Tile.State.EMPTY) {
                if (diagonal && tiles.get(i).get(i).getState() != first) {
                    diagonal = false;
                }
            } else {
                diagonal = false;
            }
        }
        if (diagonal) {
            if (first == Tile.State.CROSS) {
                state = State.CROSS;
            } else {
                state = State.NOUGHT;
            }
            return 1;
        }
        first = tiles.get(0).get(tiles.size() - 1).getState();
        diagonal = true;
        for (int i = 1; i < tiles.size(); i++) {
            if (first != Tile.State.EMPTY) {
                if (diagonal && tiles.get(i).get(tiles.size() - (i + 1)).getState() != first) {
                    diagonal = false;
                }
            } else {
                diagonal = false;
            }
        }
        if (diagonal) {
            if (first == Tile.State.CROSS) {
                state = State.CROSS;
            } else {
                state = State.NOUGHT;
            }
            return 2;
        }
        return 0;
    }

    private boolean checkDraw(List<List<Tile>> tiles) {
        for (List<Tile> row : tiles) {
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
