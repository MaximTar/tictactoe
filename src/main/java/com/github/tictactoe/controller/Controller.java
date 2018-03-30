package com.github.tictactoe.controller;

import com.github.tictactoe.model.Game;
import com.github.tictactoe.view.*;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Created by maxtar.
 */
@SuppressWarnings("UnnecessaryLocalVariable")
public class Controller {

    private final GameBox gameBox;
    private final int gameBoxSize = GameBox.getSize();
    private final int tileSize = Tile.getSize();
    private final int tileSpacing = GameBox.getTileSpacing();
    private final Game game;

    public Controller(GameBox gameBox) {
        this.gameBox = gameBox;
        this.game = gameBox.getGame();
    }

    public void onGameParameters() {
        ParameterPane parameterPane = new ParameterPane(this);
        Stage parametersStage = new Stage();
        parametersStage.setScene(new Scene(parameterPane, 300, 400));
        parametersStage.initOwner(gameBox.getScene().getWindow());
        parametersStage.initModality(Modality.WINDOW_MODAL);
        parametersStage.show();
    }

    public void onViewParameters() {
        ParameterPane parameterPane = new ParameterPane(this);
        Stage parametersStage = new Stage();
        parametersStage.setScene(new Scene(parameterPane, 300, 400));
        parametersStage.initOwner(gameBox.getScene().getWindow());
        parametersStage.initModality(Modality.WINDOW_MODAL);
        parametersStage.show();
    }

    public void playerComboListener(ComboBox<String> playerCombo) {
        String selected = playerCombo.getValue();
        if (Objects.equals(selected, GameTab.getCROSS())) {
            Game.setIsCross(true);
        } else if (Objects.equals(selected, GameTab.getNOUGHT())) {
            Game.setIsCross(false);
        }
    }

    public void sizeSpinnerListener(Spinner<Integer> sizeSpinner) {
        int size = sizeSpinner.getValue();
        sizeSpinner.getEditor().setText(size + "x" + size);
        GameBox.setSize(size);
    }

    public void onAcceptClicked(Tab tab) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Принять внесенные изменения");
        alert.setHeaderText("Это перезапустит текущую игру!");
        alert.setContentText("Вы действительно хотите\nпринять внесенные изменения?");
        alert.showAndWait();
        Stage primaryStage = (Stage) gameBox.getScene().getWindow();
        GameBox gameBox = new GameBox();
        primaryStage.setScene(new Scene(gameBox, gameBox.getGameBoxWidth(), gameBox.getGameBoxHeight()));
        Stage stage = (Stage) tab.getTabPane().getScene().getWindow();
        stage.close();
    }

    public void onCancelClicked(Tab tab) {
        Stage stage = (Stage) tab.getTabPane().getScene().getWindow();
        stage.close();
    }

    public void onTileClicked(Tile tile) {
        if (game.getState() == Game.State.CONTINUES && tile.getState() == Tile.State.EMPTY) {
            Text text = new Text();
            if (Game.isCross()) {
                text.setText("x");
                text.setFont(Font.font(Tile.getCrossFontSize()));
                Game.setIsCross(false);
                tile.setState(Tile.State.CROSS);
            } else {
//                text.setText("\u25CB");
                text.setText("o");
                text.setFont(Font.font(Tile.getNoughtFontSize()));
                Game.setIsCross(true);
                tile.setState(Tile.State.NOUGHT);
            }
            tile.getChildren().add(text);
        }
        game.checkResult();
        if (game.getState() != Game.State.CONTINUES) {
            int result = game.checkResult();
            switch (game.getLineType()) {
                case ROW: {
                    int startX = tileSpacing;
                    int startY = tileSize / 2 + (result - 1) * tileSize + result * tileSpacing;
                    int endX = gameBoxSize * (tileSize + tileSpacing);
                    int endY = startY;
                    gameBox.drawLine(startX, startY, endX, endY);
                    break;
                }
                case COLUMN: {
                    int startX = tileSize / 2 + (result - 1) * tileSize + result * tileSpacing;
                    int startY = tileSpacing;
                    int endX = startX;
                    int endY = gameBoxSize * (tileSize + tileSpacing);
                    gameBox.drawLine(startX, startY, endX, endY);
                    break;
                }
                case DIAGONAL: {
                    switch (result) {
                        case 1: {
                            int startX = tileSpacing;
                            int startY = tileSpacing;
                            int endX = gameBoxSize * (tileSize + tileSpacing);
                            int endY = gameBoxSize * (tileSize + tileSpacing);
                            gameBox.drawLine(startX, startY, endX, endY);
                            break;
                        }
                        case 2: {
                            int startX = gameBoxSize * (tileSize + tileSpacing);
                            int startY = tileSpacing;
                            int endX = tileSpacing;
                            int endY = gameBoxSize * (tileSize + tileSpacing);
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
