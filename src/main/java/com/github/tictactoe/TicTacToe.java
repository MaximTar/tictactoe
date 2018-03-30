package com.github.tictactoe;

import com.github.tictactoe.view.GameBox;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by maxtar.
 */
@SuppressWarnings("unused")
public class TicTacToe extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tic Tac Toe");
        GameBox gameBox = new GameBox();
        primaryStage.setScene(new Scene(gameBox, gameBox.getGameBoxWidth(), gameBox.getGameBoxHeight()));
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNIFIED);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
