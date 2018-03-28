package com.github.tictactoe.view;

import com.github.tictactoe.TicTacToe;
import com.github.tictactoe.controller.Controller;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.util.Optional;

/**
 * Created by maxtar.
 */
@SuppressWarnings("WeakerAccess")
public class AlertHandler extends Alert {

    public AlertHandler(String header, Window owner) {
        super(AlertType.CONFIRMATION);
        super.setTitle("Игра законена");
        super.setHeaderText(header);
        super.setContentText("Сыграем еще раз?");
        super.initOwner(owner);
        super.initModality(Modality.WINDOW_MODAL);
        super.setResizable(false);
        super.initStyle(StageStyle.UNDECORATED);

        ButtonType btnAgain = new ButtonType("Сыграть ещё раз!");
        ButtonType btnCancel = new ButtonType("Закончить");
        super.getButtonTypes().setAll(btnAgain, btnCancel);
        Optional<ButtonType> result = super.showAndWait();
        if (result.isPresent()) {
            Stage stage = (Stage) owner;
            if (result.get() == btnAgain) {
                int size = TicTacToe.getSize();
                stage.setScene(new Scene(new Controller().createContent(), size, size));
            } else if (result.get() == btnCancel) {
                super.close();
                stage.close();
            }
        }



    }
}
