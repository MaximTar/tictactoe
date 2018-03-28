package com.github.tictactoe;

import com.github.tictactoe.controller.Controller;
import com.github.tictactoe.view.GameBox;
import com.github.tictactoe.view.Tile;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by maxtar.
 */
public class TicTacToe extends Application {

    private static int size;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tic Tac Toe");
        size = GameBox.getSize() * Tile.getSize() + (GameBox.getSize() + 1) * GameBox.getTileSpacing()
                + GameBox.getSize();
        Controller controller = new Controller();
        primaryStage.setScene(new Scene(controller.createContent(), size, size));
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNIFIED);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static int getSize() {
        return size;
    }
}
