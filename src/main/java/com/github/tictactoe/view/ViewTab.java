package com.github.tictactoe.view;

import com.github.tictactoe.controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

@SuppressWarnings("WeakerAccess")
public class ViewTab extends Tab {

    public ViewTab(Controller controller) {
        BorderPane border = new BorderPane();
        border.setPadding(new Insets(25, 25, 25, 25));

        setText("Настройки внешнего вида");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label tileSizeLabel = new Label("Размер клеток поля");
        grid.add(tileSizeLabel, 0, 0);

        Label crossFontSizeLabel = new Label("Размер крестиков");
        grid.add(crossFontSizeLabel, 0, 1);

        Label noughtFontSizeLabel = new Label("Размер ноликов");
        grid.add(noughtFontSizeLabel, 0, 2);

        Label tileSpacingLabel = new Label("Промежуток между клетками поля");
        grid.add(tileSpacingLabel, 0, 3);

        Label lineWidthLabel = new Label("Толщина линии");
        grid.add(lineWidthLabel, 0, 4);

        Label lineColorLabel = new Label("Цвет линии");
        grid.add(lineColorLabel, 0, 5);

        Spinner<Integer> tileSizeSpinner = new Spinner<>();
        SpinnerValueFactory<Integer> tileSizeValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(30, 300);
        tileSizeSpinner.setValueFactory(tileSizeValueFactory);
        tileSizeSpinner.getValueFactory().setValue(Tile.getSize());
        tileSizeSpinner.valueProperty().addListener((observable, oldValue, newValue) ->
                controller.tileSizeSpinnerListener(tileSizeSpinner));
        grid.add(tileSizeSpinner, 1, 0);

        Spinner<Integer> crossFontSizeSpinner = new Spinner<>();
        SpinnerValueFactory<Integer> crossFontSizeValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(20, 200);
        crossFontSizeSpinner.setValueFactory(crossFontSizeValueFactory);
        crossFontSizeSpinner.getValueFactory().setValue(Tile.getCrossFontSize());
        crossFontSizeSpinner.valueProperty().addListener((observable, oldValue, newValue) ->
                controller.crossFontSizeSpinnerListener(crossFontSizeSpinner));
        grid.add(crossFontSizeSpinner, 1, 1);

        Spinner<Integer> noughtFontSizeSpinner = new Spinner<>();
        SpinnerValueFactory<Integer> noughtFontSizeValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(20, 200);
        noughtFontSizeSpinner.setValueFactory(noughtFontSizeValueFactory);
        noughtFontSizeSpinner.getValueFactory().setValue(Tile.getNoughtFontSize());
        noughtFontSizeSpinner.valueProperty().addListener((observable, oldValue, newValue) ->
                controller.noughtFontSizeSpinnerListener(noughtFontSizeSpinner));
        grid.add(noughtFontSizeSpinner, 1, 2);

        Spinner<Integer> tileSpacingSpinner = new Spinner<>();
        SpinnerValueFactory<Integer> tileSpacingValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50);
        tileSpacingSpinner.setValueFactory(tileSpacingValueFactory);
        tileSpacingSpinner.getValueFactory().setValue(GameBox.getTileSpacing());
        tileSpacingSpinner.valueProperty().addListener((observable, oldValue, newValue) ->
                controller.tileSpacingSpinnerListener(tileSpacingSpinner));
        grid.add(tileSpacingSpinner, 1, 3);

        Spinner<Integer> lineWidthSpinner = new Spinner<>();
        SpinnerValueFactory<Integer> lineWidthValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50);
        lineWidthSpinner.setValueFactory(lineWidthValueFactory);
        lineWidthSpinner.getValueFactory().setValue(GameBox.getLineWidth());
        lineWidthSpinner.valueProperty().addListener((observable, oldValue, newValue) ->
                controller.lineWidthSpinnerListener(lineWidthSpinner));
        grid.add(lineWidthSpinner, 1, 4);

        ColorPicker lineColorPicker = new ColorPicker(GameBox.getLineColor());
        lineColorPicker.setOnAction(event -> controller.lineColorPickerListener(lineColorPicker));
        grid.add(lineColorPicker, 1, 5);

        controller.setViewValues(tileSizeSpinner.getValue(), crossFontSizeSpinner.getValue(),
                noughtFontSizeSpinner.getValue(), tileSpacingSpinner.getValue(),
                lineWidthSpinner.getValue(), lineColorPicker.getValue());

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
}
