package com.github.tictactoe;

import com.github.tictactoe.view.GameBox;
import com.github.tictactoe.view.Tile;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by maxtar.
 */
@SuppressWarnings("unused")
public class TicTacToe extends Application {

    private static int width;
    private static int height;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tic Tac Toe");
        width = GameBox.getSize() * Tile.getSize() + (GameBox.getSize() + 1) * GameBox.getTileSpacing() + GameBox.getSize();
        height = GameBox.getSize() * Tile.getSize() + (GameBox.getSize() + 1) * GameBox.getTileSpacing() + GameBox.getSize();
        primaryStage.setScene(new Scene(new GameBox(), width, height));
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNIFIED);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }
}
