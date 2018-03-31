package com.github.tictactoe.controller;

import com.github.tictactoe.model.Game;
import com.github.tictactoe.view.*;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Optional;

/**
 * Created by maxtar.
 */
@SuppressWarnings("UnnecessaryLocalVariable")
public class Controller {

    private static boolean isFirstIsCross = true;
    private GameBox gameBox;
    private int gameBoxSize = GameBox.getSize();
    private int tileSize = Tile.getSize();
    private int tileSpacing = GameBox.getTileSpacing();
    private Game game;
    private String playerComboValue;
    private int sizeSpinnerValue;
    private int tileSizeSpinnerValue;
    private int crossFontSizeSpinnerValue;
    private int noughtFontSizeSpinnerValue;
    private int tileSpacingSpinnerValue;
    private int lineWidthSpinnerValue;
    private Color lineColorPickerValue;

    public Controller(GameBox gameBox) {
        this.gameBox = gameBox;
        this.game = gameBox.getGame();
    }

    public void onGameParameters() {
        Stage parameterStage = new ParameterStage(this,
                gameBox.getScene().getWindow(), ParameterStage.Tab.GAME);
        parameterStage.show();
    }

    public void onViewParameters() {
        Stage parameterStage = new ParameterStage(this,
                gameBox.getScene().getWindow(), ParameterStage.Tab.VIEW);
        parameterStage.show();
    }

    public void playerComboListener(ComboBox<String> playerCombo) {
        playerComboValue = playerCombo.getValue();
    }

    public void sizeSpinnerListener(Spinner<Integer> sizeSpinner) {
        sizeSpinnerValue = sizeSpinner.getValue();
        sizeSpinner.getEditor().setText(sizeSpinnerValue + "x" + sizeSpinnerValue);
    }

    public void tileSizeSpinnerListener(Spinner<Integer> tileSizeSpinner) {
        tileSizeSpinnerValue = tileSizeSpinner.getValue();
    }

    public void crossFontSizeSpinnerListener(Spinner<Integer> crossFontSizeSpinner) {
        crossFontSizeSpinnerValue = crossFontSizeSpinner.getValue();
    }

    public void noughtFontSizeSpinnerListener(Spinner<Integer> noughtFontSizeSpinner) {
        noughtFontSizeSpinnerValue = noughtFontSizeSpinner.getValue();
    }

    public void tileSpacingSpinnerListener(Spinner<Integer> tileSpacingSpinner) {
        tileSpacingSpinnerValue = tileSpacingSpinner.getValue();
    }

    public void lineWidthSpinnerListener(Spinner<Integer> lineWidthSpinner) {
        lineWidthSpinnerValue = lineWidthSpinner.getValue();
    }

    public void lineColorPickerListener(ColorPicker lineColorPicker) {
        lineColorPickerValue = lineColorPicker.getValue();
    }

    public void onAcceptClicked(Tab tab) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Принять внесенные изменения");
        alert.setHeaderText("Это перезапустит текущую игру!");
        alert.setContentText("Вы действительно хотите\nпринять внесенные изменения?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (Objects.equals(playerComboValue, GameTab.getCROSS())) {
                isFirstIsCross = true;
            } else if (Objects.equals(playerComboValue, GameTab.getNOUGHT())) {
                isFirstIsCross = false;
            }

            GameBox.setSize(sizeSpinnerValue);
            Tile.setSize(tileSizeSpinnerValue);
            Tile.setCrossFontSize(crossFontSizeSpinnerValue);
            Tile.setNoughtFontSize(noughtFontSizeSpinnerValue);
            GameBox.setTileSpacing(tileSpacingSpinnerValue);
            GameBox.setLineWidth(lineWidthSpinnerValue);
            GameBox.setLineColor(lineColorPickerValue);

            Stage primaryStage = (Stage) gameBox.getScene().getWindow();
            GameBox gameBox = new GameBox();
            primaryStage.setScene(new Scene(gameBox, gameBox.getGameBoxWidth(), gameBox.getGameBoxHeight()));
            Stage stage = (Stage) tab.getTabPane().getScene().getWindow();
            stage.close();
        }
    }

    public void onCancelClicked(Tab tab) {
        Stage stage = (Stage) tab.getTabPane().getScene().getWindow();
        stage.close();
    }

    public void onTileClicked(Tile tile) {
        if (game.getState() == Game.State.CONTINUES && tile.getState() == Tile.State.EMPTY) {
            Text text = new Text();
            if (game.isCross()) {
                text.setText("x");
                text.setFont(Font.font(Tile.getCrossFontSize()));
                game.setIsCross(false);
                tile.setState(Tile.State.CROSS);
            } else {
//                text.setText("\u25CB");
                text.setText("o");
                text.setFont(Font.font(Tile.getNoughtFontSize()));
                game.setIsCross(true);
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

    public static boolean isFirstIsCross() {
        return isFirstIsCross;
    }

    public void setGameValues(String selectedPlayer, int gameSize) {
        this.playerComboValue = selectedPlayer;
        this.sizeSpinnerValue = gameSize;
    }

    public void setViewValues(int tileSizeSpinnerValue, int crossFontSizeSpinnerValue, int noughtFontSizeSpinnerValue,
            int tileSpacingSpinnerValue, int lineWidthSpinnerValue, Color lineColorPickerValue) {
        this.tileSizeSpinnerValue = tileSizeSpinnerValue;
        this.crossFontSizeSpinnerValue = crossFontSizeSpinnerValue;
        this.noughtFontSizeSpinnerValue = noughtFontSizeSpinnerValue;
        this.tileSpacingSpinnerValue = tileSpacingSpinnerValue;
        this.lineWidthSpinnerValue = lineWidthSpinnerValue;
        this.lineColorPickerValue = lineColorPickerValue;
    }
}
