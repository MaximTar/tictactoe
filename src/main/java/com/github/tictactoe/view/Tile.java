package com.github.tictactoe.view;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by maxtar.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class Tile extends StackPane {

    public enum State {EMPTY, NOUGHT, CROSS}

    private static int size = 100;
    private static int crossFontSize = 80;
    private static int noughtFontSize = 80;
    private State state = State.EMPTY;

    Tile() {
        Rectangle rectangle = new Rectangle(size, size, Color.TRANSPARENT);
        rectangle.setStroke(Color.BLACK);
        getChildren().add(rectangle);
        setAlignment(Pos.CENTER);
    }

    public Tile.State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public static int getSize() {
        return size;
    }

    public static void setSize(int size) {
        Tile.size = size;
    }

    public static int getCrossFontSize() {
        return crossFontSize;
    }

    public static void setCrossFontSize(int crossFontSize) {
        Tile.crossFontSize = crossFontSize;
    }

    public static int getNoughtFontSize() {
        return noughtFontSize;
    }

    public static void setNoughtFontSize(int noughtFontSize) {
        Tile.noughtFontSize = noughtFontSize;
    }
}
