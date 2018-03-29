package com.github.tictactoe.controller;

import com.github.tictactoe.model.Game;
import com.github.tictactoe.view.ConfirmationAlert;
import com.github.tictactoe.view.GameBox;
import com.github.tictactoe.view.Tile;
import javafx.application.Platform;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Created by maxtar.
 */
@SuppressWarnings("UnnecessaryLocalVariable")
public class Controller {

    private final GameBox gameBox;

    public Controller(GameBox gameBox) {
        this.gameBox = gameBox;
    }

    public void onTileClicked(Tile tile) {
        Game game = gameBox.getGame();
        int tileSpacing = GameBox.getTileSpacing();
        int size = GameBox.getSize();
        if (game.getState() == Game.State.CONTINUES && tile.getState() == Tile.State.EMPTY) {
            Text text = new Text();
            if (game.isCross()) {
                text.setText("+");
                text.setFont(Font.font(Tile.getCrossFontSize()));
                text.setRotate(45.);
                game.setIsCross(false);
                tile.setState(Tile.State.CROSS);
            } else {
//                text.setText("\u25CB");
                text.setText("O");
                text.setFont(Font.font(Tile.getNoughtFontSize()));
                game.setIsCross(true);
                tile.setState(Tile.State.NOUGHT);
            }
            tile.getChildren().add(text);
        }
        game.checkResult();
        if (game.getState() != Game.State.CONTINUES) {
            int result = game.checkResult();
            int tileSize = Tile.getSize();
            switch (game.getLineType()) {
                case ROW: {
                    int startX = tileSpacing;
                    int startY = tileSize / 2 + (result - 1) * tileSize + result * tileSpacing;
                    int endX = size * (tileSize + tileSpacing);
                    int endY = startY;
                    gameBox.drawLine(startX, startY, endX, endY);
                    break;
                }
                case COLUMN: {
                    int startX = tileSize / 2 + (result - 1) * tileSize + result * tileSpacing;
                    int startY = tileSpacing;
                    int endX = startX;
                    int endY = size * (tileSize + tileSpacing);
                    gameBox.drawLine(startX, startY, endX, endY);
                    break;
                }
                case DIAGONAL: {
                    switch (result) {
                        case 1: {
                            int startX = tileSpacing;
                            int startY = tileSpacing;
                            int endX = size * (tileSize + tileSpacing);
                            int endY = size * (tileSize + tileSpacing);
                            gameBox.drawLine(startX, startY, endX, endY);
                            break;
                        }
                        case 2: {
                            int startX = size * (tileSize + tileSpacing);
                            int startY = tileSpacing;
                            int endX = tileSpacing;
                            int endY = size * (tileSize + tileSpacing);
                            gameBox.drawLine(startX, startY, endX, endY);
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
                    new ConfirmationAlert("Ничья!", gameBox.getScene().getWindow());
                    break;
                }
                case CROSS: {
                    gameBox.getTimeline().setOnFinished(actionEvent -> Platform.runLater(() ->
                            new ConfirmationAlert("Крестики победили!", gameBox.getScene().getWindow())));
                    break;
                }
                case NOUGHT: {
                    gameBox.getTimeline().setOnFinished(actionEvent -> Platform.runLater(() ->
                            new ConfirmationAlert("Нолики победили!", gameBox.getScene().getWindow())));
                    break;
                }
            }
        }
    }


}
