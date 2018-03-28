package com.github.tictactoe.view;

import com.github.tictactoe.model.Game;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Created by maxtar.
 */
@SuppressWarnings("unused")
public class Tile extends StackPane {

    public enum State {EMPTY, NOUGHT, CROSS}

    private static int size = 100;
    private static int crossFontSize = 80;
    private static int noughtFontSize = 60;
    private State state = State.EMPTY;

    Tile(Game game) {
        Rectangle rectangle = new Rectangle(size, size, Color.TRANSPARENT);
        rectangle.setStroke(Color.BLACK);
        getChildren().add(rectangle);
        setAlignment(Pos.CENTER);

        setOnMouseClicked(event -> {
            if (game.getState() == Game.State.CONTINUES && state == State.EMPTY) {
                Text text = new Text();
                if (game.isCross()) {
                    text.setText("+");
                    text.setFont(Font.font(crossFontSize));
                    text.setRotate(45.);
                    game.setIsCross(false);
                    state = State.CROSS;
                } else {
//                    text.setText("\u25CB");
                    text.setText("O");
                    text.setFont(Font.font(noughtFontSize));
                    game.setIsCross(true);
                    state = State.NOUGHT;
                }
                game.checkResult();
                getChildren().add(text);
            }
        });
    }

    public Tile.State getState() {
        return state;
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
