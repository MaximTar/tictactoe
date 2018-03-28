package com.github.tictactoe.view;

import com.github.tictactoe.controller.Controller;
import com.github.tictactoe.model.Game;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxtar.
 */
@SuppressWarnings("unused")
public class GameBox extends VBox {

    private static final int DEFAULT_SIZE = 3;
    private static int size = DEFAULT_SIZE;
    private static int tileSpacing = 5;
    private final List<List<Tile>> tiles = new ArrayList<>();

    @SuppressWarnings("UnnecessaryLocalVariable")
    public GameBox(Controller controller) {

        Game game = new Game(this);

        setPadding(new Insets(tileSpacing));
        setSpacing(tileSpacing);
        for (int i = 0; i < size; i++) {
            List<Tile> row = new ArrayList<>();
            HBox hBox = new HBox(tileSpacing);
            for (int j = 0; j < size; j++) {
                Tile tile = new Tile(game);
                hBox.getChildren().add(tile);
                row.add(tile);
            }
            getChildren().add(hBox);
            tiles.add(row);
        }

        addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (game.getState() != Game.State.CONTINUES) {
                int result = game.checkResult();
                int tileSize = Tile.getSize();
                switch (game.getLineType()) {
                    case ROW: {
                        int startX = tileSpacing;
                        int startY = tileSize / 2 + (result - 1) * tileSize + result * tileSpacing;
                        int endX = size * (tileSize + tileSpacing);
                        int endY = startY;
                        controller.drawLine(startX, startY, endX, endY);
                        break;
                    }
                    case COLUMN: {
                        int startX = tileSize / 2 + (result - 1) * tileSize + result * tileSpacing;
                        int startY = tileSpacing;
                        int endX = startX;
                        int endY = size * (tileSize + tileSpacing);
                        controller.drawLine(startX, startY, endX, endY);
                        break;
                    }
                    case DIAGONAL: {
                        switch (result) {
                            case 1: {
                                int startX = tileSpacing;
                                int startY = tileSpacing;
                                int endX = size * (tileSize + tileSpacing);
                                int endY = size * (tileSize + tileSpacing);
                                controller.drawLine(startX, startY, endX, endY);
                                break;
                            }
                            case 2: {
                                int startX = size * (tileSize + tileSpacing);
                                int startY = tileSpacing;
                                int endX = tileSpacing;
                                int endY = size * (tileSize + tileSpacing);
                                controller.drawLine(startX, startY, endX, endY);
                                break;
                            }
                        }
                        break;
                    }
                    case DRAW: {
                        break;
                    }
                }
                switch (game.getState()) {
                    case DRAW: {
                        new AlertHandler("Ничья!", getScene().getWindow());
                        break;
                    }
                    case CROSS: {
                        controller.getTimeline().setOnFinished(actionEvent -> Platform.runLater(() ->
                                new AlertHandler("Крестики победили!", getScene().getWindow())));
                        break;
                    }
                    case NOUGHT: {
                        controller.getTimeline().setOnFinished(actionEvent -> Platform.runLater(() ->
                                new AlertHandler("Нолики победили!", getScene().getWindow())));
                        break;
                    }
                }
            }
        });
    }

    public static int getSize() {
        return size;
    }

    public static void setSize(int size) {
        if (size < DEFAULT_SIZE) {
            // todo say to user
            GameBox.size = DEFAULT_SIZE;
        } else {
            GameBox.size = size;
        }
    }

    public static int getTileSpacing() {
        return tileSpacing;
    }

    public static void setTileSpacing(int tileSpacing) {
        GameBox.tileSpacing = tileSpacing;
    }

    public List<List<Tile>> getTiles() {
        return tiles;
    }
}
