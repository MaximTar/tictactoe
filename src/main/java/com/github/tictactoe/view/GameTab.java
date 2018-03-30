package com.github.tictactoe.view;

import com.github.tictactoe.controller.Controller;
import com.github.tictactoe.model.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * Created by maxtar.
 */
@SuppressWarnings("WeakerAccess")
public class GameTab extends Tab {

    private static final String CROSS = "Крестик";
    private static final String NOUGHT = "Нолик";

    private static ComboBox<String> playerCombo;

    public GameTab(Controller controller) {

        BorderPane border = new BorderPane();
        border.setPadding(new Insets(25, 25, 25, 25));

        setText("Настройки игры");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label playerLabel = new Label("Первым ходит");
        grid.add(playerLabel, 0, 0);

        Label gameLabel = new Label("Размер поля");
        grid.add(gameLabel, 0, 1);

        ObservableList<String> options = FXCollections.observableArrayList(CROSS, NOUGHT);
        playerCombo = new ComboBox<>(options);
        if (Game.isCross()) {
            playerCombo.getSelectionModel().select(CROSS);
        } else {
            playerCombo.getSelectionModel().select(NOUGHT);
        }
        playerCombo.valueProperty().addListener((observable, oldValue, newValue) -> controller.playerComboListener(playerCombo));
        grid.add(playerCombo, 1, 0);

        Spinner<Integer> sizeSpinner = new Spinner<>();
        int minValue = 3;
        int maxValue = Integer.MAX_VALUE;
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(minValue, maxValue);
        sizeSpinner.setValueFactory(valueFactory);
        int size = GameBox.getSize();
        sizeSpinner.getEditor().setText(size + "x" + size);
        sizeSpinner.valueProperty().addListener((observable, oldValue, newValue) -> controller.sizeSpinnerListener(sizeSpinner));
        grid.add(sizeSpinner, 1, 1);

        Button accept = new Button("Принять");
        accept.setOnMouseClicked(event -> controller.onAcceptClicked(this));

        Button cancel = new Button("Отмена");
        cancel.setOnMouseClicked(event -> controller.onCancelClicked(this));

        HBox buttonsBox = new HBox(10, accept, cancel);
        buttonsBox.setAlignment(Pos.CENTER_RIGHT);

        border.setCenter(grid);
        border.setBottom(buttonsBox);

        setContent(border);
    }

    public static String getCROSS() {
        return CROSS;
    }

    public static String getNOUGHT() {
        return NOUGHT;
    }

    public static ComboBox<String> getPlayerCombo() {
        return playerCombo;
    }
}
